package com.javastarterkit.patterns.modelviewpresenter.presenter;

import com.javastarterkit.patterns.modelviewpresenter.exception.TaskNotFoundException;
import com.javastarterkit.patterns.modelviewpresenter.model.DashboardMetrics;
import com.javastarterkit.patterns.modelviewpresenter.model.Notification;
import com.javastarterkit.patterns.modelviewpresenter.model.Task;
import com.javastarterkit.patterns.modelviewpresenter.model.TaskStatus;
import com.javastarterkit.patterns.modelviewpresenter.model.User;
import com.javastarterkit.patterns.modelviewpresenter.repository.TaskRepository;
import com.javastarterkit.patterns.modelviewpresenter.repository.UserRepository;
import com.javastarterkit.patterns.modelviewpresenter.view.TaskView;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Presenter for the task list/dashboard view.
 * Mediates between the view and the model/repositories.
 * Thread-safe through AtomicReference for mutable state.
 */
public final class TaskListPresenter implements TaskPresenter {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final AtomicReference<TaskView> viewRef = new AtomicReference<>();
    private final AtomicReference<User> currentUser = new AtomicReference<>();

    public TaskListPresenter(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = Objects.requireNonNull(userRepository, "UserRepository must not be null");
        this.taskRepository = Objects.requireNonNull(taskRepository, "TaskRepository must not be null");
    }

    @Override
    public void onAttach(TaskView view) {
        viewRef.set(Objects.requireNonNull(view, "View must not be null"));
    }

    @Override
    public void onDetach() {
        viewRef.set(null);
    }

    @Override
    public void onDestroy() {
        viewRef.set(null);
        currentUser.set(null);
    }

    /**
     * Called when the view is ready to display data.
     */
    public void onViewReady(String userId) {
        Objects.requireNonNull(userId, "User ID must not be null");
        TaskView view = getAttachedView();

        view.showLoading();
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new TaskNotFoundException("User not found: " + userId));
            List<Task> tasks = taskRepository.findByUserId(userId);
            DashboardMetrics metrics = computeMetrics(tasks);
            List<Notification> notifications = generateNotifications(tasks, user);

            currentUser.set(user);
            view.displayUser(user);
            view.displayMetrics(metrics);
            view.showTasks(tasks);
            view.displayNotifications(notifications);
        } finally {
            view.hideLoading();
        }
    }

    /**
     * Refreshes the dashboard data.
     */
    public void onRefreshRequested() {
        User user = currentUser.get();
        if (user != null) {
            onViewReady(user.id());
        }
    }

    /**
     * Returns the current user ID, or null if not loaded.
     */
    public String getCurrentUserId() {
        User user = currentUser.get();
        return user != null ? user.id() : null;
    }

    private TaskView getAttachedView() {
        TaskView view = viewRef.get();
        if (view == null) {
            throw new IllegalStateException("Presenter not attached to a view");
        }
        return view;
    }

    private DashboardMetrics computeMetrics(List<Task> tasks) {
        long total = tasks.size();
        long pending = tasks.stream()
                .filter(t -> t.status() == TaskStatus.PENDING)
                .count();
        long inProgress = tasks.stream()
                .filter(t -> t.status() == TaskStatus.IN_PROGRESS)
                .count();
        long completed = tasks.stream()
                .filter(t -> t.status() == TaskStatus.COMPLETED)
                .count();
        long overdue = tasks.stream()
                .filter(Task::isOverdue)
                .count();
        return new DashboardMetrics(total, pending, inProgress, completed, overdue);
    }

    private List<Notification> generateNotifications(List<Task> tasks, User user) {
        List<Notification> notifications = new ArrayList<>();

        long overdueCount = tasks.stream()
                .filter(Task::isOverdue)
                .count();
        if (overdueCount > 0) {
            notifications.add(Notification.warning(
                    "overdue-" + user.id(),
                    "You have " + overdueCount + " overdue task(s)"
            ));
        }

        long completedToday = tasks.stream()
                .filter(t -> t.status() == TaskStatus.COMPLETED)
                .filter(t -> t.completedAt() != null)
                .filter(t -> isToday(t.completedAt()))
                .count();
        if (completedToday > 0) {
            notifications.add(Notification.success(
                    "completed-" + user.id(),
                    "Great job! You completed " + completedToday + " task(s) today"
            ));
        }

        return List.copyOf(notifications);
    }

    private boolean isToday(Instant instant) {
        LocalDate today = LocalDate.now();
        LocalDate taskDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        return taskDate.equals(today);
    }
}