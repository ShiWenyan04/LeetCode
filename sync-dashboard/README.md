# LeetCode Sync Dashboard

一个面向 leetcode.cn 的本地可视化同步工具，用来把账号里的 AC 提交同步到本地 Git 仓库，并可选择自动 commit / push 到 GitHub。

这个项目最初是为个人刷题仓库定制的，核心目标是解决一个很日常的问题：题写完了，但经常忘记把代码整理并提交到 GitHub。工具启动后可以在浏览器里选择同步范围、执行模式、批量大小，并查看实时日志。

## 功能

- 从 leetcode.cn 读取已通过题目和 AC 提交
- 自动生成 `AutoSync/{题号}-{slug}/solution.xxx`
- 为每道题生成简洁的 `README.md`
- 支持 Java、C++、C、Python、MySQL 等常见提交语言
- 支持预览、本地提交、提交并推送三种模式
- 支持全量历史分批同步，避免 leetcode.cn 限流导致长时间卡住
- 全量分批同步时，每批成功结束后会询问是否继续下一批
- 支持后台运行、实时日志、任务取消
- 推送前自动 `git pull --rebase --autostash`，减少远程分支领先导致的 push 失败
- 使用 `.leetcode-sync-state.json` 记录已同步提交，重复运行会自动跳过

## 项目结构

```text
sync-dashboard/
├── pom.xml
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
- 一个已经登录过 leetcode.cn 的浏览器账号
- 一个本地 Git 仓库，建议已配置 GitHub remote

## Cookie 配置

工具需要读取 leetcode.cn 的登录 Cookie。启动前设置两个环境变量：

```powershell
$env:LEETCODE_SESSION="your_LEETCODE_SESSION"
$env:CSRF_TOKEN="your_csrftoken"
```

这些值可以从浏览器开发者工具中获取：

```text
leetcode.cn -> DevTools -> Application -> Cookies
```

需要的字段是：

- `LEETCODE_SESSION`
- `csrftoken`

注意：不要把真实 Cookie 写进代码、README 或提交到 GitHub。

## 启动 Web 界面

在仓库根目录的 `sync-dashboard` 下启动：

```powershell
cd D:\Study\Code\LeetCode\src\sync-dashboard
mvn spring-boot:run
```

浏览器打开：

```text
http://127.0.0.1:8848
```

默认配置在：

```text
src/main/resources/application.properties
```

当前默认端口：

```properties
server.port=8848
server.address=127.0.0.1
```

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
本批最多同步：30
执行模式：写入并本地提交
调试输出：不勾选
```

全量历史建议分批跑。每批完成后检查 `AutoSync/`，确认没问题再继续下一批或推送。

在 Web 界面中，如果选择 `全量历史` 且设置了 `本批最多同步`，每批成功结束后会弹窗询问是否继续下一批。选择继续会沿用当前配置再次启动同步；选择取消则停在当前批次。

## 命令行使用

不启动 Web，也可以直接运行核心同步脚本：

```powershell
cd D:\Study\Code\LeetCode\src
java .\sync-dashboard\tools\LeetCodeCnSync.java --limit 20 --dry-run
```

常用命令：

```powershell
# 预览最近 20 条 AC 提交，不写文件
java .\sync-dashboard\tools\LeetCodeCnSync.java --limit 20 --dry-run

# 同步最近提交，生成本地 commit，但不 push
java .\sync-dashboard\tools\LeetCodeCnSync.java --limit 20 --max-sync 30 --no-push

# 同步最近提交，并自动 push
java .\sync-dashboard\tools\LeetCodeCnSync.java --limit 20 --max-sync 30

# 分批同步全部历史
java .\sync-dashboard\tools\LeetCodeCnSync.java --all --max-sync 30 --no-push
```

参数说明：

- `--all`：扫描所有已通过题目
- `--limit N`：不使用 `--all` 时，扫描最近 N 个已通过题目
- `--max-sync N`：本次最多同步 N 个新的 AC 提交，`0` 表示不限制
- `--dry-run`：只预览，不写入文件，不提交 Git
- `--no-push`：写入并生成本地 commit，但不推送
- `--debug`：输出更详细的扫描日志

## 自动定时同步

Windows 下可以创建每日定时任务：

```powershell
cd D:\Study\Code\LeetCode\src
powershell -ExecutionPolicy Bypass -File .\sync-dashboard\setup_daily_sync.ps1 -RunTime "23:30"
```

定时任务会执行最近提交同步，并限制单次同步数量，避免触发频繁限流。

如果要让定时任务稳定运行，建议把 `LEETCODE_SESSION` 和 `CSRF_TOKEN` 设置为用户环境变量，而不是只在当前终端里临时设置。

## 输出目录

默认同步到仓库根目录：

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

这个文件用来避免重复同步同一条提交。如果删除它，工具会认为所有题目都没有同步过。

## 常见问题

### leetcode.cn 提示超出访问限制

leetcode.cn 对提交详情接口有限流。工具已经内置等待和重试，但全量历史仍建议使用分批同步：

```powershell
java .\sync-dashboard\tools\LeetCodeCnSync.java --all --max-sync 30 --no-push
```

### push 被 GitHub 拒绝

如果远程分支比本地更新，工具会在 push 前自动执行：

```powershell
git pull --rebase --autostash origin main
```

如果发生文件冲突，需要手动解决冲突后再 push。

### Git 出现 index.lock

这通常是另一个 Git 进程还在运行，或者上一次 Git 操作异常退出。先关闭正在运行的同步任务，再确认没有 Git 进程后删除：

```powershell
Remove-Item -LiteralPath "D:\Study\Code\LeetCode\src\.git\index.lock"
```

### 不想提交工具，只想同步题目

可以只提交 `AutoSync/` 和 `.leetcode-sync-state.json`：

```powershell
git add AutoSync .leetcode-sync-state.json
git commit -m "sync: update leetcode solutions"
git push
```

## 后续开源计划

如果要把这个工具独立成一个开源项目，建议调整为：

```text
leetcode-sync-dashboard/
├── README.md
├── LICENSE
├── pom.xml
├── tools/
└── src/
```

可以继续补充的能力：

- 支持自定义输出目录
- 支持配置文件保存 Cookie 名称、仓库路径、同步目录
- 支持多账号或多仓库
- 支持同步统计图表
- 支持打包成可执行 jar
- 支持 Docker 或桌面启动脚本

## 安全提醒

`LEETCODE_SESSION` 和 `CSRF_TOKEN` 等价于登录凭证。不要上传到公开仓库，不要截图公开展示完整值。如果怀疑泄露，退出 leetcode.cn 并重新登录刷新 Cookie。
