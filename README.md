# LeetCode Solutions

这是我的力扣刷题仓库，用来记录在 [leetcode.cn](https://leetcode.cn/) 上完成的算法题、数据库题和日常练习代码。

仓库里的题解来源主要分为两类：一部分是早期手动整理或从力扣导出的代码，另一部分是通过本地同步工具自动从 leetcode.cn 拉取的 AC 提交。后续会逐步把题解整理成更统一的目录结构和 README 格式。

## 目录结构

```text
.
├── AutoSync/        # 自动同步后的 AC 题解，每题一个目录
├── Commit/          # 早期从力扣导出或手动提交的题解
├── Java/            # 在 IDEA 中编写的 Java 练习代码
├── Cpp/             # 在 CLion / Visual Studio 中编写的 C/C++ 练习代码
└── sync-dashboard/  # leetcode.cn 可视化同步工具
```

## 语言

目前主要包含：

- Java
- C++
- C
- Python
- MySQL

其中 Java 使用最多，C/C++ 和 SQL 会根据题目类型补充。

## AutoSync 题解格式

`AutoSync/` 目录由同步工具生成，结构大致如下：

```text
AutoSync/
└── 1-two-sum/
    ├── README.md
    └── solution.java
```

每个题目目录通常包含：

- `solution.xxx`：对应语言的 AC 代码
- `README.md`：题目编号、语言、提交时间、题目链接、提交链接

## 同步工具

本仓库包含一个本地可视化同步工具，用于把 leetcode.cn 的 AC 提交同步到 `AutoSync/`，并自动提交到 GitHub。

工具位置：

```text
sync-dashboard/
```

使用说明见：

```text
sync-dashboard/README.md
```

启动可视化工具：

```powershell
cd D:\Study\Code\LeetCode\src\sync-dashboard
mvn spring-boot:run
```

日常使用推荐流程：

```text
同步范围：最近提交
最近数量：20
本批最多同步：30
执行模式：写入、提交并推送
```

## 说明

这个仓库主要用于个人学习记录。部分早期题解文件命名和格式还不统一，后续会逐步整理；自动同步生成的新题解会优先放在 `AutoSync/` 中。
