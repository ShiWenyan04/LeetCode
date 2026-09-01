param(
    [string]$TaskName = "LeetCode Auto Sync",
    [string]$RunTime = "23:30"
)

$repo = (git rev-parse --show-toplevel).Trim()
$java = (Get-Command java).Source
$script = Join-Path $repo "sync-dashboard\tools\LeetCodeCnSync.java"

if (-not (Test-Path $script)) {
    throw "Cannot find sync script: $script"
}

$action = New-ScheduledTaskAction `
    -Execute $java `
    -Argument "`"$script`" --limit 80 --max-sync 30" `
    -WorkingDirectory $repo

$trigger = New-ScheduledTaskTrigger -Daily -At $RunTime

Register-ScheduledTask `
    -TaskName $TaskName `
    -Action $action `
    -Trigger $trigger `
    -Description "Sync accepted leetcode.cn submissions to GitHub every day." `
    -Force

Write-Host "Created daily task '$TaskName' at $RunTime."
Write-Host "Important: set LEETCODE_SESSION and CSRF_TOKEN as user environment variables before relying on the scheduled task."
