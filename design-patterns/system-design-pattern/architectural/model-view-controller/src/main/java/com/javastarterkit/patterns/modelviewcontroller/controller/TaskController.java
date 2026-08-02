package com.javastarterkit.patterns.modelviewcontroller.controller;

import com.javastarterkit.patterns.modelviewcontroller.model.Task;
import com.javastarterkit.patterns.modelviewcontroller.model.TaskList;
import java.util.List;
import java.util.Objects;

/**
 * Controller in the MVC pattern.
 *
 * <p>Receives user input, validates it, and updates the model. The controller
 * knows about the model but the model does not know about the controller.
 * The controller is stateless and thread-safe.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public final class TaskController {

    private final TaskList model;

    /**
     * Constructs a controller bound to the given model.
     *
     * @param model the task list model
     */
    public TaskController(TaskList model) {
        this.model = Objects.requireNonNull(model, "Model must not be null");
    }

    /**
     * User command: add a new task.
     *
     * @param description the task description
     * @return the created task
     */
    public Task addTask(String description) {
        return model.addTask(description);
    }

    /**
     * User command: mark a task as complete by index.
     *
     * @param index the task index
     * @return the completed task
     */
    public Task completeTask(int index) {
        return model.completeTask(index);
    }

    /**
     * User command: mark a task as complete by ID.
     *
     * @param taskId the task identifier
     * @return the completed task
     */
    public Task completeTaskById(String taskId) {
        return model.completeTaskById(taskId);
    }

    /**
     * User command: get tasks for display.
     *
     * @return an immutable list of tasks
     */
    public List<Task> listTasks() {
        return model.tasks();
    }

    /**
     * Returns the number of tasks in the model.
     *
     * @return the task count
     */
    public int taskCount() {
        return model.size();
    }

    /**
     * Returns the number of completed tasks.
     *
     * @return the completed task count
     */
    public int completedCount() {
        return model.completedCount();
    }
}