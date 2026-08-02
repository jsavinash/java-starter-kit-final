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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Comprehensive test suite for the Model-View-Presenter pattern.
 * Uses lightweight stub implementations rather than Mockito mocks
 * for maximum compatibility with Java 25.
 */
@DisplayName("Model-View-Presenter Pattern Tests")
class ModelViewPresenterTest {

    private static final String PASSWORD = "secret123";

    private UserRepository userRepository;
    private TaskRepository taskRepository;
    private SessionManager sessionManager;
    private AuthenticationService authService;
    private User admin;
    private Task sampleTask;

    /**
     * Recording stub for TaskView that captures invocations.
     */
    static class RecordingTaskView implements TaskView {
        private final List<Task> displayedTasks = new ArrayList<>();
        private final List<Task> singleTasks = new ArrayList<>();
        private final List<Notification> notifications = new ArrayList<>();
        private final List<NotificationType> notificationTypes = new ArrayList<>();
        private final List<String> notificationsShown = new ArrayList<>();
        private User displayedUser;
        private DashboardMetrics displayedMetrics;
        private String lastError;
        private boolean loadingShown;
        private boolean loadingHidden;
        private boolean formEnabled;
        private boolean editingEnabled;
        private Task displayedTask;
        private int totalCount;
        private int pendingCount;
        private int completedCount;
        private String taskNotFoundId;
        private String confirmationMessage;
        private boolean confirmationResult = true;

        @Override
        public void showTasks(List<Task> tasks) {
            displayedTasks.addAll(tasks);
        }

        @Override
        public void showTask(Task task) {
            singleTasks.add(task);
            displayedTask = task;
        }

        @Override
        public void showNotification(String message, NotificationType type) {
            notificationsShown.add(message);
            notificationTypes.add(type);
        }

        @Override
        public void showLoading() {
            loadingShown = true;
        }

        @Override
        public void hideLoading() {
            loadingHidden = true;
        }

        @Override
        public void clearForm() {
            // no-op
        }

        @Override
        public void showTaskNotFoundError(String taskId) {
            this.taskNotFoundId = taskId;
        }

        @Override
        public boolean showConfirmation(String message) {
            this.confirmationMessage = message;
            return confirmationResult;
        }

        @Override
        public void setFormEnabled(boolean enabled) {
            this.formEnabled = enabled;
        }

        @Override
        public void updateTaskCount(int total, int pending, int completed) {
            this.totalCount = total;
            this.pendingCount = pending;
            this.completedCount = completed;
        }

        @Override
        public void displayUser(User user) {
            this.displayedUser = user;
        }

        @Override
        public void displayMetrics(DashboardMetrics metrics) {
            this.displayedMetrics = metrics;
        }

        @Override
        public void displayNotifications(List<Notification> notices) {
            this.notifications.addAll(notices);
        }

        @Override
        public void displayTask(Task task) {
            this.displayedTask = task;
            this.singleTasks.add(task);
        }

        @Override
        public void showError(String message) {
            this.lastError = message;
        }

        @Override
        public void enableEditing(boolean enabled) {
            this.editingEnabled = enabled;
        }

        // Getters for assertions
        public User getDisplayedUser() { return displayedUser; }
        public DashboardMetrics getDisplayedMetrics() { return displayedMetrics; }
        public List<Task> getDisplayedTasks() { return List.copyOf(displayedTasks); }
        public List<Task> getSingleTasks() { return List.copyOf(singleTasks); }
        public Task getDisplayedTask() { return displayedTask; }
        public List<Notification> getNotifications() { return List.copyOf(notifications); }
        public List<String> getNotificationsShown() { return List.copyOf(notificationsShown); }
        public boolean isLoadingShown() { return loadingShown; }
        public boolean isLoadingHidden() { return loadingHidden; }
        public boolean isFormEnabled() { return formEnabled; }
        public boolean isEditingEnabled() { return editingEnabled; }
        public String getLastError() { return lastError; }
        public int getTotalCount() { return totalCount; }
        public int getPendingCount() { return pendingCount; }
        public int getCompletedCount() { return completedCount; }
        public String getTaskNotFoundId() { return taskNotFoundId; }
        public String getConfirmationMessage() { return confirmationMessage; }
    }

    @BeforeEach
    void setUp() {
        userRepository = new InMemoryUserRepository();
        taskRepository = new InMemoryTaskRepository();
        sessionManager = new SessionManager();
        authService = new AuthenticationService(userRepository, sessionManager);

        admin = User.create("admin", "admin@example.com", PASSWORD, Set.of(Role.ADMIN, Role.USER));
        userRepository.save(admin);

        sampleTask = Task.create(
                admin.id(),
                "Design MVP architecture",
                "Create LLD with Mermaid diagrams",
                Priority.HIGH,
                Instant.now().plus(2, ChronoUnit.DAYS)
        );
        taskRepository.save(sampleTask);
    }

    @Nested
    @DisplayName("Authentication Service Tests")
    class AuthenticationServiceTest {

        @Test
        @DisplayName("Should authenticate valid credentials and create session")
        void should_authenticateValidCredentials() {
            AuthenticationResult result = authService.authenticate("admin", PASSWORD);

            assertThat(result.success()).isTrue();
            assertThat(result.getSessionId()).isPresent();
            assertThat(result.getUser()).isPresent();
            assertThat(result.getUser().get().username()).isEqualTo("admin");
            assertThat(sessionManager.isValid(result.getSessionId().get())).isTrue();
        }

        @Test
        @DisplayName("Should reject invalid credentials")
        void should_rejectInvalidCredentials() {
            AuthenticationResult result = authService.authenticate("admin", "wrongpassword");

            assertThat(result.success()).isFalse();
            assertThat(result.errorMessage()).contains("Invalid");
        }

        @Test
        @DisplayName("Should reject invalid username format")
        void should_rejectInvalidUsernameFormat() {
            AuthenticationResult result = authService.authenticate("ab", "password123");

            assertThat(result.success()).isFalse();
            assertThat(result.errorMessage()).contains("Invalid username format");
        }

        @Test
        @DisplayName("Should log out by invalidating session")
        void should_logoutInvalidatesSession() {
            AuthenticationResult result = authService.authenticate("admin", PASSWORD);
            String sessionId = result.getSessionId().orElseThrow();

            authService.logout(sessionId);

            assertThat(sessionManager.isValid(sessionId)).isFalse();
        }

        @Test
        @DisplayName("Should reject null username")
        void should_rejectNullUsername() {
            assertThatThrownBy(() -> authService.authenticate(null, PASSWORD))
                    .isInstanceOf(NullPointerException.class);
        }
    }

    @Nested
    @DisplayName("Task List Presenter Tests")
    class TaskListPresenterTest {

        @Test
        @DisplayName("Should display user, metrics, tasks, and notifications on view ready")
        void should_displayDashboardDataOnViewReady() {
            RecordingTaskView view = new RecordingTaskView();
            TaskListPresenter presenter = new TaskListPresenter(userRepository, taskRepository);
            presenter.onAttach(view);

            presenter.onViewReady(admin.id());

            assertThat(view.isLoadingShown()).isTrue();
            assertThat(view.isLoadingHidden()).isTrue();
            assertThat(view.getDisplayedUser()).isNotNull();
            assertThat(view.getDisplayedUser().username()).isEqualTo("admin");
            assertThat(view.getDisplayedMetrics()).isNotNull();
            assertThat(view.getDisplayedTasks()).hasSize(1);
            assertThat(view.getNotifications()).isEmpty();
        }

        @Test
        @DisplayName("Should compute correct dashboard metrics")
        void should_computeCorrectMetrics() {
            RecordingTaskView view = new RecordingTaskView();
            TaskListPresenter presenter = new TaskListPresenter(userRepository, taskRepository);
            presenter.onAttach(view);

            // Add more tasks with different states
            Task pending = Task.create(admin.id(), "Pending task", "desc", Priority.LOW, null);
            Task inProgress = Task.create(admin.id(), "In progress", "desc", Priority.MEDIUM, null)
                    .withStatus(TaskStatus.IN_PROGRESS);
            Task completed = Task.create(admin.id(), "Completed", "desc", Priority.HIGH, null)
                    .markCompleted();
            Task overdue = Task.create(admin.id(), "Overdue", "desc", Priority.CRITICAL,
                    Instant.now().minus(1, ChronoUnit.DAYS));
            taskRepository.save(pending);
            taskRepository.save(inProgress);
            taskRepository.save(completed);
            taskRepository.save(overdue);

            presenter.onViewReady(admin.id());

            DashboardMetrics metrics = view.getDisplayedMetrics();
            assertThat(metrics).isNotNull();
            assertThat(metrics.totalTasks()).isEqualTo(5);
            assertThat(metrics.pendingTasks()).isEqualTo(3); // sample + pending + overdue
            assertThat(metrics.inProgressTasks()).isEqualTo(1);
            assertThat(metrics.completedTasks()).isEqualTo(1);
            assertThat(metrics.overdueTasks()).isEqualTo(1);
        }

        @Test
        @DisplayName("Should throw IllegalStateException when not attached to a view")
        void should_throwWhenNotAttached() {
            TaskListPresenter presenter = new TaskListPresenter(userRepository, taskRepository);

            assertThatThrownBy(() -> presenter.onViewReady(admin.id()))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("not attached");
        }

        @Test
        @DisplayName("Should reject null user ID")
        void should_rejectNullUserId() {
            RecordingTaskView view = new RecordingTaskView();
            TaskListPresenter presenter = new TaskListPresenter(userRepository, taskRepository);
            presenter.onAttach(view);

            assertThatThrownBy(() -> presenter.onViewReady(null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Should detach and destroy cleanly")
        void should_detachAndDestroy() {
            RecordingTaskView view = new RecordingTaskView();
            TaskListPresenter presenter = new TaskListPresenter(userRepository, taskRepository);
            presenter.onAttach(view);
            presenter.onViewReady(admin.id());

            presenter.onDetach();
            assertThatThrownBy(() -> presenter.onViewReady(admin.id()))
                    .isInstanceOf(IllegalStateException.class);

            presenter.onAttach(view);
            presenter.onViewReady(admin.id());
            presenter.onDestroy();
        }
    }

    @Nested
    @DisplayName("Task Detail Presenter Tests")
    class TaskDetailPresenterTest {

        @Test
        @DisplayName("Should display task on view ready")
        void should_displayTaskOnViewReady() {
            RecordingTaskView view = new RecordingTaskView();
            TaskDetailPresenter presenter = new TaskDetailPresenter(taskRepository);
            presenter.onAttach(view);

            presenter.onViewReady(sampleTask.id());

            assertThat(view.isLoadingShown()).isTrue();
            assertThat(view.isLoadingHidden()).isTrue();
            assertThat(view.getDisplayedTask()).isEqualTo(sampleTask);
        }

        @Test
        @DisplayName("Should save updated task preserving original ID and user")
        void should_saveUpdatedTask() {
            RecordingTaskView view = new RecordingTaskView();
            TaskDetailPresenter presenter = new TaskDetailPresenter(taskRepository);
            presenter.onAttach(view);
            presenter.onViewReady(sampleTask.id());

            Task updated = new Task(
                    sampleTask.id(),
                    sampleTask.userId(),
                    "Updated title",
                    "Updated description",
                    TaskStatus.IN_PROGRESS,
                    Priority.CRITICAL,
                    sampleTask.createdAt(),
                    Instant.now(),
                    null,
                    sampleTask.dueDate()
            );

            presenter.onSaveRequested(updated);

            Task saved = view.getDisplayedTask();
            assertThat(saved).isNotNull();
            assertThat(saved.title()).isEqualTo("Updated title");
            assertThat(saved.description()).isEqualTo("Updated description");
            assertThat(saved.status()).isEqualTo(TaskStatus.IN_PROGRESS);
            assertThat(saved.priority()).isEqualTo(Priority.CRITICAL);
            assertThat(saved.id()).isEqualTo(sampleTask.id());
            assertThat(saved.userId()).isEqualTo(sampleTask.userId());
            assertThat(view.getNotificationsShown()).hasSize(1);
        }

        @Test
        @DisplayName("Should delete current task")
        void should_deleteTask() {
            RecordingTaskView view = new RecordingTaskView();
            TaskDetailPresenter presenter = new TaskDetailPresenter(taskRepository);
            presenter.onAttach(view);
            presenter.onViewReady(sampleTask.id());

            presenter.onDeleteRequested();

            assertThat(taskRepository.findById(sampleTask.id())).isEmpty();
            assertThat(view.getNotificationsShown()).hasSize(1);
        }

        @Test
        @DisplayName("Should mark task as completed")
        void should_markTaskCompleted() {
            RecordingTaskView view = new RecordingTaskView();
            TaskDetailPresenter presenter = new TaskDetailPresenter(taskRepository);
            presenter.onAttach(view);
            presenter.onViewReady(sampleTask.id());

            presenter.onMarkCompleted();

            Task completed = taskRepository.findById(sampleTask.id()).orElseThrow();
            assertThat(completed.status()).isEqualTo(TaskStatus.COMPLETED);
            assertThat(completed.completedAt()).isNotNull();
        }

        @Test
        @DisplayName("Should throw TaskNotFoundException for missing task")
        void should_throwForMissingTask() {
            RecordingTaskView view = new RecordingTaskView();
            TaskDetailPresenter presenter = new TaskDetailPresenter(taskRepository);
            presenter.onAttach(view);

            assertThatThrownBy(() -> presenter.onViewReady("missing-task-id"))
                    .isInstanceOf(com.javastarterkit.patterns.modelviewpresenter.exception.TaskNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("Repository Concurrency Tests")
    class RepositoryConcurrencyTest {

        @Test
        @Timeout(value = 5, unit = TimeUnit.SECONDS)
        @DisplayName("Should handle 100 concurrent task saves safely")
        void should_handleConcurrentTaskSaves() throws InterruptedException {
            int threadCount = 100;
            ExecutorService executor = Executors.newFixedThreadPool(threadCount);
            CountDownLatch startLatch = new CountDownLatch(1);
            CountDownLatch doneLatch = new CountDownLatch(threadCount);

            for (int i = 0; i < threadCount; i++) {
                final int index = i;
                executor.submit(() -> {
                    try {
                        startLatch.await();
                        Task task = Task.create(
                                admin.id(),
                                "Concurrent task " + index,
                                "desc",
                                Priority.MEDIUM,
                                null
                        );
                        taskRepository.save(task);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        doneLatch.countDown();
                    }
                });
            }

            startLatch.countDown();
            doneLatch.await(5, TimeUnit.SECONDS);
            executor.shutdown();

            assertThat(taskRepository.countByUserId(admin.id()))
                    .isEqualTo(101); // 100 new + 1 sample
        }

        @Test
        @Timeout(value = 5, unit = TimeUnit.SECONDS)
        @DisplayName("Should handle concurrent reads and writes of same task safely")
        void should_handleConcurrentReadWrite() throws InterruptedException {
            int threadCount = 50;
            ExecutorService executor = Executors.newFixedThreadPool(threadCount);
            CountDownLatch startLatch = new CountDownLatch(1);
            CountDownLatch doneLatch = new CountDownLatch(threadCount);

            for (int i = 0; i < threadCount; i++) {
                final int index = i;
                executor.submit(() -> {
                    try {
                        startLatch.await();
                        Task task = taskRepository.findById(sampleTask.id()).orElseThrow();
                        Priority priority = index % 2 == 0 ? Priority.HIGH : Priority.LOW;
                        taskRepository.save(task.withPriority(priority));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        doneLatch.countDown();
                    }
                });
            }

            startLatch.countDown();
            doneLatch.await(5, TimeUnit.SECONDS);
            executor.shutdown();

            assertThat(taskRepository.findById(sampleTask.id())).isPresent();
        }
    }

    @Nested
    @DisplayName("End-to-End Flow Tests")
    class EndToEndTest {

        @Test
        @DisplayName("Should demonstrate complete MVP flow without throwing")
        void should_completeEndToEndFlow() {
            assertThatCode(() -> ModelViewPresenter.demonstrate())
                    .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("Should perform full authentication to dashboard to task detail flow")
        void should_performAuthToDashboardToDetailFlow() {
            // Authentication
            AuthenticationResult authResult = authService.authenticate("admin", PASSWORD);
            assertThat(authResult.success()).isTrue();
            User loggedInUser = authResult.getUser().orElseThrow();

            // Dashboard
            RecordingTaskView dashboardView = new RecordingTaskView();
            TaskListPresenter listPresenter = new TaskListPresenter(userRepository, taskRepository);
            listPresenter.onAttach(dashboardView);
            listPresenter.onViewReady(loggedInUser.id());
            assertThat(dashboardView.getDisplayedUser()).isNotNull();

            // Task Detail
            RecordingTaskView detailView = new RecordingTaskView();
            TaskDetailPresenter detailPresenter = new TaskDetailPresenter(taskRepository);
            detailPresenter.onAttach(detailView);
            detailPresenter.onViewReady(sampleTask.id());
            assertThat(detailView.getDisplayedTask()).isEqualTo(sampleTask);

            // Complete the task
            detailPresenter.onMarkCompleted();
            Task completed = taskRepository.findById(sampleTask.id()).orElseThrow();
            assertThat(completed.status()).isEqualTo(TaskStatus.COMPLETED);

            // Logout
            authService.logout(authResult.getSessionId().orElseThrow());
            assertThat(sessionManager.isValid(authResult.getSessionId().orElseThrow())).isFalse();
        }
    }
}