# LeetCode

This repository stores my LeetCode solutions.

Existing folders:

- `Commit/`: solutions previously exported from LeetCode.
- `Java/`: solutions written locally in IDEA.
- `Cpp/`: solutions written locally in CLion / Visual Studio.
- `AutoSync/`: solutions synchronized automatically from leetcode.cn.

## Auto sync

Set these environment variables before running the sync script:

```powershell
$env:LEETCODE_SESSION="your_LEETCODE_SESSION"
$env:CSRF_TOKEN="your_csrftoken"
```

Then run:

```powershell
python .\scripts\sync_leetcode_cn.py --all
```

For daily updates:

```powershell
python .\scripts\sync_leetcode_cn.py
```

