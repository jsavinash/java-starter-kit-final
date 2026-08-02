package com.javastarterkit.patterns.modelviewpresenter.presenter;

import com.javastarterkit.patterns.modelviewpresenter.exception.TaskNotFoundException;
import com.javastarterkit.patterns.modelviewpresenter.model.Task;
import com.javastarterkit.patterns.modelviewpresenter.model.TaskStatus;
import com.javastarterkit.patterns.modelviewpresenter.model.NotificationType;
import com.javastarterkit.patterns.modelviewpresenter.repository.TaskRepository;
import com.javastarterkit.patterns.modelviewpresenter.view.TaskView;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Presenter for viewing and editing individual task details.
 * Thread-safe through AtomicReference for mutable state.
 */
public final class TaskDetailPresenter implements TaskPresenter {

    private final TaskRepository taskRepository;
    private final AtomicReference<TaskView> viewRef = new AtomicReference<>();
    private final AtomicReference<String> currentTaskId = new AtomicReference<>();

    public TaskDetailPresenter(TaskRepository taskRepository) {
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
        currentTaskId.set(null);
    }

    /**
     * Loads and displays a task by ID.
     */
    public void onViewReady(String taskId) {
        Objects.requireNonNull(taskId, "Task ID must not be null");
        TaskView view = getAttachedView();

        view.showLoading();
        try {
            Task task = taskRepository.findById(taskId)
                    .orElseThrow(() -> new TaskNotFoundException("Task not found: " + taskId));
            currentTaskId.set(taskId);
            view.displayTask(task);
        } finally {
            view.hideLoading();
        }
    }

    /**
     * Saves task updates.
     */
    public void onSaveRequested(Task updatedTask) {
        Objects.requireNonNull(updatedTask, "Task must not be null");
        TaskView view = getAttachedView();

        view.showLoading();
        try {
            String taskId = currentTaskId.get();
            if (taskId == null) {
                view.showError("No task selected");
                return;
            }

            Task existing = taskRepository.findById(taskId)
                    .orElseThrow(() -> new TaskNotFoundException("Task not found: " + taskId));

            // Preserve the original ID and user ownership
            Task saved = new Task(
                    existing.id(),
                    existing.userId(),
                    updatedTask.title(),
                    updatedTask.description(),
                    updatedTask.status(),
                    updatedTask.priority(),
                    existing.createdAt(),
                    java.time.Instant.now(),
                    updatedTask.completedAt(),
                    updatedTask.dueDate()
            );
            Task persisted = taskRepository.save(saved);
            view.displayTask(persisted);
            view.showNotification("Task saved successfully", NotificationType.SUCCESS);
        } finally {
            view.hideLoading();
        }
    }

    /**
     * Deletes the current task.
     */
    public void onDeleteRequested() {
        String taskId = currentTaskId.get();
        if (taskId == null) {
            throw new IllegalStateException("No task selected");
        }
        TaskView view = getAttachedView();

        try {
            taskRepository.deleteById(taskId);
            currentTaskId.set(null);
            view.setFormEnabled(false);
            view.showNotification("Task deleted", NotificationType.SUCCESS);
        } catch (Exception e) {
            view.showError("Failed to delete task: " + e.getMessage());
        }
    }

    /**
     * Marks the task as completed.
     */
    public void onMarkCompleted() {
        String taskId = currentTaskId.get();
        if (taskId == null) {
            throw new IllegalStateException("No task selected");
        }

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found: " + taskId));
        Task completed = task.withStatus(TaskStatus.COMPLETED);
        Task saved = taskRepository.save(completed);

        TaskView view = viewRef.get();
        if (view != null) {
            view.displayTask(saved);
            view.showNotification("Task marked as completed", NotificationType.SUCCESS);
        }
    }

    /**
     * Enables or disables editing mode.
     */
    public void onEditModeChanged(boolean enabled) {
        TaskView view = viewRef.get();
        if (view != null) {
            view.setFormEnabled(enabled);
        }
    }

    private TaskView getAttachedView() {
        TaskView view = viewRef.get();
        if (view == null) {
            throw new IllegalStateException("Presenter not attached to a view");
        }
        return view;
    }
}