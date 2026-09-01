import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

public class LeetCodeCnSync {
    private static final String GRAPHQL_URL = "https://leetcode.cn/graphql/";
    private static final String PROBLEMS_URL = "https://leetcode.cn/api/problems/all/";
    private static final String BASE_URL = "https://leetcode.cn";
    private static final String AUTOSYNC_DIR = "AutoSync";
    private static final String STATE_FILE = ".leetcode-sync-state.json";
    private static final int MAX_HTTP_ATTEMPTS = 4;
    private static final int MAX_GRAPHQL_ATTEMPTS = 30;
    private static final long REQUEST_PAUSE_MILLIS = 350L;
    private static final long DETAIL_PAUSE_MILLIS = 2_500L;

    private static final Map<String, String> LANG_EXTENSIONS = Map.ofEntries(
            Map.entry("bash", "sh"),
            Map.entry("c", "c"),
            Map.entry("c#", "cs"),
            Map.entry("cpp", "cpp"),
            Map.entry("c++", "cpp"),
            Map.entry("golang", "go"),
            Map.entry("java", "java"),
            Map.entry("javascript", "js"),
            Map.entry("kotlin", "kt"),
            Map.entry("mysql", "sql"),
            Map.entry("mssql", "sql"),
            Map.entry("ms sql server", "sql"),
            Map.entry("oracle", "sql"),
            Map.entry("pandas", "py"),
            Map.entry("php", "php"),
            Map.entry("postgresql", "sql"),
            Map.entry("python", "py"),
            Map.entry("python3", "py"),
            Map.entry("rust", "rs"),
            Map.entry("scala", "scala"),
            Map.entry("swift", "swift"),
            Map.entry("typescript", "ts")
    );

    private static final String USER_STATUS_QUERY = """
            query userStatus {
              userStatus {
                isSignedIn
                username
                userSlug
              }
            }
            """;

    private static final String SUBMISSION_LIST_QUERY = """
            query submissionList($offset: Int!, $limit: Int!, $lastKey: String, $questionSlug: String!) {
              submissionList(offset: $offset, limit: $limit, lastKey: $lastKey, questionSlug: $questionSlug) {
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
            """;

    private static final String SUBMISSION_DETAIL_QUERY = """
            query submissionDetails($submissionId: ID!) {
              submissionDetail(submissionId: $submissionId) {
                id
                code
                lang
                timestamp
              }
            }
            """;

    public static void main(String[] args) throws Exception {
        Options options = Options.parse(args);
        if (options.help) {
            Options.printHelp();
            return;
        }

        String session = System.getenv("LEETCODE_SESSION");
        String csrfToken = firstNonBlank(System.getenv("CSRF_TOKEN"), System.getenv("csrftoken"));
        if (isBlank(session) || isBlank(csrfToken)) {
            System.err.println("Missing LEETCODE_SESSION or CSRF_TOKEN environment variable.");
            System.exit(2);
        }

        Path root = repoRoot();
        SyncState state = SyncState.load(root.resolve(STATE_FILE));

        Map<String, Object> userStatus = fetchUserStatus(session, csrfToken);
        if (options.debug) {
            System.out.println("User status: isSignedIn=" + userStatus.get("isSignedIn")
                    + " username=" + value(userStatus.get("username")));
        }
        if (!Boolean.TRUE.equals(userStatus.get("isSignedIn"))) {
            System.out.println("leetcode.cn says this cookie is not signed in. Please refresh LEETCODE_SESSION and CSRF_TOKEN.");
            System.exit(2);
        }

        List<Map<String, Object>> submissions = fetchSubmissions(session, csrfToken, options.all, options.limit, options.debug);
        if (options.debug) {
            System.out.println("Fetched submissions: " + submissions.size());
            for (int i = 0; i < Math.min(submissions.size(), 20); i++) {
                Map<String, Object> item = submissions.get(i);
                System.out.println("  id=" + value(item.get("id"))
                        + " status=" + value(item.get("status"))
                        + " statusDisplay=" + value(item.get("statusDisplay"))
                        + " lang=" + value(item.get("lang"))
                        + " title=" + value(item.get("title"))
                        + " timestamp=" + value(item.get("timestamp")));
            }
        }

        int syncedCount = 0;
        int skippedCount = 0;
        int plannedCount = 0;
        Set<String> seenProblemLang = new LinkedHashSet<>();
        for (Map<String, Object> item : submissions) {
            String submissionId = value(item.get("id"));
            String problemLang = value(item.get("problemSlug")) + "|" + value(item.get("lang"));

            if (state.syncedSubmissionIds.contains(submissionId)) {
                if (syncedOutputExists(root, item)) {
                    skippedCount++;
                    continue;
                }
                state.syncedSubmissionIds.remove(submissionId);
                System.out.println("State contains " + submissionId + " but local solution file is missing; syncing it again.");
            }
            if (seenProblemLang.contains(problemLang)) {
                skippedCount++;
                continue;
            }
            if (options.maxSync > 0 && (options.dryRun ? plannedCount : syncedCount) >= options.maxSync) {
                System.out.println("Reached --max-sync " + options.maxSync + "; stop this batch and run again to continue.");
                break;
            }

            if (options.dryRun) {
                Path solution = previewSolutionPath(root, item);
                System.out.println("Would sync " + submissionId + ": " + root.relativize(solution));
                plannedCount++;
            } else {
                Map<String, Object> details = fetchSubmissionDetails(submissionId, session, csrfToken);
                details.put("problemFrontendId", item.get("problemFrontendId"));
                details.put("problemSlug", item.get("problemSlug"));
                details.put("problemTitle", item.get("problemTitle"));
                details.put("title", item.get("title"));

                if (details.isEmpty() || isBlank(value(details.get("code")))) {
                    skippedCount++;
                    continue;
                }

                Path solution = solutionPath(root, details);
                writeSubmission(root, details);
                state.syncedSubmissionIds.add(submissionId);
                state.save(root.resolve(STATE_FILE));
                syncedCount++;
                System.out.println("Synced " + submissionId + ": " + root.relativize(solution));
            }
            seenProblemLang.add(problemLang);
        }

        if (!options.dryRun) {
            state.save(root.resolve(STATE_FILE));
        }
        System.out.println("Synced: " + syncedCount + "; skipped: " + skippedCount + "; accepted scanned: " + submissions.size());
        commitAndPush(root, options.noPush, options.dryRun);
    }

    private static Map<String, Object> fetchUserStatus(String session, String csrfToken) throws IOException {
        Map<String, Object> data = graphql(USER_STATUS_QUERY, Map.of(), session, csrfToken);
        return asMap(data.get("userStatus"));
    }

    private static List<Map<String, Object>> fetchAcceptedProblems(String session, String csrfToken) throws IOException {
        Map<String, Object> data = getJson(PROBLEMS_URL, session, csrfToken);
        List<Map<String, Object>> problems = new ArrayList<>();
        for (Object raw : asList(data.get("stat_status_pairs"))) {
            Map<String, Object> item = asMap(raw);
            if (!"ac".equals(value(item.get("status")))) {
                continue;
            }
            Map<String, Object> stat = asMap(item.get("stat"));
            Map<String, Object> problem = new LinkedHashMap<>();
            problem.put("frontend_id", stat.get("frontend_question_id"));
            problem.put("slug", stat.get("question__title_slug"));
            problem.put("title", stat.get("question__title"));
            problems.add(problem);
        }
        return problems;
    }

    private static List<Map<String, Object>> fetchProblemSubmissions(
            String questionSlug,
            String session,
            String csrfToken,
            int limit
    ) throws IOException {
        List<Map<String, Object>> submissions = new ArrayList<>();
        int offset = 0;
        Object lastKey = null;
        int pageSize = Math.min(Math.max(limit, 1), 50);

        while (true) {
            Map<String, Object> variables = new LinkedHashMap<>();
            variables.put("offset", offset);
            variables.put("limit", pageSize);
            variables.put("lastKey", lastKey);
            variables.put("questionSlug", questionSlug);

            Map<String, Object> data = graphql(SUBMISSION_LIST_QUERY, variables, session, csrfToken);
            Map<String, Object> listing = asMap(data.get("submissionList"));
            List<Object> page = asList(listing.get("submissions"));
            for (Object raw : page) {
                submissions.add(asMap(raw));
            }

            if (submissions.size() >= limit) {
                return submissions.subList(0, limit);
            }
            if (!Boolean.TRUE.equals(listing.get("hasNext")) || page.isEmpty()) {
                return submissions;
            }
            lastKey = listing.get("lastKey");
            offset += page.size();
        }
    }

    private static List<Map<String, Object>> fetchSubmissions(
            String session,
            String csrfToken,
            boolean fetchAll,
            int limit,
            boolean debug
    ) throws IOException {
        List<Map<String, Object>> problems = fetchAcceptedProblems(session, csrfToken);
        if (debug) {
            System.out.println("Accepted problems: " + problems.size());
        }
        if (!fetchAll && problems.size() > limit) {
            problems = problems.subList(0, Math.max(limit, 1));
        }

        List<Map<String, Object>> submissions = new ArrayList<>();
        int perProblemLimit = fetchAll ? 12 : 5;
        int processedProblems = 0;
        for (Map<String, Object> problem : problems) {
            String slug = value(problem.get("slug"));
            if (isBlank(slug)) {
                continue;
            }
            List<Map<String, Object>> page = fetchProblemSubmissions(slug, session, csrfToken, perProblemLimit);
            for (Map<String, Object> item : page) {
                item.put("problemFrontendId", problem.get("frontend_id"));
                item.put("problemSlug", problem.get("slug"));
                item.put("problemTitle", problem.get("title"));
            }
            List<Map<String, Object>> accepted = latestAcceptedByLanguage(page);
            if (debug) {
                System.out.println("  " + slug + ": submissions=" + page.size() + " accepted=" + accepted.size());
            }
            submissions.addAll(accepted);
            processedProblems++;
            if (!debug && fetchAll && processedProblems % 25 == 0) {
                System.out.println("Scanned accepted problems: " + processedProblems + "/" + problems.size());
            }
            pauseBetweenRequests();
            if (!fetchAll && submissions.size() >= limit) {
                break;
            }
        }

        if (!fetchAll && submissions.size() > limit) {
            return submissions.subList(0, limit);
        }
        return submissions;
    }

    private static List<Map<String, Object>> latestAcceptedByLanguage(List<Map<String, Object>> submissions) {
        List<Map<String, Object>> result = new ArrayList<>();
        Set<String> seenLanguages = new LinkedHashSet<>();
        for (Map<String, Object> item : submissions) {
            if (!accepted(item.get("statusDisplay"), item.get("status"))) {
                continue;
            }
            if (seenLanguages.add(value(item.get("lang")))) {
                result.add(item);
            }
        }
        return result;
    }

    private static Map<String, Object> fetchSubmissionDetails(String submissionId, String session, String csrfToken) throws IOException {
        try {
            Map<String, Object> data = graphql(SUBMISSION_DETAIL_QUERY, Map.of("submissionId", submissionId), session, csrfToken);
            return asMap(data.get("submissionDetail"));
        } finally {
            pauseAfterSubmissionDetail();
        }
    }

    private static Map<String, Object> graphql(String query, Map<String, Object> variables, String session, String csrfToken)
            throws IOException {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("query", query);
        payload.put("variables", variables);
        String requestBody = Json.stringify(payload);
        RuntimeException lastGraphqlError = null;

        for (int attempt = 1; attempt <= MAX_GRAPHQL_ATTEMPTS; attempt++) {
            String response = request("POST", GRAPHQL_URL, requestBody, session, csrfToken);
            Map<String, Object> parsed = asMap(Json.parse(response));
            if (!parsed.containsKey("errors")) {
                return asMap(parsed.get("data"));
            }

            String errors = Json.stringify(parsed.get("errors"));
            lastGraphqlError = new RuntimeException(errors);
            if (!isRetryableGraphqlError(errors) || attempt == MAX_GRAPHQL_ATTEMPTS) {
                throw lastGraphqlError;
            }
            sleepBeforeGraphqlRetry(attempt, errors);
        }
        throw lastGraphqlError == null ? new RuntimeException("GraphQL request failed.") : lastGraphqlError;
    }

    private static Map<String, Object> getJson(String url, String session, String csrfToken) throws IOException {
        return asMap(Json.parse(request("GET", url, null, session, csrfToken)));
    }

    private static String request(String method, String url, String body, String session, String csrfToken) throws IOException {
        IOException lastIoError = null;
        RuntimeException lastHttpError = null;
        for (int attempt = 1; attempt <= MAX_HTTP_ATTEMPTS; attempt++) {
            try {
                return requestOnce(method, url, body, session, csrfToken, attempt);
            } catch (IOException ex) {
                lastIoError = ex;
                if (attempt == MAX_HTTP_ATTEMPTS) {
                    throw ex;
                }
            } catch (RuntimeException ex) {
                lastHttpError = ex;
                if (!isRetryableHttpError(ex) || attempt == MAX_HTTP_ATTEMPTS) {
                    throw ex;
                }
            }
            sleepBeforeRetry(attempt);
        }
        if (lastIoError != null) {
            throw lastIoError;
        }
        throw lastHttpError == null ? new RuntimeException("Request failed.") : lastHttpError;
    }

    private static String requestOnce(String method, String url, String body, String session, String csrfToken, int attempt)
            throws IOException {
        URL target = URI.create(url).toURL();
        HttpURLConnection connection = (HttpURLConnection) target.openConnection();
        connection.setInstanceFollowRedirects(true);
        connection.setConnectTimeout(30_000);
        connection.setReadTimeout(30_000);
        connection.setRequestMethod(method);
        connection.setRequestProperty("Referer", BASE_URL);
        connection.setRequestProperty("Origin", BASE_URL);
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        connection.setRequestProperty("x-csrftoken", csrfToken);
        connection.setRequestProperty("Cookie", "LEETCODE_SESSION=" + session + "; csrftoken=" + csrfToken);

        if ("POST".equals(method)) {
            byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Content-Length", String.valueOf(bytes.length));
            try (OutputStream out = connection.getOutputStream()) {
                out.write(bytes);
            }
        }

        int statusCode = connection.getResponseCode();
        InputStream stream = statusCode >= 200 && statusCode < 300
                ? connection.getInputStream()
                : connection.getErrorStream();
        String response;
        try (InputStream in = stream) {
            response = in == null ? "" : new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } finally {
            connection.disconnect();
        }
        if (statusCode < 200 || statusCode >= 300) {
            throw new RuntimeException("leetcode.cn returned HTTP " + statusCode + " after attempt " + attempt + ": " + response);
        }
        return response;
    }

    private static boolean isRetryableHttpError(RuntimeException ex) {
        String message = ex.getMessage() == null ? "" : ex.getMessage();
        return message.contains("HTTP 429")
                || message.contains("HTTP 500")
                || message.contains("HTTP 502")
                || message.contains("HTTP 503")
                || message.contains("HTTP 504");
    }

    private static boolean isRetryableGraphqlError(String errors) {
        String message = errors == null ? "" : errors.toLowerCase(Locale.ROOT);
        if (message.contains("\u8d85\u51fa\u8bbf\u95ee\u9650\u5236")
                || message.contains("\u7a0d\u540e\u518d\u8bd5")) {
            return true;
        }
        return message.contains("超出访问限制")
                || message.contains("稍后再试")
                || message.contains("too many requests")
                || message.contains("rate limit")
                || message.contains("please try again later");
    }

    private static void sleepBeforeRetry(int attempt) {
        try {
            Thread.sleep(800L * attempt * attempt);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while waiting to retry.", ex);
        }
    }

    private static void sleepBeforeGraphqlRetry(int attempt, String errors) {
        long seconds = Math.min(300L, 30L * attempt);
        System.out.println("leetcode.cn rate limited this request; waiting " + seconds
                + "s before retry " + (attempt + 1) + "/" + MAX_GRAPHQL_ATTEMPTS + ".");
        try {
            Thread.sleep(seconds * 1_000L);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while waiting for leetcode.cn rate limit.", ex);
        }
    }

    private static void pauseBetweenRequests() {
        try {
            Thread.sleep(REQUEST_PAUSE_MILLIS);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while pacing requests.", ex);
        }
    }

    private static void pauseAfterSubmissionDetail() {
        try {
            Thread.sleep(DETAIL_PAUSE_MILLIS);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while pacing submission detail requests.", ex);
        }
    }

    private static Path repoRoot() throws IOException, InterruptedException {
        ProcessResult result = run(List.of("git", "rev-parse", "--show-toplevel"), Path.of("."), true);
        return Path.of(result.stdout.trim());
    }

    private static void writeSubmission(Path root, Map<String, Object> details) throws IOException {
        Path path = solutionPath(root, details);
        Files.createDirectories(path.getParent());
        Files.writeString(path, value(details.get("code")).stripTrailing() + System.lineSeparator(), StandardCharsets.UTF_8);
        Files.writeString(readmePath(path), makeReadme(details), StandardCharsets.UTF_8);
    }

    private static Path solutionPath(Path root, Map<String, Object> details) {
        String frontendId = safePart(firstNonBlank(value(details.get("problemFrontendId")), "unknown"));
        String slug = safePart(firstNonBlank(value(details.get("problemSlug")), value(details.get("problemTitle")), value(details.get("title")), "problem"));
        String ext = languageExtension(value(details.get("lang")));
        return root.resolve(AUTOSYNC_DIR).resolve(frontendId + "-" + slug).resolve("solution." + ext);
    }

    private static Path previewSolutionPath(Path root, Map<String, Object> submission) {
        String frontendId = safePart(firstNonBlank(value(submission.get("problemFrontendId")), "unknown"));
        String slug = safePart(firstNonBlank(value(submission.get("problemSlug")), value(submission.get("problemTitle")), value(submission.get("title")), "problem"));
        String ext = languageExtension(value(submission.get("lang")));
        return root.resolve(AUTOSYNC_DIR).resolve(frontendId + "-" + slug).resolve("solution." + ext);
    }

    private static boolean syncedOutputExists(Path root, Map<String, Object> submission) {
        Path solution = previewSolutionPath(root, submission);
        return Files.exists(solution) && Files.exists(readmePath(solution));
    }

    private static Path readmePath(Path solution) {
        return solution.getParent().resolve("README.md");
    }

    private static String makeReadme(Map<String, Object> details) {
        String title = firstNonBlank(value(details.get("problemTitle")), value(details.get("title")), "Unknown");
        String titleSlug = value(details.get("problemSlug"));
        String frontendId = firstNonBlank(value(details.get("problemFrontendId")), "unknown");
        String submittedAt = formatTimestamp(details.get("timestamp"));
        String problemUrl = isBlank(titleSlug) ? BASE_URL : BASE_URL + "/problems/" + titleSlug + "/";
        String submissionUrl = BASE_URL + "/submissions/detail/" + value(details.get("id")) + "/";

        return "# " + frontendId + ". " + title + "\n\n"
                + "- Language: " + firstNonBlank(value(details.get("lang")), "unknown") + "\n"
                + "- Submitted: " + submittedAt + "\n"
                + "- Problem: " + problemUrl + "\n"
                + "- Submission: " + submissionUrl + "\n";
    }

    private static String formatTimestamp(Object timestamp) {
        try {
            long seconds = Long.parseLong(value(timestamp));
            return DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(Instant.ofEpochSecond(seconds).atZone(ZoneId.systemDefault()));
        } catch (RuntimeException ex) {
            return "unknown";
        }
    }

    private static void commitAndPush(Path root, boolean noPush, boolean dryRun) throws IOException, InterruptedException {
        if (dryRun) {
            System.out.println("Dry run enabled; no files were written and git was not changed.");
            return;
        }

        ProcessResult status = run(List.of("git", "status", "--short"), root, true);
        if (status.stdout.trim().isEmpty()) {
            System.out.println("No file changes to commit.");
            if (!noPush) {
                pushToRemote(root);
            }
            return;
        }

        System.out.println("Changed files:");
        System.out.println(status.stdout.trim());
        stageSyncChanges(root);
        ProcessResult commit = run(
                List.of("git", "commit", "-m", "sync: update leetcode solutions " + java.time.LocalDate.now()),
                root,
                false
        );
        if (commit.exitCode != 0) {
            System.out.println(commit.stdout.trim());
            System.err.println(commit.stderr.trim());
            return;
        }

        System.out.println(commit.stdout.trim());
        if (noPush) {
            System.out.println("Created local commit; --no-push enabled.");
            return;
        }

        pushToRemote(root);
    }

    private static void pushToRemote(Path root) throws IOException, InterruptedException {
        if (!syncRemoteBeforePush(root)) {
            System.err.println("Could not integrate remote changes automatically. Resolve the git message above, then run git push again.");
            System.exit(1);
        }

        ProcessResult push = run(List.of("git", "push"), root, false);
        if (push.exitCode != 0) {
            System.out.println(push.stdout.trim());
            System.err.println(push.stderr.trim());
            System.exit(push.exitCode);
        }
        System.out.println(push.stdout.trim().isEmpty() ? "Pushed to origin." : push.stdout.trim());
    }

    private static boolean syncRemoteBeforePush(Path root) throws IOException, InterruptedException {
        ProcessResult remote = run(List.of("git", "remote", "get-url", "origin"), root, false);
        if (remote.exitCode != 0) {
            System.out.println("No origin remote found; skip pull before push.");
            return true;
        }

        ProcessResult branchResult = run(List.of("git", "branch", "--show-current"), root, false);
        String branch = firstNonBlank(branchResult.stdout.trim(), "main");
        System.out.println("Pulling latest origin/" + branch + " before push...");
        ProcessResult pull = run(List.of("git", "pull", "--rebase", "--autostash", "origin", branch), root, false);
        if (pull.exitCode == 0) {
            printIfPresent(pull.stdout);
            printIfPresent(pull.stderr);
            return true;
        }

        String combined = pull.stdout + pull.stderr;
        if (combined.contains("couldn't find remote ref") || combined.contains("could not find remote ref")) {
            System.out.println("Remote branch origin/" + branch + " does not exist yet; push will create it.");
            return true;
        }

        printIfPresent(pull.stdout);
        if (!pull.stderr.isBlank()) {
            System.err.println(pull.stderr.trim());
        }
        return false;
    }

    private static void printIfPresent(String text) {
        if (!text.isBlank()) {
            System.out.println(text.trim());
        }
    }

    private static void stageSyncChanges(Path root) throws IOException, InterruptedException {
        List<String> candidates = List.of(AUTOSYNC_DIR, STATE_FILE, ".gitignore", "README.md", "sync-dashboard", "scripts");
        List<String> paths = new ArrayList<>();
        for (String candidate : candidates) {
            if (Files.exists(root.resolve(candidate)) || hasGitChanges(root, candidate)) {
                paths.add(candidate);
            }
        }

        if (paths.isEmpty()) {
            System.out.println("No sync-owned paths to stage.");
            return;
        }

        List<String> command = new ArrayList<>(List.of("git", "add", "-A", "--"));
        command.addAll(paths);
        run(command, root, true);
    }

    private static boolean hasGitChanges(Path root, String path) throws IOException, InterruptedException {
        ProcessResult result = run(List.of("git", "status", "--short", "--", path), root, false);
        return !result.stdout.trim().isEmpty();
    }

    private static ProcessResult run(List<String> command, Path cwd, boolean check) throws IOException, InterruptedException {
        Process process = new ProcessBuilder(command)
                .directory(cwd.toFile())
                .redirectErrorStream(false)
                .start();
        String stdout = readAll(process.getInputStream());
        String stderr = readAll(process.getErrorStream());
        int exitCode = process.waitFor();
        if (check && exitCode != 0) {
            throw new RuntimeException(String.join(" ", command) + " failed: " + stderr);
        }
        return new ProcessResult(exitCode, stdout, stderr);
    }

    private static String readAll(InputStream stream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, Charset.defaultCharset()))) {
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line).append(System.lineSeparator());
            }
            return out.toString();
        }
    }

    private static boolean accepted(Object statusDisplay, Object status) {
        String display = value(statusDisplay).trim().toLowerCase(Locale.ROOT);
        String statusText = value(status).trim().toUpperCase(Locale.ROOT);
        return display.equals("accepted") || display.equals("ac")
                || statusText.equals("10") || statusText.equals("AC") || statusText.equals("ACCEPTED");
    }

    private static String languageExtension(String lang) {
        String normalized = lang.trim().toLowerCase(Locale.ROOT);
        String safe = safePart(normalized);
        return LANG_EXTENSIONS.getOrDefault(normalized, safe.isEmpty() ? "txt" : safe);
    }

    private static String safePart(String value) {
        String cleaned = value.trim().toLowerCase(Locale.ROOT);
        cleaned = Pattern.compile("[\\\\/:*?\"<>|]").matcher(cleaned).replaceAll("-");
        cleaned = Pattern.compile("\\s+").matcher(cleaned).replaceAll("-");
        cleaned = Pattern.compile("-+").matcher(cleaned).replaceAll("-");
        cleaned = cleaned.replaceAll("^-|-$", "");
        return cleaned.isEmpty() ? "unknown" : cleaned;
    }

    private static String value(Object object) {
        return object == null ? "" : String.valueOf(object);
    }

    private static String firstNonBlank(String... values) {
        for (String item : values) {
            if (!isBlank(item)) {
                return item;
            }
        }
        return "";
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> asMap(Object object) {
        if (object instanceof Map<?, ?> map) {
            return (Map<String, Object>) map;
        }
        return new LinkedHashMap<>();
    }

    @SuppressWarnings("unchecked")
    private static List<Object> asList(Object object) {
        if (object instanceof List<?> list) {
            return (List<Object>) list;
        }
        return List.of();
    }

    private record ProcessResult(int exitCode, String stdout, String stderr) {
    }

    private static class Options {
        boolean all;
        int limit = 50;
        int maxSync;
        boolean noPush;
        boolean dryRun;
        boolean debug;
        boolean help;

        static Options parse(String[] args) {
            Options options = new Options();
            for (int i = 0; i < args.length; i++) {
                String arg = args[i];
                switch (arg) {
                    case "--all" -> options.all = true;
                    case "--no-push" -> options.noPush = true;
                    case "--dry-run" -> options.dryRun = true;
                    case "--debug" -> options.debug = true;
                    case "-h", "--help" -> options.help = true;
                    case "--limit" -> {
                        if (i + 1 >= args.length) {
                            throw new IllegalArgumentException("--limit requires a number.");
                        }
                        options.limit = Integer.parseInt(args[++i]);
                    }
                    case "--max-sync" -> {
                        if (i + 1 >= args.length) {
                            throw new IllegalArgumentException("--max-sync requires a number.");
                        }
                        options.maxSync = Integer.parseInt(args[++i]);
                    }
                    default -> throw new IllegalArgumentException("Unknown argument: " + arg);
                }
            }
            return options;
        }

        static void printHelp() {
            System.out.println("""
                    Usage:
                      java .\\sync-dashboard\\tools\\LeetCodeCnSync.java [options]

                    Options:
                      --all          Fetch all visible accepted problems.
                      --limit N      Recent accepted problems to scan when --all is not set. Default: 50.
                      --max-sync N   Sync at most N new accepted submissions in this run. Default: unlimited.
                      --no-push      Commit locally but do not push.
                      --dry-run      Fetch and show what would be written.
                      --debug        Print scanned submission statuses.
                      -h, --help     Show this help.
                    """);
        }
    }

    private static class SyncState {
        final Set<String> syncedSubmissionIds = new LinkedHashSet<>();

        static SyncState load(Path path) throws IOException {
            SyncState state = new SyncState();
            if (!Files.exists(path)) {
                return state;
            }
            Map<String, Object> parsed = asMap(Json.parse(Files.readString(path, StandardCharsets.UTF_8)));
            for (Object id : asList(parsed.get("synced_submission_ids"))) {
                state.syncedSubmissionIds.add(value(id));
            }
            return state;
        }

        void save(Path path) throws IOException {
            List<String> ids = new ArrayList<>(syncedSubmissionIds);
            Collections.sort(ids);
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("synced_submission_ids", ids);
            data.put("latest_sync_at", Instant.now().toString());
            Files.writeString(path, Json.stringify(data) + "\n", StandardCharsets.UTF_8);
        }
    }

    private static class Json {
        static Object parse(String text) {
            return new Parser(text).parse();
        }

        static String stringify(Object value) {
            if (value == null) {
                return "null";
            }
            if (value instanceof String string) {
                return quote(string);
            }
            if (value instanceof Number || value instanceof Boolean) {
                return value.toString();
            }
            if (value instanceof Map<?, ?> map) {
                List<String> items = new ArrayList<>();
                for (Map.Entry<?, ?> entry : map.entrySet()) {
                    items.add(quote(String.valueOf(entry.getKey())) + ":" + stringify(entry.getValue()));
                }
                return "{" + String.join(",", items) + "}";
            }
            if (value instanceof Iterable<?> iterable) {
                List<String> items = new ArrayList<>();
                for (Object item : iterable) {
                    items.add(stringify(item));
                }
                return "[" + String.join(",", items) + "]";
            }
            return quote(String.valueOf(value));
        }

        private static String quote(String value) {
            StringBuilder out = new StringBuilder("\"");
            for (int i = 0; i < value.length(); i++) {
                char ch = value.charAt(i);
                switch (ch) {
                    case '"' -> out.append("\\\"");
                    case '\\' -> out.append("\\\\");
                    case '\b' -> out.append("\\b");
                    case '\f' -> out.append("\\f");
                    case '\n' -> out.append("\\n");
                    case '\r' -> out.append("\\r");
                    case '\t' -> out.append("\\t");
                    default -> {
                        if (ch < 0x20) {
                            out.append(String.format("\\u%04x", (int) ch));
                        } else {
                            out.append(ch);
                        }
                    }
                }
            }
            out.append('"');
            return out.toString();
        }

        private static class Parser {
            private final String text;
            private int index;

            Parser(String text) {
                this.text = Objects.requireNonNull(text);
            }

            Object parse() {
                Object value = parseValue();
                skipWhitespace();
                if (index != text.length()) {
                    throw new IllegalArgumentException("Unexpected trailing JSON at " + index);
                }
                return value;
            }

            private Object parseValue() {
                skipWhitespace();
                if (index >= text.length()) {
                    throw new IllegalArgumentException("Unexpected end of JSON.");
                }
                char ch = text.charAt(index);
                return switch (ch) {
                    case '{' -> parseObject();
                    case '[' -> parseArray();
                    case '"' -> parseString();
                    case 't' -> consumeLiteral("true", Boolean.TRUE);
                    case 'f' -> consumeLiteral("false", Boolean.FALSE);
                    case 'n' -> consumeLiteral("null", null);
                    default -> parseNumber();
                };
            }

            private Map<String, Object> parseObject() {
                expect('{');
                Map<String, Object> map = new LinkedHashMap<>();
                skipWhitespace();
                if (peek('}')) {
                    index++;
                    return map;
                }
                while (true) {
                    String key = parseString();
                    skipWhitespace();
                    expect(':');
                    map.put(key, parseValue());
                    skipWhitespace();
                    if (peek('}')) {
                        index++;
                        return map;
                    }
                    expect(',');
                    skipWhitespace();
                }
            }

            private List<Object> parseArray() {
                expect('[');
                List<Object> list = new ArrayList<>();
                skipWhitespace();
                if (peek(']')) {
                    index++;
                    return list;
                }
                while (true) {
                    list.add(parseValue());
                    skipWhitespace();
                    if (peek(']')) {
                        index++;
                        return list;
                    }
                    expect(',');
                }
            }

            private String parseString() {
                expect('"');
                StringBuilder out = new StringBuilder();
                while (index < text.length()) {
                    char ch = text.charAt(index++);
                    if (ch == '"') {
                        return out.toString();
                    }
                    if (ch != '\\') {
                        out.append(ch);
                        continue;
                    }
                    if (index >= text.length()) {
                        throw new IllegalArgumentException("Invalid JSON escape.");
                    }
                    char escaped = text.charAt(index++);
                    switch (escaped) {
                        case '"' -> out.append('"');
                        case '\\' -> out.append('\\');
                        case '/' -> out.append('/');
                        case 'b' -> out.append('\b');
                        case 'f' -> out.append('\f');
                        case 'n' -> out.append('\n');
                        case 'r' -> out.append('\r');
                        case 't' -> out.append('\t');
                        case 'u' -> {
                            String hex = text.substring(index, index + 4);
                            out.append((char) Integer.parseInt(hex, 16));
                            index += 4;
                        }
                        default -> throw new IllegalArgumentException("Invalid JSON escape: \\" + escaped);
                    }
                }
                throw new IllegalArgumentException("Unclosed JSON string.");
            }

            private Object parseNumber() {
                int start = index;
                while (index < text.length()) {
                    char ch = text.charAt(index);
                    if ((ch >= '0' && ch <= '9') || ch == '-' || ch == '+' || ch == '.' || ch == 'e' || ch == 'E') {
                        index++;
                    } else {
                        break;
                    }
                }
                String number = text.substring(start, index);
                if (number.contains(".") || number.contains("e") || number.contains("E")) {
                    return new BigDecimal(number);
                }
                return Long.parseLong(number);
            }

            private Object consumeLiteral(String literal, Object value) {
                if (!text.startsWith(literal, index)) {
                    throw new IllegalArgumentException("Expected " + literal + " at " + index);
                }
                index += literal.length();
                return value;
            }

            private void skipWhitespace() {
                while (index < text.length() && Character.isWhitespace(text.charAt(index))) {
                    index++;
                }
            }

            private void expect(char expected) {
                if (index >= text.length() || text.charAt(index) != expected) {
                    throw new IllegalArgumentException("Expected '" + expected + "' at " + index);
                }
                index++;
            }

            private boolean peek(char expected) {
                return index < text.length() && text.charAt(index) == expected;
            }
        }
    }
}
