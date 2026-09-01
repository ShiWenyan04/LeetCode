# LeetCode Sync Dashboard

Local Spring Boot dashboard for running `tools/LeetCodeCnSync.java` from a browser.

## Start

```powershell
cd D:\Study\Code\LeetCode\src\sync-dashboard
mvn spring-boot:run
```

Open:

```text
http://127.0.0.1:8848
```

## Environment variables

Set these before starting the dashboard:

```powershell
$env:LEETCODE_SESSION="your_LEETCODE_SESSION"
$env:CSRF_TOKEN="your_csrftoken"
```

The dashboard only shows whether the variables exist. It does not print the full cookie values.

## Direct sync command

```powershell
cd D:\Study\Code\LeetCode\src
java .\sync-dashboard\tools\LeetCodeCnSync.java --limit 20 --dry-run
```

## Daily task

```powershell
cd D:\Study\Code\LeetCode\src
powershell -ExecutionPolicy Bypass -File .\sync-dashboard\setup_daily_sync.ps1 -RunTime "23:30"
```
