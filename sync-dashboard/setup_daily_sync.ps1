param(
    [string]$TaskName = "LeetCode Auto Sync",
    [string]$RunTime = "23:30",
    [string]$RepoRoot = "",
    [string]$OutputDir = "AutoSync"
)

$toolRoot = $PSScriptRoot
if ([string]::IsNullOrWhiteSpace($RepoRoot)) {
    $RepoRoot = (Resolve-Path (Join-Path $toolRoot "..")).Path
}

$java = (Get-Command java).Source
$script = Join-Path $toolRoot "tools\LeetCodeCnSync.java"

if (-not (Test-Path $script)) {
    throw "Cannot find sync script: $script"
}
if (-not (Test-Path (Join-Path $RepoRoot ".git"))) {
    throw "RepoRoot must be a git repository: $RepoRoot"
}

$action = New-ScheduledTaskAction `
    -Execute $java `
    -Argument "`"$script`" --repo-root `"$RepoRoot`" --output-dir `"$OutputDir`" --limit 80 --max-sync 30" `
    -WorkingDirectory $RepoRoot

$trigger = New-ScheduledTaskTrigger -Daily -At $RunTime

Register-ScheduledTask `
    -TaskName $TaskName `
    -Action $action `
    -Trigger $trigger `
    -Description "Sync accepted leetcode.cn submissions to GitHub every day." `
    -Force

Write-Host "Created daily task '$TaskName' at $RunTime."
Write-Host "Repo root: $RepoRoot"
Write-Host "Output dir: $OutputDir"
Write-Host "Important: set LEETCODE_SESSION and CSRF_TOKEN as user environment variables before relying on the scheduled task."
