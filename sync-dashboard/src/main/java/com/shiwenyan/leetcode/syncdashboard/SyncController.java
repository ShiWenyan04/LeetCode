package com.shiwenyan.leetcode.syncdashboard;

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

    private final Path repoRoot;
    private final String syncScript;
    private final Map<String, SyncJob> jobs = new ConcurrentHashMap<>();
    private final ExecutorService executor = Executors.newCachedThreadPool();

    public SyncController(
            @Value("${leetcode.repo-root:..}") String repoRoot,
            @Value("${leetcode.sync-script:sync-dashboard/tools/LeetCodeCnSync.java}") String syncScript
    ) {
        this.repoRoot = Path.of(repoRoot).toAbsolutePath().normalize();
        this.syncScript = syncScript;
    }

    @GetMapping("/status")
    public DashboardStatus status() {
        return new DashboardStatus(
                repoRoot.toString(),
                Files.exists(repoRoot.resolve(syncScript)),
                envPresent("LEETCODE_SESSION"),
                envPresent("CSRF_TOKEN") || envPresent("csrftoken"),
                maskedEnv("LEETCODE_SESSION"),
                maskedEnv("CSRF_TOKEN", "csrftoken"),
                gitBranch(),
                gitDirtyCount(),
                autoSyncProblemCount(),
                syncedSubmissionCount(),
                Instant.now().toString()
        );
    }

    @PostMapping("/command")
    public CommandResponse command(@RequestBody SyncRequest request) {
        return new CommandResponse(String.join(" ", buildCommand(request)));
    }

    @PostMapping("/sync")
    public SyncJobSnapshot sync(@RequestBody SyncRequest request) {
        List<String> command = buildCommand(request);
        SyncJob job = new SyncJob(UUID.randomUUID().toString(), String.join(" ", command));
        jobs.put(job.id, job);
        executor.execute(() -> runSyncJob(job, command));
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

    private void runSyncJob(SyncJob job, List<String> command) {
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.directory(repoRoot.toFile());

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

    private List<String> buildCommand(SyncRequest request) {
        SyncRequest safe = request == null ? new SyncRequest("recent", 20, 30, "preview", true) : request;
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
        command.add(syncScript);
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
        return command;
    }

    private boolean envPresent(String key) {
        return !isBlank(System.getenv(key));
    }

    private String maskedEnv(String... keys) {
        for (String key : keys) {
            String value = System.getenv(key);
            if (!isBlank(value)) {
                int keep = Math.min(6, value.length());
                return value.substring(0, keep) + "...(" + value.length() + ")";
            }
        }
        return "";
    }

    private String gitBranch() {
        return runQuietly(List.of("git", "branch", "--show-current")).trim();
    }

    private int gitDirtyCount() {
        String status = runQuietly(List.of("git", "status", "--short"));
        if (status.isBlank()) {
            return 0;
        }
        return (int) status.lines().filter(line -> !line.isBlank()).count();
    }

    private int autoSyncProblemCount() {
        Path autoSync = repoRoot.resolve("AutoSync");
        if (!Files.isDirectory(autoSync)) {
            return 0;
        }
        try (var stream = Files.list(autoSync)) {
            return (int) stream.filter(Files::isDirectory).count();
        } catch (IOException ignored) {
            return 0;
        }
    }

    private int syncedSubmissionCount() {
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

    private String runQuietly(List<String> command) {
        try {
            Process process = new ProcessBuilder(command).directory(repoRoot.toFile()).start();
            boolean finished = process.waitFor(10, TimeUnit.SECONDS);
            if (!finished || process.exitValue() != 0) {
                return "";
            }
            return new String(process.getInputStream().readAllBytes(), Charset.defaultCharset());
        } catch (IOException ignored) {
            return "";
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
            return "";
        }
    }

    private static String blankToDefault(String value, String fallback) {
        return isBlank(value) ? fallback : value;
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public record SyncRequest(String scope, Integer limit, Integer maxSync, String mode, Boolean debug) {
    }

    public record CommandResponse(String command) {
    }

    public record DashboardStatus(
            String repoRoot,
            boolean syncScriptFound,
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
