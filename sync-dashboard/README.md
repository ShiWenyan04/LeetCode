# LeetCode Sync Dashboard

一个面向 `leetcode.cn` 的本地可视化同步工具，用来把账号里的 AC 提交同步到本地 Git 题解仓库，并可选择自动 `commit` / `push` 到 GitHub。

它适合两种场景：

- 第一次整理历史题解：分批拉取所有已通过题目，自动生成目录和 README
- 日常刷题闭环：刷完题后同步最近提交，自动写入、提交并推送

## 功能

- 读取 `leetcode.cn` 已通过题目和 AC 提交
- 生成 `AutoSync/{题号}-{slug}/solution.xxx`
- 为每道题生成简洁的 `README.md`
- 支持 Java、C++、C、Python、MySQL 等常见提交语言
- 支持预览、本地提交、自动推送三种模式
- 全量历史支持分批同步和断点续扫
- 同步结束后可继续下一批
- 页面内保存本地配置：题解仓库、输出目录、同步脚本、Cookie
- 页面内置诊断：Java、Maven、Git、仓库、origin、Cookie、`index.lock`
- 推送前自动执行 `git pull --rebase --autostash`
- 使用 `.leetcode-sync-state.json` 记录同步状态，避免重复拉取

## 项目结构

```text
leetcode-sync-dashboard/
├── .gitignore
├── pom.xml
├── README.md
├── setup_daily_sync.ps1
├── tools/
│   └── LeetCodeCnSync.java
└── src/main/
    ├── java/com/shiwenyan/leetcode/syncdashboard/
    │   ├── LeetCodeSyncDashboardApplication.java
    │   └── SyncController.java
    └── resources/
        ├── application.properties
        └── static/index.html
```

## 环境要求

- JDK 17 或更高版本
- Maven 3.8 或更高版本
- Git
- 一个已经登录过 `leetcode.cn` 的账号
- 一个本地 Git 题解仓库，建议已经配置 GitHub `origin`

## 启动

进入本项目目录：

```powershell
cd D:\Study\Code\leetcode-sync-dashboard
mvn spring-boot:run
```

浏览器打开：

```text
http://127.0.0.1:8848
```

如果端口被占用，可以换端口：

```powershell
mvn spring-boot:run '-Dspring-boot.run.arguments=--server.port=8899'
```

## 配置

启动后可以直接在页面左侧“本地配置”里填写并保存：

- 题解仓库路径：代码要保存到哪个本地 Git 仓库
- 输出目录：默认 `AutoSync`
- 同步脚本路径：默认 `tools/LeetCodeCnSync.java`
- `LEETCODE_SESSION`
- `CSRF_TOKEN`

配置会保存到用户目录：

```text
C:\Users\<你的用户名>\.leetcode-sync-dashboard\config.json
```

这个文件只在本机使用，不要提交到 GitHub。

也可以继续使用环境变量：

```powershell
$env:LEETCODE_SESSION="your_LEETCODE_SESSION"
$env:CSRF_TOKEN="your_csrftoken"
```

Cookie 可以从浏览器开发者工具获取：

```text
leetcode.cn -> DevTools -> Application -> Cookies
```

需要的字段是：

- `LEETCODE_SESSION`
- `csrftoken`

## application.properties

默认配置在：

```text
src/main/resources/application.properties
```

如果工具目录放在题解仓库内部，推荐：

```properties
leetcode.repo-root=..
leetcode.output-dir=AutoSync
leetcode.sync-script=tools/LeetCodeCnSync.java
```

如果工具作为独立项目运行，推荐使用绝对路径：

```properties
leetcode.repo-root=D:/your/path/leetcode-solutions
leetcode.output-dir=AutoSync
leetcode.sync-script=tools/LeetCodeCnSync.java
```

页面里保存的配置优先级高于 `application.properties`。

## 推荐用法

日常刷题后同步最近提交：

```text
同步范围：最近提交
最近数量：20
本批最多同步：30
执行模式：写入、提交并推送
调试输出：不勾选
```

第一次补全历史题目：

```text
同步范围：全量历史
本批最多同步：30 或 50
执行模式：写入、提交并推送
调试输出：不勾选
```

全量历史会记录扫描游标。本批同步结束后，下一批会从上次位置继续，不再每次都从第 1 道已通过题目开始扫。

如果你想重新从头扫描全量历史，可以勾选：

```text
重置全量扫描游标
```

## 命令行使用

不启动 Web，也可以直接运行核心脚本：

```powershell
cd D:\Study\Code\leetcode-sync-dashboard

java .\tools\LeetCodeCnSync.java `
  --repo-root D:\Study\Code\LeetCode `
  --output-dir AutoSync `
  --limit 20 `
  --dry-run
```

常用命令：

```powershell
# 预览最近 20 条 AC 提交，不写文件
java .\tools\LeetCodeCnSync.java --repo-root D:\Study\Code\LeetCode --output-dir AutoSync --limit 20 --dry-run

# 同步最近提交，生成本地 commit，但不 push
java .\tools\LeetCodeCnSync.java --repo-root D:\Study\Code\LeetCode --output-dir AutoSync --limit 20 --max-sync 30 --no-push

# 同步最近提交，并自动 push
java .\tools\LeetCodeCnSync.java --repo-root D:\Study\Code\LeetCode --output-dir AutoSync --limit 20 --max-sync 30

# 分批同步全部历史
java .\tools\LeetCodeCnSync.java --repo-root D:\Study\Code\LeetCode --output-dir AutoSync --all --max-sync 30

# 重置全量扫描游标
java .\tools\LeetCodeCnSync.java --repo-root D:\Study\Code\LeetCode --output-dir AutoSync --all --max-sync 30 --reset-cursor
```

参数说明：

- `--all`：扫描所有已通过题目
- `--limit N`：不使用 `--all` 时，扫描最近 N 个已通过题目
- `--max-sync N`：本次最多同步 N 个新的 AC 提交，`0` 表示不限制
- `--repo-root P`：目标题解 Git 仓库路径
- `--output-dir D`：题解输出目录，相对于 `--repo-root`
- `--dry-run`：只预览，不写入文件，不提交 Git
- `--no-push`：写入并生成本地 commit，但不推送
- `--debug`：输出更详细的扫描日志
- `--reset-cursor`：重置全量历史扫描游标

## 输出目录

默认同步到题解仓库根目录：

```text
AutoSync/
```

示例：

```text
AutoSync/
└── 1-two-sum/
    ├── README.md
    └── solution.java
```

同步状态记录在：

```text
.leetcode-sync-state.json
```

它会记录：

- 已同步过的 submission id
- 全量历史扫描游标
- 最近同步时间

删除这个文件后，工具会认为所有题目都没有同步过。

## 诊断

页面右侧“诊断”会检查：

- Java 是否可用
- Maven 是否可用
- Git 是否可用
- 题解仓库目录是否存在
- 题解仓库是否是 Git 仓库
- `origin` 是否配置
- 同步脚本是否存在
- 输出目录是否安全
- Cookie 是否已配置
- 是否存在 `.git/index.lock`

遇到问题时，诊断项会给出对应修复建议。

## 自动定时同步

Windows 下可以创建每日定时任务：

```powershell
cd D:\Study\Code\leetcode-sync-dashboard

powershell -ExecutionPolicy Bypass -File .\setup_daily_sync.ps1 `
  -RepoRoot "D:\Study\Code\LeetCode" `
  -OutputDir "AutoSync" `
  -RunTime "23:30"
```

定时任务建议只跑“最近提交”，避免历史全量触发频繁限流。

## 常见问题

### 为什么全量历史会显示 419？

这是 `leetcode.cn` 返回的账号已 AC 题目总数。全量模式需要知道历史题目列表，`--max-sync 30` 或 `--max-sync 50` 限制的是本批最多写入多少个新提交，不是账号总题数。

现在工具已经支持全量扫描游标，下一批会从上次位置继续。

### leetcode.cn 提示超出访问限制

提交详情接口比较容易限流。工具内置了等待和重试，但全量历史仍建议分批执行：

```powershell
java .\tools\LeetCodeCnSync.java --repo-root D:\Study\Code\LeetCode --output-dir AutoSync --all --max-sync 30
```

如果连续被限流，等几分钟后再继续。

### GitHub 返回 502

这通常是 GitHub 或本地网络临时异常。题解已经保存在本地并生成 commit 时，直接重试：

```powershell
git push
```

### Git 出现 index.lock

先停止同步任务，并确认没有其他 Git 命令在运行。确认安全后删除：

```powershell
Remove-Item -LiteralPath "D:\Study\Code\LeetCode\.git\index.lock"
```

### 当前分支没有 upstream

执行一次：

```powershell
git push --set-upstream origin main
```

或者：

```powershell
git config --global push.autoSetupRemote true
```

### 不想提交工具，只想提交题解

只提交题解目录和同步状态：

```powershell
git add AutoSync .leetcode-sync-state.json
git commit -m "sync: update leetcode solutions"
git push
```

## 安全提醒

`LEETCODE_SESSION` 和 `CSRF_TOKEN` 等价于登录凭证。不要上传到公开仓库，不要截图公开展示完整值。如果怀疑泄露，退出 `leetcode.cn` 并重新登录刷新 Cookie。
