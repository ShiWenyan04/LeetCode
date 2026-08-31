param(
    [string]$TaskName = "LeetCode Auto Sync",
    [string]$RunTime = "23:30"
)

$repo = (git rev-parse --show-toplevel).Trim()
$python = (Get-Command python).Source
$script = Join-Path $repo "scripts\sync_leetcode_cn.py"
$log = Join-Path $repo "leetcode-sync.log"

if (-not (Test-Path $script)) {
    throw "Cannot find sync script: $script"
}

$action = New-ScheduledTaskAction `
    -Execute $python `
    -Argument "`"$script`" --limit 80" `
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

