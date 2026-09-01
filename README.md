# LeetCode

这个仓库脚本主要是为了把我的力扣题目提交到github上面而设计的

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

Run the Java sync script:

```powershell
java .\scripts\LeetCodeCnSync.java --limit 20 --dry-run --debug
java .\scripts\LeetCodeCnSync.java --all
```

For daily updates:

```powershell
mvn spring-boot:run
```
