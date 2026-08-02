package com.javastarterkit.patterns.modelviewcontroller.model;

import com.javastarterkit.patterns.modelviewcontroller.exception.TaskNotFoundException;
import com.javastarterkit.patterns.modelviewcontroller.view.TaskView;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Thread-safe task list model with observer notification.
 *
 * <p>This is the <b>Model</b> in the MVC pattern. It manages the task data
 * and business rules, and notifies registered views whenever the state
 * changes. The model has <b>no knowledge</b> of the views or controller.
 *
 * <p><b>Thread-Safety Strategy:</b>
 * <ul>
 *   <li>Uses {@link CopyOnWriteArrayList} for thread-safe observer registration
 *       and notification.</li>
 *   <li>Uses {@link CopyOnWriteArrayList} for the task list to allow concurrent
 *       reads while mutations are atomic.</li>
 *   <li>Returns defensive copies via {@link List#copyOf}.</li>
 * </ul>
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public final class TaskList {

    private final CopyOnWriteArrayList<Task> tasks = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<TaskView> observers = new CopyOnWriteArrayList<>();

    /**
     * Registers a view to be notified on model changes.
     *
     * @param view the view to register
     */
    public void addObserver(TaskView view) {
        observers.add(Objects.requireNonNull(view, "View must not be null"));
    }

    /**
     * Removes a view from the observer list.
     *
     * @param view the view to remove
     */
    public void removeObserver(TaskView view) {
        observers.remove(view);
    }

    /**
     * Adds a new task to the list and notifies observers.
     *
     * @param description the task description
     * @return the created task
     * @throws IllegalArgumentException if the description is blank
     */
    public Task addTask(String description) {
        Objects.requireNonNull(description, "Task description must not be null");
        if (description.isBlank()) {
            throw new IllegalArgumentException("Task description must not be blank");
        }
        Task task = new Task(description);
        tasks.add(task);
        notifyObservers();
        return task;
    }

    /**
     * Marks a task as completed by index and notifies observers.
     *
     * @param index the task index
     * @return the completed task
     * @throws TaskNotFoundException if the index is out of bounds
     */
    public Task completeTask(int index) {
        if (index < 0 || index >= tasks.size()) {
            throw new TaskNotFoundException("Task not found at index: " + index);
        }
        Task task = tasks.get(index);
        task.complete();
        notifyObservers();
        return task;
    }

    /**
     * Marks a task as completed by ID and notifies observers.
     *
     * @param taskId the task identifier
     * @return the completed task
     * @throws TaskNotFoundException if the task ID is not found
     */
    public Task completeTaskById(String taskId) {
        Objects.requireNonNull(taskId, "Task ID must not be null");
        Task task = tasks.stream()
                .filter(t -> t.id().equals(taskId))
                .findFirst()
                .orElseThrow(() -> new TaskNotFoundException("Task not found: " + taskId));
        task.complete();
        notifyObservers();
        return task;
    }

    /**
     * Returns an immutable snapshot of all tasks.
     *
     * @return an immutable list of tasks
     */
    public List<Task> tasks() {
        return List.copyOf(tasks);
    }

    /**
     * Returns the number of tasks.
     *
     * @return the task count
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the number of completed tasks.
     *
     * @return the completed task count
     */
    public int completedCount() {
        return (int) tasks.stream().filter(Task::isCompleted).count();
    }

    /**
     * Notifies all registered observers of a model change.
     */
    private void notifyObservers() {
        for (TaskView view : observers) {
            view.onModelChanged(this);
        }
    }
}