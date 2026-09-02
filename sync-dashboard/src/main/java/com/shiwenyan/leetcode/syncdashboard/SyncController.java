package com.shiwenyan.leetcode.syncdashboard;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
public class SyncController {
    private static final Duration SYNC_TIMEOUT = Duration.ofHours(4);
    private static final int MAX_LOG_CHARS = 300_000;
    private static final Path CONFIG_PATH = Path.of(System.getProperty("user.home"), ".leetcode-sync-dashboard", "config.json");

    private final Path appRoot;
    private final String defaultRepoRoot;
    private final String defaultSyncScript;
    private final String defaultOutputDir;
    private final ObjectMapper mapper = new ObjectMapper();
    private final Map<String, SyncJob> jobs = new ConcurrentHashMap<>();
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private volatile DashboardConfig config;

    public SyncController(
            @Value("${leetcode.repo-root:..}") String repoRoot,
            @Value("${leetcode.sync-script:tools/LeetCodeCnSync.java}") String syncScript,
            @Value("${leetcode.output-dir:AutoSync}") String outputDir
    ) {
        this.appRoot = Path.of("").toAbsolutePath().normalize();
        this.defaultRepoRoot = blankToDefault(repoRoot, "..");
        this.defaultSyncScript = blankToDefault(syncScript, "tools/LeetCodeCnSync.java");
        this.defaultOutputDir = blankToDefault(outputDir, "AutoSync");
        this.config = loadConfig();
    }

    @GetMapping("/status")
    public DashboardStatus status() {
        EffectiveConfig effective = effectiveConfig();
        Path repoRoot = effective.repoRoot();
        Path syncScript = effective.syncScript();
        return new DashboardStatus(
                repoRoot.toString(),
                Files.exists(syncScript),
                effective.outputDir(),
                credentialPresent("LEETCODE_SESSION", effective.leetcodeSession()),
                credentialPresent("CSRF_TOKEN", effective.csrfToken()) || envPresent("csrftoken"),
                mask(firstNonBlank(effective.leetcodeSession(), System.getenv("LEETCODE_SESSION"))),
                mask(firstNonBlank(effective.csrfToken(), System.getenv("CSRF_TOKEN"), System.getenv("csrftoken"))),
                gitBranch(repoRoot),
                gitDirtyCount(repoRoot),
                autoSyncProblemCount(repoRoot, effective.outputDir()),
                syncedSubmissionCount(repoRoot),
                Instant.now().toString()
        );
    }

    @GetMapping("/config")
    public ConfigResponse config() {
        EffectiveConfig effective = effectiveConfig();
        return new ConfigResponse(
                effective.repoRoot().toString(),
                effective.outputDir(),
                effective.syncScript().toString(),
                CONFIG_PATH.toString(),
                credentialPresent("LEETCODE_SESSION", effective.leetcodeSession()),
                credentialPresent("CSRF_TOKEN", effective.csrfToken()) || envPresent("csrftoken"),
                mask(firstNonBlank(effective.leetcodeSession(), System.getenv("LEETCODE_SESSION"))),
                mask(firstNonBlank(effective.csrfToken(), System.getenv("CSRF_TOKEN"), System.getenv("csrftoken")))
        );
    }

    @PostMapping("/config")
    public ConfigResponse saveConfig(@RequestBody DashboardConfig next) throws IOException {
        DashboardConfig safe = next == null ? new DashboardConfig("", "", "", "", "") : next;
        this.config = new DashboardConfig(
                blankToDefault(safe.repoRoot(), defaultRepoRoot),
                blankToDefault(safe.outputDir(), defaultOutputDir),
                blankToDefault(safe.syncScript(), defaultSyncScript),
                safe.leetcodeSession(),
                safe.csrfToken()
        );
        Files.createDirectories(CONFIG_PATH.getParent());
        mapper.writerWithDefaultPrettyPrinter().writeValue(CONFIG_PATH.toFile(), this.config);
        return config();
    }

    @GetMapping("/diagnostics")
    public DiagnosticsResponse diagnostics() {
        EffectiveConfig effective = effectiveConfig();
        Path repoRoot = effective.repoRoot();
        Path syncScript = effective.syncScript();
        List<DiagnosticItem> items = new ArrayList<>();

        items.add(checkCommand("java", List.of("java", "-version"), appRoot, "Java", "安装 JDK 17+，并确认 java 已加入 PATH。"));
        items.add(checkCommand("mvn", List.of("mvn", "-version"), appRoot, "Maven", "安装 Maven，或在 IDE 中使用 Maven 面板启动。"));
        items.add(checkCommand("git", List.of("git", "--version"), appRoot, "Git", "安装 Git，并确认 git 已加入 PATH。"));
        items.add(new DiagnosticItem(
                "repo_exists",
                "题解仓库目录",
                Files.isDirectory(repoRoot),
                Files.isDirectory(repoRoot) ? "ok" : "error",
                repoRoot.toString(),
                "在配置里把题解仓库路径改成真实存在的目录。"
        ));
        items.add(new DiagnosticItem(
                "sync_script",
                "同步脚本",
                Files.exists(syncScript),
                Files.exists(syncScript) ? "ok" : "error",
                syncScript.toString(),
                "通常保持 tools/LeetCodeCnSync.java；如果移动了文件，请同步修改配置。"
        ));
        items.add(new DiagnosticItem(
                "output_dir",
                "输出目录",
                isSafeRelativePath(effective.outputDir()),
                isSafeRelativePath(effective.outputDir()) ? "ok" : "error",
                effective.outputDir(),
                "输出目录必须是相对路径，例如 AutoSync，不能是绝对路径或 ..。"
        ));
        items.add(checkGitRepo(repoRoot));
        items.add(checkOrigin(repoRoot));
        items.add(checkIndexLock(repoRoot));
        items.add(new DiagnosticItem(
                "leetcode_session",
                "LEETCODE_SESSION",
                credentialPresent("LEETCODE_SESSION", effective.leetcodeSession()),
                credentialPresent("LEETCODE_SESSION", effective.leetcodeSession()) ? "ok" : "error",
                mask(firstNonBlank(effective.leetcodeSession(), System.getenv("LEETCODE_SESSION"))),
                "在配置区保存 Cookie，或在启动 Maven 的终端里设置环境变量。"
        ));
        items.add(new DiagnosticItem(
                "csrf_token",
                "CSRF_TOKEN",
                credentialPresent("CSRF_TOKEN", effective.csrfToken()) || envPresent("csrftoken"),
                credentialPresent("CSRF_TOKEN", effective.csrfToken()) || envPresent("csrftoken") ? "ok" : "error",
                mask(firstNonBlank(effective.csrfToken(), System.getenv("CSRF_TOKEN"), System.getenv("csrftoken"))),
                "从 leetcode.cn 的 csrftoken Cookie 复制并保存。"
        ));

        int errors = (int) items.stream().filter(item -> "error".equals(item.severity())).count();
        int warnings = (int) items.stream().filter(item -> "warn".equals(item.severity())).count();
        return new DiagnosticsResponse(errors == 0, errors, warnings, items, Instant.now().toString());
    }

    @PostMapping("/command")
    public CommandResponse command(@RequestBody SyncRequest request) {
        return new CommandResponse(String.join(" ", buildCommand(request, effectiveConfig())));
    }

    @PostMapping("/sync")
    public SyncJobSnapshot sync(@RequestBody SyncRequest request) {
        EffectiveConfig effective = effectiveConfig();
        List<String> command = buildCommand(request, effective);
        SyncJob job = new SyncJob(UUID.randomUUID().toString(), String.join(" ", command));
        jobs.put(job.id, job);
        executor.execute(() -> runSyncJob(job, command, effective));
        return job.snapshot();
    }

    @GetMapping("/sync/{jobId}")
    public ResponseEntity<SyncJobSnapshot> syncJob(@PathVariable String jobId) {
        SyncJob job = jobs.get(jobId);
        if (job == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(job.snapshot());
    }

    @PostMapping("/sync/{jobId}/cancel")
    public ResponseEntity<SyncJobSnapshot> cancelSyncJob(@PathVariable String jobId) {
        SyncJob job = jobs.get(jobId);
        if (job == null) {
            return ResponseEntity.notFound().build();
        }
        job.cancel();
        return ResponseEntity.ok(job.snapshot());
    }

    private void runSyncJob(SyncJob job, List<String> command, EffectiveConfig effective) {
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.directory(effective.repoRoot().toFile());
        applyCredentials(builder, effective);

        job.markStarted();
        try {
            Process process = builder.start();
            job.process = process;
            Future<?> stdoutReader = executor.submit(() -> readStream(process.getInputStream(), job::appendStdout));
            Future<?> stderrReader = executor.submit(() -> readStream(process.getErrorStream(), job::appendStderr));

            boolean finished = process.waitFor(SYNC_TIMEOUT.toSeconds(), TimeUnit.SECONDS);
            if (!finished) {
                process.destroyForcibly();
                job.appendStderr("Sync timed out after " + SYNC_TIMEOUT.toMinutes() + " minutes.");
                job.markFinished(false, -1);
                return;
            }

            waitForReader(stdoutReader);
            waitForReader(stderrReader);
            int exitCode = process.exitValue();
            job.markFinished(exitCode == 0, exitCode);
        } catch (IOException ex) {
            job.appendStderr(ex.toString());
            job.markFinished(false, -1);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            job.appendStderr("Interrupted while running sync.");
            job.markFinished(false, -1);
        }
    }

    private void applyCredentials(ProcessBuilder builder, EffectiveConfig effective) {
        if (!isBlank(effective.leetcodeSession())) {
            builder.environment().put("LEETCODE_SESSION", effective.leetcodeSession());
        }
        if (!isBlank(effective.csrfToken())) {
            builder.environment().put("CSRF_TOKEN", effective.csrfToken());
            builder.environment().put("csrftoken", effective.csrfToken());
        }
    }

    private void readStream(InputStream stream, LogAppender appender) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, Charset.defaultCharset()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                appender.append(line + System.lineSeparator());
            }
        } catch (IOException ex) {
            appender.append(ex + System.lineSeparator());
        }
    }

    private void waitForReader(Future<?> reader) {
        try {
            reader.get(2, TimeUnit.SECONDS);
        } catch (Exception ignored) {
        }
    }

    private List<String> buildCommand(SyncRequest request, EffectiveConfig effective) {
        SyncRequest safe = request == null ? new SyncRequest("recent", 20, 30, "preview", true, false) : request;
        String scope = blankToDefault(safe.scope(), "recent");
        String mode = blankToDefault(safe.mode(), "preview");
        int limit = safe.limit() == null ? 20 : Math.max(1, Math.min(5000, safe.limit()));
        int maxSync = safe.maxSync() == null ? 30 : Math.max(0, Math.min(5000, safe.maxSync()));

        if (!List.of("recent", "all").contains(scope)) {
            throw new IllegalArgumentException("scope must be recent or all.");
        }
        if (!List.of("preview", "local", "push").contains(mode)) {
            throw new IllegalArgumentException("mode must be preview, local, or push.");
        }

        List<String> command = new ArrayList<>();
        command.add("java");
        command.add(effective.syncScript().toString());
        command.add("--repo-root");
        command.add(effective.repoRoot().toString());
        command.add("--output-dir");
        command.add(effective.outputDir());
        if ("all".equals(scope)) {
            command.add("--all");
        } else {
            command.add("--limit");
            command.add(String.valueOf(limit));
        }
        if ("preview".equals(mode)) {
            command.add("--dry-run");
        }
        if ("local".equals(mode)) {
            command.add("--no-push");
        }
        if (maxSync > 0) {
            command.add("--max-sync");
            command.add(String.valueOf(maxSync));
        }
        if (Boolean.TRUE.equals(safe.debug())) {
            command.add("--debug");
        }
        if (Boolean.TRUE.equals(safe.resetCursor())) {
            command.add("--reset-cursor");
        }
        return command;
    }

    private DashboardConfig loadConfig() {
        if (!Files.exists(CONFIG_PATH)) {
            return new DashboardConfig("", "", "", "", "");
        }
        try {
            return mapper.readValue(CONFIG_PATH.toFile(), DashboardConfig.class);
        } catch (IOException ignored) {
            return new DashboardConfig("", "", "", "", "");
        }
    }

    private EffectiveConfig effectiveConfig() {
        DashboardConfig current = config == null ? new DashboardConfig("", "", "", "", "") : config;
        String repoRootSetting = blankToDefault(current.repoRoot(), defaultRepoRoot);
        String outputDir = normalizeOutputDir(blankToDefault(current.outputDir(), defaultOutputDir));
        String syncScriptSetting = blankToDefault(current.syncScript(), defaultSyncScript);
        return new EffectiveConfig(
                resolvePath(repoRootSetting, appRoot),
                outputDir,
                resolvePath(syncScriptSetting, appRoot),
                current.leetcodeSession(),
                current.csrfToken()
        );
    }

    private int autoSyncProblemCount(Path repoRoot, String outputDir) {
        Path autoSync = repoRoot.resolve(outputDir).normalize();
        if (!Files.isDirectory(autoSync)) {
            return 0;
        }
        try (var stream = Files.list(autoSync)) {
            return (int) stream.filter(Files::isDirectory).count();
        } catch (IOException ignored) {
            return 0;
        }
    }

    private int syncedSubmissionCount(Path repoRoot) {
        Path state = repoRoot.resolve(".leetcode-sync-state.json");
        if (!Files.exists(state)) {
            return 0;
        }
        try {
            String text = Files.readString(state, StandardCharsets.UTF_8);
            Matcher matcher = Pattern.compile("\"synced_submission_ids\"\\s*:\\s*\\[(.*?)]", Pattern.DOTALL).matcher(text);
            if (!matcher.find()) {
                return 0;
            }
            String ids = matcher.group(1).trim();
            if (ids.isEmpty()) {
                return 0;
            }
            return (int) Pattern.compile("\"[^\"]+\"").matcher(ids).results().count();
        } catch (IOException ignored) {
            return 0;
        }
    }

    private DiagnosticItem checkCommand(String key, List<String> command, Path cwd, String label, String fix) {
        ProcessResult result = runQuietly(command, cwd);
        boolean ok = result.exitCode() == 0;
        String detail = firstNonBlank(firstLine(result.stdout()), firstLine(result.stderr()), ok ? "已安装" : "不可用");
        return new DiagnosticItem(key, label, ok, ok ? "ok" : "error", detail, fix);
    }

    private DiagnosticItem checkGitRepo(Path repoRoot) {
        ProcessResult result = runQuietly(List.of("git", "rev-parse", "--is-inside-work-tree"), repoRoot);
        boolean ok = result.exitCode() == 0 && result.stdout().trim().equals("true");
        return new DiagnosticItem(
                "git_repo",
                "Git 仓库",
                ok,
                ok ? "ok" : "error",
                ok ? "当前目录是 Git 仓库" : firstNonBlank(result.stderr().trim(), "当前目录不是 Git 仓库"),
                "目标目录需要先 git init，或者配置为已经 clone 下来的仓库。"
        );
    }

    private DiagnosticItem checkOrigin(Path repoRoot) {
        ProcessResult result = runQuietly(List.of("git", "remote", "get-url", "origin"), repoRoot);
        boolean ok = result.exitCode() == 0 && !result.stdout().isBlank();
        return new DiagnosticItem(
                "origin",
                "GitHub origin",
                ok,
                ok ? "ok" : "warn",
                ok ? result.stdout().trim() : "未配置 origin",
                "使用 git remote add origin https://github.com/your-name/your-repo.git。"
        );
    }

    private DiagnosticItem checkIndexLock(Path repoRoot) {
        Path lock = repoRoot.resolve(".git").resolve("index.lock");
        boolean ok = !Files.exists(lock);
        return new DiagnosticItem(
                "index_lock",
                "Git index.lock",
                ok,
                ok ? "ok" : "error",
                ok ? "未发现锁文件" : lock.toString(),
                "先停止同步任务并确认没有 Git 进程，再删除 .git/index.lock。"
        );
    }

    private int gitDirtyCount(Path repoRoot) {
        String status = runQuietly(List.of("git", "status", "--short"), repoRoot).stdout();
        if (status.isBlank()) {
            return 0;
        }
        return (int) status.lines().filter(line -> !line.isBlank()).count();
    }

    private String gitBranch(Path repoRoot) {
        return runQuietly(List.of("git", "branch", "--show-current"), repoRoot).stdout().trim();
    }

    private ProcessResult runQuietly(List<String> command, Path cwd) {
        try {
            Process process = new ProcessBuilder(command)
                    .directory(cwd.toFile())
                    .redirectErrorStream(false)
                    .start();
            StreamReader stdoutReader = new StreamReader(process.getInputStream());
            StreamReader stderrReader = new StreamReader(process.getErrorStream());
            stdoutReader.start();
            stderrReader.start();
            boolean finished = process.waitFor(10, TimeUnit.SECONDS);
            if (!finished) {
                process.destroyForcibly();
                return new ProcessResult(-1, stdoutReader.output(), "command timed out");
            }
            stdoutReader.join(1_000);
            stderrReader.join(1_000);
            return new ProcessResult(process.exitValue(), stdoutReader.output(), stderrReader.output());
        } catch (IOException ex) {
            return new ProcessResult(-1, "", ex.getMessage());
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            return new ProcessResult(-1, "", "interrupted");
        }
    }

    private static String normalizeOutputDir(String value) {
        String output = blankToDefault(value, "AutoSync").replace('\\', '/').trim();
        return output.isBlank() ? "AutoSync" : output;
    }

    private static boolean isSafeRelativePath(String value) {
        if (isBlank(value)) {
            return false;
        }
        Path path = Path.of(value);
        String normalized = path.normalize().toString();
        return !path.isAbsolute() && !normalized.startsWith("..") && !".".equals(normalized);
    }

    private static boolean credentialPresent(String envKey, String configValue) {
        return !isBlank(configValue) || !isBlank(System.getenv(envKey));
    }

    private boolean envPresent(String key) {
        return !isBlank(System.getenv(key));
    }

    private static String mask(String value) {
        if (isBlank(value)) {
            return "";
        }
        int keep = Math.min(6, value.length());
        return value.substring(0, keep) + "...(" + value.length() + ")";
    }

    private static String firstLine(String value) {
        if (isBlank(value)) {
            return "";
        }
        return value.lines().findFirst().orElse("").trim();
    }

    private static String blankToDefault(String value, String fallback) {
        return isBlank(value) ? fallback : value;
    }

    private static Path resolvePath(String value, Path base) {
        Path path = Path.of(blankToDefault(value, "."));
        if (!path.isAbsolute()) {
            path = base.resolve(path);
        }
        return path.normalize();
    }

    private static String firstNonBlank(String... values) {
        for (String value : values) {
            if (!isBlank(value)) {
                return value;
            }
        }
        return "";
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public record SyncRequest(String scope, Integer limit, Integer maxSync, String mode, Boolean debug, Boolean resetCursor) {
    }

    public record DashboardConfig(String repoRoot, String outputDir, String syncScript, String leetcodeSession, String csrfToken) {
    }

    private record EffectiveConfig(Path repoRoot, String outputDir, Path syncScript, String leetcodeSession, String csrfToken) {
    }

    private record ProcessResult(int exitCode, String stdout, String stderr) {
    }

    public record CommandResponse(String command) {
    }

    public record ConfigResponse(
            String repoRoot,
            String outputDir,
            String syncScript,
            String configPath,
            boolean leetcodeSessionPresent,
            boolean csrfTokenPresent,
            String leetcodeSessionMask,
            String csrfTokenMask
    ) {
    }

    public record DashboardStatus(
            String repoRoot,
            boolean syncScriptFound,
            String outputDir,
            boolean leetcodeSessionPresent,
            boolean csrfTokenPresent,
            String leetcodeSessionMask,
            String csrfTokenMask,
            String branch,
            int dirtyFileCount,
            int autoSyncProblemCount,
            int syncedSubmissionCount,
            String checkedAt
    ) {
    }

    public record DiagnosticsResponse(
            boolean ok,
            int errors,
            int warnings,
            List<DiagnosticItem> items,
            String checkedAt
    ) {
    }

    public record DiagnosticItem(String key, String label, boolean ok, String severity, String detail, String fix) {
    }

    public record SyncJobSnapshot(
            String jobId,
            boolean running,
            boolean success,
            Integer exitCode,
            String command,
            String stdout,
            String stderr,
            String startedAt,
            String finishedAt,
            String checkedAt
    ) {
    }

    @FunctionalInterface
    private interface LogAppender {
        void append(String text);
    }

    private static final class StreamReader extends Thread {
        private final InputStream stream;
        private final StringBuilder out = new StringBuilder();

        private StreamReader(InputStream stream) {
            this.stream = stream;
            setDaemon(true);
        }

        @Override
        public void run() {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, Charset.defaultCharset()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    synchronized (out) {
                        out.append(line).append(System.lineSeparator());
                    }
                }
            } catch (IOException ex) {
                synchronized (out) {
                    out.append(ex).append(System.lineSeparator());
                }
            }
        }

        private String output() {
            synchronized (out) {
                return out.toString();
            }
        }
    }

    private static class SyncJob {
        private final String id;
        private final String command;
        private final StringBuilder stdout = new StringBuilder();
        private final StringBuilder stderr = new StringBuilder();
        private volatile Process process;
        private volatile boolean running;
        private volatile boolean success;
        private volatile Integer exitCode;
        private volatile String startedAt;
        private volatile String finishedAt;

        SyncJob(String id, String command) {
            this.id = id;
            this.command = command;
        }

        void markStarted() {
            this.startedAt = Instant.now().toString();
            this.running = true;
        }

        void markFinished(boolean success, int exitCode) {
            this.success = success;
            this.exitCode = exitCode;
            this.finishedAt = Instant.now().toString();
            this.running = false;
        }

        void cancel() {
            Process current = process;
            if (current != null && current.isAlive()) {
                current.destroyForcibly();
                appendStderr("Cancelled by user." + System.lineSeparator());
                markFinished(false, -1);
            }
        }

        synchronized void appendStdout(String text) {
            append(stdout, text);
        }

        synchronized void appendStderr(String text) {
            append(stderr, text);
        }

        synchronized SyncJobSnapshot snapshot() {
            return new SyncJobSnapshot(
                    id,
                    running,
                    success,
                    exitCode,
                    command,
                    stdout.toString(),
                    stderr.toString(),
                    startedAt,
                    finishedAt,
                    Instant.now().toString()
            );
        }

        private void append(StringBuilder builder, String text) {
            builder.append(text);
            if (builder.length() > MAX_LOG_CHARS) {
                builder.delete(0, builder.length() - MAX_LOG_CHARS);
            }
        }
    }
}
