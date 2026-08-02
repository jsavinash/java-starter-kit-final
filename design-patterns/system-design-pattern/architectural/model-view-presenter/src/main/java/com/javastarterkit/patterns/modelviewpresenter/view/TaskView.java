package com.javastarterkit.patterns.modelviewpresenter.view;

import com.javastarterkit.patterns.modelviewpresenter.model.DashboardMetrics;
import com.javastarterkit.patterns.modelviewpresenter.model.Notification;
import com.javastarterkit.patterns.modelviewpresenter.model.NotificationType;
import com.javastarterkit.patterns.modelviewpresenter.model.Task;
import com.javastarterkit.patterns.modelviewpresenter.model.User;
import java.util.List;

/**
 * View interface for Task MVP pattern.
 * The view is passive and only displays data - it doesn't contain business logic.
 * The presenter orchestrates all business logic and updates the view.
 */
public interface TaskView {

    /**
     * Displays a list of tasks.
     */
    void showTasks(List<Task> tasks);

    /**
     * Displays a single task in detail.
     */
    void showTask(Task task);

    /**
     * Shows a notification/message to the user.
     */
    void showNotification(String message, NotificationType type);

    /**
     * Shows loading indicator.
     */
    void showLoading();

    /**
     * Hides loading indicator.
     */
    void hideLoading();

    /**
     * Clears the task form.
     */
    void clearForm();

    /**
     * Shows error message for task not found.
     */
    void showTaskNotFoundError(String taskId);

    /**
     * Shows confirmation dialog.
     */
    boolean showConfirmation(String message);

    /**
     * Enables/disables form fields.
     */
    void setFormEnabled(boolean enabled);

    /**
     * Updates the task count display.
     */
    void updateTaskCount(int total, int pending, int completed);

    /**
     * Displays the current user in the dashboard header.
     */
    void displayUser(User user);

    /**
     * Displays dashboard metrics.
     */
    void displayMetrics(DashboardMetrics metrics);

    /**
     * Displays a list of notifications.
     */
    void displayNotifications(List<Notification> notifications);

    /**
     * Displays a task (detail view).
     */
    void displayTask(Task task);

    /**
     * Shows an error message.
     */
    void showError(String message);

    /**
     * Enables or disables editing mode.
     */
    void enableEditing(boolean enabled);
}