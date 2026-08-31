#!/usr/bin/env python3
"""
Sync accepted submissions from leetcode.cn into this Git repository.

Required environment variables:
  LEETCODE_SESSION
  CSRF_TOKEN

Examples:
  python scripts/sync_leetcode_cn.py --all
  python scripts/sync_leetcode_cn.py --limit 30
  python scripts/sync_leetcode_cn.py --all --no-push
  python scripts/sync_leetcode_cn.py --limit 20 --dry-run --debug
"""

from __future__ import annotations

import argparse
import json
import os
import re
import subprocess
import sys
from datetime import datetime, timezone
from pathlib import Path
from typing import Any
from urllib.error import HTTPError, URLError
from urllib.request import Request, urlopen


GRAPHQL_URL = "https://leetcode.cn/graphql/"
PROBLEMS_URL = "https://leetcode.cn/api/problems/all/"
BASE_URL = "https://leetcode.cn"
AUTOSYNC_DIR = "AutoSync"
STATE_FILE = ".leetcode-sync-state.json"

LANG_EXTENSIONS = {
    "bash": "sh",
    "c": "c",
    "c#": "cs",
    "cpp": "cpp",
    "c++": "cpp",
    "golang": "go",
    "java": "java",
    "javascript": "js",
    "kotlin": "kt",
    "mysql": "sql",
    "mssql": "sql",
    "ms sql server": "sql",
    "oracle": "sql",
    "pandas": "py",
    "php": "php",
    "postgresql": "sql",
    "python": "py",
    "python3": "py",
    "rust": "rs",
    "scala": "scala",
    "swift": "swift",
    "typescript": "ts",
}


SUBMISSION_LIST_QUERY = """
query submissionList(
  $offset: Int!,
  $limit: Int!,
  $lastKey: String,
  $questionSlug: String!
) {
  submissionList(
    offset: $offset,
    limit: $limit,
    lastKey: $lastKey,
    questionSlug: $questionSlug
  ) {
    lastKey
    hasNext
    submissions {
      id
      title
      status
      statusDisplay
      lang
      timestamp
      url
    }
  }
}
"""


USER_STATUS_QUERY = """
query userStatus {
  userStatus {
    isSignedIn
    username
    userSlug
  }
}
"""


SUBMISSION_DETAILS_QUERY = """
query submissionDetails($submissionId: ID!) {
  submissionDetail(submissionId: $submissionId) {
    id
    code
    lang
    timestamp
    question {
      questionId
      questionFrontendId
      title
      translatedTitle
      titleSlug
      difficulty
      topicTags {
        name
        translatedName
        slug
      }
    }
  }
}
"""


SUBMISSION_DETAILS_FALLBACK_QUERY = """
query submissionDetails($submissionId: Int!) {
  submissionDetails(submissionId: $submissionId) {
    id
    code
    lang
    runtimeDisplay
    memoryDisplay
    timestamp
    statusCode
    question {
      questionId
      questionFrontendId
      title
      translatedTitle
      titleSlug
      difficulty
      topicTags {
        name
        translatedName
        slug
      }
    }
  }
}
"""


def repo_root() -> Path:
    result = subprocess.run(
        ["git", "rev-parse", "--show-toplevel"],
        check=True,
        text=True,
        capture_output=True,
    )
    return Path(result.stdout.strip())


def run_git(args: list[str], cwd: Path, check: bool = True) -> subprocess.CompletedProcess[str]:
    return subprocess.run(
        ["git", *args],
        cwd=cwd,
        text=True,
        capture_output=True,
        check=check,
    )


def graphql(query: str, variables: dict[str, Any], session: str, csrf_token: str) -> dict[str, Any]:
    body = json.dumps({"query": query, "variables": variables}).encode("utf-8")
    request = Request(
        GRAPHQL_URL,
        data=body,
        method="POST",
        headers={
            "Content-Type": "application/json",
            "Referer": BASE_URL,
            "Origin": BASE_URL,
            "User-Agent": "Mozilla/5.0",
            "x-csrftoken": csrf_token,
            "Cookie": f"LEETCODE_SESSION={session}; csrftoken={csrf_token}",
        },
    )
    try:
        with urlopen(request, timeout=30) as response:
            payload = json.loads(response.read().decode("utf-8"))
    except HTTPError as exc:
        detail = exc.read().decode("utf-8", errors="replace")
        raise RuntimeError(f"leetcode.cn returned HTTP {exc.code}: {detail}") from exc
    except URLError as exc:
        raise RuntimeError(f"Could not connect to leetcode.cn: {exc}") from exc

    if "errors" in payload:
        raise RuntimeError(json.dumps(payload["errors"], ensure_ascii=False, indent=2))
    return payload["data"]


def get_json(url: str, session: str, csrf_token: str) -> dict[str, Any]:
    request = Request(
        url,
        method="GET",
        headers={
            "Referer": BASE_URL,
            "User-Agent": "Mozilla/5.0",
            "x-csrftoken": csrf_token,
            "Cookie": f"LEETCODE_SESSION={session}; csrftoken={csrf_token}",
        },
    )
    try:
        with urlopen(request, timeout=30) as response:
            return json.loads(response.read().decode("utf-8"))
    except HTTPError as exc:
        detail = exc.read().decode("utf-8", errors="replace")
        raise RuntimeError(f"leetcode.cn returned HTTP {exc.code}: {detail}") from exc
    except URLError as exc:
        raise RuntimeError(f"Could not connect to leetcode.cn: {exc}") from exc


def fetch_user_status(session: str, csrf_token: str) -> dict[str, Any]:
    data = graphql(USER_STATUS_QUERY, {}, session, csrf_token)
    return data.get("userStatus") or {}


def load_state(path: Path) -> dict[str, Any]:
    if not path.exists():
        return {"synced_submission_ids": [], "latest_sync_at": None}
    return json.loads(path.read_text(encoding="utf-8"))


def save_state(path: Path, state: dict[str, Any]) -> None:
    state["synced_submission_ids"] = sorted(set(str(x) for x in state["synced_submission_ids"]))
    state["latest_sync_at"] = datetime.now(timezone.utc).isoformat()
    path.write_text(json.dumps(state, ensure_ascii=False, indent=2) + "\n", encoding="utf-8")


def safe_part(value: str) -> str:
    value = value.strip().lower()
    value = re.sub(r"[\\/:*?\"<>|]", "-", value)
    value = re.sub(r"\s+", "-", value)
    value = re.sub(r"-+", "-", value)
    return value.strip("-") or "unknown"


def language_extension(lang: str) -> str:
    normalized = lang.strip().lower()
    return LANG_EXTENSIONS.get(normalized, safe_part(normalized) or "txt")


def accepted(status_display: str | None, status: Any) -> bool:
    display = (status_display or "").strip().lower()
    status_text = str(status).strip().upper()
    return display in {"accepted", "ac"} or status_text in {"10", "AC", "ACCEPTED"}


def fetch_accepted_problems(session: str, csrf_token: str) -> list[dict[str, Any]]:
    data = get_json(PROBLEMS_URL, session, csrf_token)
    problems = []
    for item in data.get("stat_status_pairs") or []:
        if item.get("status") != "ac":
            continue
        stat = item.get("stat") or {}
        problems.append(
            {
                "frontend_id": stat.get("frontend_question_id"),
                "slug": stat.get("question__title_slug"),
                "title": stat.get("question__title"),
            }
        )
    return problems


def fetch_problem_submissions(
    question_slug: str,
    session: str,
    csrf_token: str,
    limit: int,
) -> list[dict[str, Any]]:
    submissions: list[dict[str, Any]] = []
    offset = 0
    last_key = None
    page_size = min(max(limit, 1), 50)

    while True:
        data = graphql(
            SUBMISSION_LIST_QUERY,
            {
                "offset": offset,
                "limit": page_size,
                "lastKey": last_key,
                "questionSlug": question_slug,
            },
            session,
            csrf_token,
        )
        listing = data["submissionList"]
        page = listing.get("submissions") or []
        submissions.extend(page)

        if len(submissions) >= limit:
            return submissions[:limit]
        if not listing.get("hasNext") or not page:
            return submissions

        last_key = listing.get("lastKey")
        offset += len(page)


def fetch_submissions(
    session: str,
    csrf_token: str,
    fetch_all: bool,
    limit: int,
    debug: bool,
) -> list[dict[str, Any]]:
    problems = fetch_accepted_problems(session, csrf_token)
    if debug:
        print(f"Accepted problems: {len(problems)}")

    if not fetch_all:
        problems = problems[: max(limit, 1)]

    submissions: list[dict[str, Any]] = []
    per_problem_limit = 50 if fetch_all else 5
    for problem in problems:
        slug = problem.get("slug")
        if not slug:
            continue
        page = fetch_problem_submissions(slug, session, csrf_token, per_problem_limit)
        if debug:
            accepted_count = sum(1 for item in page if accepted(item.get("statusDisplay"), item.get("status")))
            print(f"  {slug}: submissions={len(page)} accepted={accepted_count}")
        submissions.extend(page)
        if not fetch_all and len(submissions) >= limit:
            break
    return submissions if fetch_all else submissions[:limit]


def fetch_submission_details(submission_id: str, session: str, csrf_token: str) -> dict[str, Any]:
    variables = {"submissionId": str(submission_id)}
    try:
        data = graphql(SUBMISSION_DETAILS_QUERY, variables, session, csrf_token)
        return data["submissionDetail"]
    except RuntimeError as exc:
        if "submissionDetail" not in str(exc) and "SubmissionDetail" not in str(exc):
            raise
        data = graphql(SUBMISSION_DETAILS_FALLBACK_QUERY, {"submissionId": int(submission_id)}, session, csrf_token)
        return data["submissionDetails"]


def solution_path(root: Path, details: dict[str, Any]) -> Path:
    question = details["question"]
    frontend_id = safe_part(str(question.get("questionFrontendId") or question.get("questionId") or "unknown"))
    slug = safe_part(question.get("titleSlug") or question.get("translatedTitle") or question.get("title") or "problem")
    lang = details.get("lang") or "txt"
    ext = language_extension(lang)
    return root / AUTOSYNC_DIR / f"{frontend_id}-{slug}" / f"solution.{ext}"


def readme_path(solution: Path) -> Path:
    return solution.parent / "README.md"


def make_readme(details: dict[str, Any]) -> str:
    question = details["question"]
    title = question.get("translatedTitle") or question.get("title") or "Unknown"
    title_slug = question.get("titleSlug") or ""
    frontend_id = question.get("questionFrontendId") or question.get("questionId") or "unknown"
    difficulty = question.get("difficulty") or "unknown"
    tags = [
        tag.get("translatedName") or tag.get("name")
        for tag in question.get("topicTags") or []
        if tag.get("translatedName") or tag.get("name")
    ]
    tags_text = ", ".join(tags) if tags else "unknown"
    submitted_at = datetime.fromtimestamp(int(details["timestamp"]), tz=timezone.utc).astimezone().isoformat()
    problem_url = f"{BASE_URL}/problems/{title_slug}/" if title_slug else BASE_URL
    submission_url = f"{BASE_URL}/submissions/detail/{details['id']}/"

    return "\n".join(
        [
            f"# {frontend_id}. {title}",
            "",
            f"- Difficulty: {difficulty}",
            f"- Language: {details.get('lang', 'unknown')}",
            f"- Runtime: {details.get('runtimeDisplay') or 'unknown'}",
            f"- Memory: {details.get('memoryDisplay') or 'unknown'}",
            f"- Submitted: {submitted_at}",
            f"- Tags: {tags_text}",
            f"- Problem: {problem_url}",
            f"- Submission: {submission_url}",
            "",
        ]
    )


def write_submission(root: Path, details: dict[str, Any]) -> Path:
    path = solution_path(root, details)
    path.parent.mkdir(parents=True, exist_ok=True)
    code = details.get("code") or ""
    path.write_text(code.rstrip() + "\n", encoding="utf-8")
    readme_path(path).write_text(make_readme(details), encoding="utf-8")
    return path


def commit_and_push(root: Path, no_push: bool, dry_run: bool) -> None:
    if dry_run:
        print("Dry run enabled; no files were written and git was not changed.")
        return

    status = run_git(["status", "--short"], root).stdout.strip()
    if not status:
        print("No file changes to commit.")
        return

    print("Changed files:")
    print(status)
    run_git(["add", AUTOSYNC_DIR, STATE_FILE, ".gitignore", "README.md", "scripts"], root)
    commit = run_git(
        ["commit", "-m", f"sync: update leetcode solutions {datetime.now().strftime('%Y-%m-%d')}"],
        root,
        check=False,
    )
    if commit.returncode != 0:
        print(commit.stdout.strip())
        print(commit.stderr.strip(), file=sys.stderr)
        return

    print(commit.stdout.strip())
    if no_push:
        print("Created local commit; --no-push enabled.")
        return

    push = run_git(["push"], root, check=False)
    if push.returncode != 0:
        print(push.stdout.strip())
        print(push.stderr.strip(), file=sys.stderr)
        raise SystemExit(push.returncode)
    print(push.stdout.strip() or "Pushed to origin.")


def main() -> int:
    parser = argparse.ArgumentParser(description="Sync accepted leetcode.cn submissions to GitHub.")
    parser.add_argument("--all", action="store_true", help="Fetch all visible submissions.")
    parser.add_argument("--limit", type=int, default=50, help="Recent submissions to scan when --all is not set.")
    parser.add_argument("--no-push", action="store_true", help="Commit locally but do not push.")
    parser.add_argument("--dry-run", action="store_true", help="Fetch and show what would be written.")
    parser.add_argument("--debug", action="store_true", help="Print scanned submission statuses.")
    args = parser.parse_args()

    session = os.getenv("LEETCODE_SESSION")
    csrf_token = os.getenv("CSRF_TOKEN") or os.getenv("csrftoken")
    if not session or not csrf_token:
        print("Missing LEETCODE_SESSION or CSRF_TOKEN environment variable.", file=sys.stderr)
        return 2

    root = repo_root()
    state_path = root / STATE_FILE
    state = load_state(state_path)
    synced_ids = set(str(x) for x in state.get("synced_submission_ids", []))

    user_status = fetch_user_status(session, csrf_token)
    if args.debug:
        print(
            "User status: "
            f"isSignedIn={user_status.get('isSignedIn')} "
            f"username={user_status.get('username')}"
        )
    if not user_status.get("isSignedIn"):
        print("leetcode.cn says this cookie is not signed in. Please refresh LEETCODE_SESSION and CSRF_TOKEN.")
        return 2

    submissions = fetch_submissions(session, csrf_token, args.all, args.limit, args.debug)
    if args.debug:
        print(f"Fetched submissions: {len(submissions)}")
        for item in submissions[: min(len(submissions), 20)]:
            print(
                "  "
                f"id={item.get('id')} "
                f"status={item.get('status')} "
                f"statusDisplay={item.get('statusDisplay')} "
                f"lang={item.get('lang')} "
                f"title={item.get('title')} "
                f"timestamp={item.get('timestamp')}"
            )

    accepted_submissions = [
        item for item in submissions if accepted(item.get("statusDisplay"), item.get("status"))
    ]

    seen_problem_lang: set[tuple[str, str]] = set()
    synced_count = 0
    skipped_count = 0

    for item in accepted_submissions:
        submission_id = str(item["id"])
        problem_lang = (item.get("title") or "", item.get("lang") or "")

        if submission_id in synced_ids:
            skipped_count += 1
            continue
        if problem_lang in seen_problem_lang and not args.all:
            skipped_count += 1
            continue

        details = fetch_submission_details(submission_id, session, csrf_token)
        if not details or not details.get("code"):
            skipped_count += 1
            continue

        path = solution_path(root, details)
        if args.dry_run:
            print(f"Would sync {submission_id}: {path.relative_to(root)}")
        else:
            write_submission(root, details)
            print(f"Synced {submission_id}: {path.relative_to(root)}")
            synced_ids.add(submission_id)
            synced_count += 1
        seen_problem_lang.add(problem_lang)

    if not args.dry_run:
        state["synced_submission_ids"] = list(synced_ids)
        save_state(state_path, state)
    print(f"Synced: {synced_count}; skipped: {skipped_count}; accepted scanned: {len(accepted_submissions)}")

    commit_and_push(root, no_push=args.no_push, dry_run=args.dry_run)
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
