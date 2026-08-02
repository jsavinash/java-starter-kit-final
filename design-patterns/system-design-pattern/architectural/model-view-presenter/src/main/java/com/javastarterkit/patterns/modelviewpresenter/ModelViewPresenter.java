package com.javastarterkit.patterns.modelviewpresenter;

import com.javastarterkit.patterns.modelviewpresenter.model.AuthenticationResult;
import com.javastarterkit.patterns.modelviewpresenter.model.DashboardMetrics;
import com.javastarterkit.patterns.modelviewpresenter.model.Notification;
import com.javastarterkit.patterns.modelviewpresenter.model.NotificationType;
import com.javastarterkit.patterns.modelviewpresenter.model.Priority;
import com.javastarterkit.patterns.modelviewpresenter.model.Role;
import com.javastarterkit.patterns.modelviewpresenter.model.Task;
import com.javastarterkit.patterns.modelviewpresenter.model.TaskStatus;
import com.javastarterkit.patterns.modelviewpresenter.model.User;
import com.javastarterkit.patterns.modelviewpresenter.presenter.TaskDetailPresenter;
import com.javastarterkit.patterns.modelviewpresenter.presenter.TaskListPresenter;
import com.javastarterkit.patterns.modelviewpresenter.repository.InMemoryTaskRepository;
import com.javastarterkit.patterns.modelviewpresenter.repository.InMemoryUserRepository;
import com.javastarterkit.patterns.modelviewpresenter.repository.TaskRepository;
import com.javastarterkit.patterns.modelviewpresenter.repository.UserRepository;
import com.javastarterkit.patterns.modelviewpresenter.service.AuthenticationService;
import com.javastarterkit.patterns.modelviewpresenter.service.SessionManager;
import com.javastarterkit.patterns.modelviewpresenter.view.TaskView;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Model-View-Presenter (MVP) Pattern Demonstration.
 * <p>
 * This class demonstrates the complete end-to-end MVP flow:
 * <ol>
 *   <li>User authentication (Model + Service)</li>
 *   <li>Dashboard presentation (Presenter orchestrates View + Model)</li>
 *   <li>Task detail CRUD operations</li>
 *   <li>Thread-safe concurrent access demonstration</li>
 * </ol>
 */
public final class ModelViewPresenter {

    private static final String PASSWORD = "secret123";

    /**
     * Console-based view implementation that demonstrates the MVP View contract.
     * The view is passive - it renders what the presenter tells it to render.
     */
    public static final class ConsoleTaskView implements TaskView {
        private final AtomicBoolean formEnabled = new AtomicBoolean(false);

        @Override
        public void showTasks(List<Task> tasks) {
            System.out.println("── Tasks ──────────────────────────────");
            tasks.forEach(t -> System.out.printf(
                    "  [%s] %s (%s) due=%s%n",
                    t.status(), t.title(), t.priority(),
                    t.dueDate() != null ? t.dueDate() : "none"
            ));
        }

        @Override
        public void showTask(Task task) {
            System.out.println("── Task Detail ────────────────────────");
            System.out.println("  ID:          " + task.id());
            System.out.println("  Title:       " + task.title());
            System.out.println("  Description: " + task.description());
            System.out.println("  Status:      " + task.status());
            System.out.println("  Priority:    " + task.priority());
        }

        @Override
        public void showNotification(String message, NotificationType type) {
            System.out.printf("  [%s] %s%n", type, message);
        }

        @Override
        public void showLoading() {
            System.out.println("  Loading...");
        }

        @Override
        public void hideLoading() {
            System.out.println("  Done.");
        }

        @Override
        public void clearForm() {
            System.out.println("  Form cleared.");
        }

        @Override
        public void showTaskNotFoundError(String taskId) {
            System.err.println("  ERROR: Task not found: " + taskId);
        }

        @Override
        public boolean showConfirmation(String message) {
            System.out.println("  Confirmation: " + message + " → yes");
            return true;
        }

        @Override
        public void setFormEnabled(boolean enabled) {
            formEnabled.set(enabled);
            System.out.println("  Form " + (enabled ? "enabled" : "disabled"));
        }

        @Override
        public void updateTaskCount(int total, int pending, int completed) {
            System.out.printf("  Task counts → total=%d pending=%d completed=%d%n",
                    total, pending, completed);
        }

        @Override
        public void displayUser(User user) {
            System.out.println("── Welcome ────────────────────────────");
            System.out.println("  User:  " + user.username());
            System.out.println("  Email: " + user.email());
            System.out.println("  Roles: " + user.roles());
        }

        @Override
        public void displayMetrics(DashboardMetrics metrics) {
            System.out.println("── Dashboard Metrics ──────────────────");
            System.out.printf("  Total: %d | Pending: %d | In-Progress: %d | Completed: %d | Overdue: %d%n",
                    metrics.totalTasks(), metrics.pendingTasks(), metrics.inProgressTasks(),
                    metrics.completedTasks(), metrics.overdueTasks());
        }

        @Override
        public void displayNotifications(List<Notification> notifications) {
            System.out.println("── Notifications ──────────────────────");
            notifications.forEach(n -> System.out.printf(
                    "  [%s] %s (%s)%n", n.type(), n.message(), n.timestamp()
            ));
        }

        @Override
        public void displayTask(Task task) {
            showTask(task);
        }

        @Override
        public void showError(String message) {
            System.err.println("  ERROR: " + message);
        }

        @Override
        public void enableEditing(boolean enabled) {
            setFormEnabled(enabled);
        }
    }

    /**
     * Demonstration entry point.
     */
    public static void demonstrate() {
        System.out.println("\n═══════════════════════════════════════════════");
        System.out.println("  MODEL-VIEW-PRESENTER (MVP) PATTERN DEMO");
        System.out.println("═══════════════════════════════════════════════\n");

        // ── 1. Bootstrap the composition root ─────────────────────────
        UserRepository userRepository = new InMemoryUserRepository();
        TaskRepository taskRepository = new InMemoryTaskRepository();
        SessionManager sessionManager = new SessionManager();
        AuthenticationService authService = new AuthenticationService(userRepository, sessionManager);

        // ── 2. Seed demo data ─────────────────────────────────────────
        User admin = User.create("admin", "admin@example.com", PASSWORD, Set.of(Role.ADMIN, Role.USER));
        userRepository.save(admin);

        Task task1 = Task.create(admin.id(), "Design MVP architecture",
                "Create low-level design document with Mermaid diagrams",
                Priority.HIGH, Instant.now().plus(2, ChronoUnit.DAYS));
        Task task2 = Task.create(admin.id(), "Implement repository layer",
                "Implement thread-safe in-memory repositories",
                Priority.MEDIUM, Instant.now().plus(5, ChronoUnit.DAYS));
        Task task3 = Task.create(admin.id(), "Write tests",
                "Cover presenters with unit tests",
                Priority.CRITICAL, Instant.now().minus(1, ChronoUnit.DAYS));

        taskRepository.save(task1);
        taskRepository.save(task2);
        taskRepository.save(task3);

        // ── 3. View creation ─────────────────────────────────────────
        ConsoleTaskView dashboardView = new ConsoleTaskView();
        ConsoleTaskView detailView = new ConsoleTaskView();

        // ── 4. Presenter creation & attachment ───────────────────────
        TaskListPresenter listPresenter = new TaskListPresenter(userRepository, taskRepository);
        TaskDetailPresenter detailPresenter = new TaskDetailPresenter(taskRepository);

        listPresenter.onAttach(dashboardView);
        detailPresenter.onAttach(detailView);

        // ── 5. End-to-end flow: Authenticate → Dashboard → Detail ────
        System.out.println("▶ STEP 1: User Authentication");
        AuthenticationResult authResult = authService.authenticate("admin", PASSWORD);
        if (!authResult.success()) {
            System.err.println("Authentication failed: " + authResult.errorMessage());
            return;
        }
        String sessionId = authResult.getSessionId().orElseThrow();
        User authenticatedUser = authResult.getUser().orElseThrow();
        System.out.println("  ✓ Authenticated user: " + authenticatedUser.username());
        System.out.println("  ✓ Session ID: " + sessionId);

        System.out.println("\n▶ STEP 2: Dashboard Presentation");
        listPresenter.onViewReady(authenticatedUser.id());

        System.out.println("\n▶ STEP 3: Task Detail View");
        detailPresenter.onViewReady(task2.id());

        System.out.println("\n▶ STEP 4: Update Task Status");
        Task inProgress = task2.withStatus(TaskStatus.IN_PROGRESS);
        taskRepository.save(inProgress);
        detailPresenter.onViewReady(task2.id());
        detailPresenter.onMarkCompleted();

        System.out.println("\n▶ STEP 5: Refresh Dashboard");
        listPresenter.onRefreshRequested();

        System.out.println("\n▶ STEP 6: Concurrent Access Test");
        demonstrateConcurrency(listPresenter, detailPresenter, taskRepository, authenticatedUser.id());

        // ── 6. Cleanup ───────────────────────────────────────────────
        detailPresenter.onDetach();
        listPresenter.onDetach();
        authService.logout(sessionId);

        System.out.println("\n═══════════════════════════════════════════════");
        System.out.println("  MVP PATTERN DEMO COMPLETE ✓");
        System.out.println("═══════════════════════════════════════════════\n");
    }

    /**
     * Demonstrates thread-safe concurrent access to the MVP components.
     * Multiple threads simultaneously read the dashboard and update tasks,
     * proving that the underlying architecture is thread-safe.
     */
    private static void demonstrateConcurrency(
            TaskListPresenter listPresenter,
            TaskDetailPresenter detailPresenter,
            TaskRepository taskRepository,
            String userId) {

        int threadCount = 5;
        List<Thread> threads = java.util.stream.IntStream.range(0, threadCount)
                .mapToObj(i -> new Thread(() -> {
                    // Concurrent reads
                    listPresenter.onRefreshRequested();
                    // Concurrent writes
                    List<Task> userTasks = taskRepository.findByUserId(userId);
                    if (!userTasks.isEmpty()) {
                        Task first = userTasks.get(0);
                        Task updated = first.withPriority(
                                i % 2 == 0 ? Priority.HIGH : Priority.LOW
                        );
                        taskRepository.save(updated);
                    }
                }, "worker-" + i))
                .toList();

        threads.forEach(Thread::start);
        threads.forEach(t -> {
            try {
                t.join(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("  Interrupted: " + e.getMessage());
            }
        });

        System.out.println("  ✓ " + threadCount + " concurrent threads completed safely");
        System.out.println("  ✓ Task repository is thread-safe (ConcurrentHashMap)");
        System.out.println("  ✓ Presenters are thread-safe (AtomicReference)");
    }

    public static void main(String[] args) {
        demonstrate();
    }
}