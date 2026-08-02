package com.javastarterkit.patterns.modelviewcontroller.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Immutable task entity in the task management model.
 *
 * <p>Represents a single task with a unique ID, description, and completion
 * state. The completion state is mutable but guarded by synchronization for
 * thread-safety.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public final class Task {

    private final String id;
    private final String description;
    private boolean completed;

    /**
     * Constructs a new task with a generated UUID.
     *
     * @param description the task description
     */
    public Task(String description) {
        this(UUID.randomUUID().toString(), description);
    }

    /**
     * Constructs a new task with an explicit ID.
     *
     * @param id          the task identifier
     * @param description the task description
     */
    public Task(String id, String description) {
        this.id = Objects.requireNonNull(id, "Task ID must not be null");
        this.description = Objects.requireNonNull(description, "Task description must not be null");
        if (description.isBlank()) {
            throw new IllegalArgumentException("Task description must not be blank");
        }
        this.completed = false;
    }

    /**
     * Returns the task identifier.
     *
     * @return the task ID
     */
    public String id() {
        return id;
    }

    /**
     * Returns the task description.
     *
     * @return the task description
     */
    public String description() {
        return description;
    }

    /**
     * Returns whether the task is completed.
     *
     * @return true if completed, false otherwise
     */
    public synchronized boolean isCompleted() {
        return completed;
    }

    /**
     * Marks the task as completed.
     */
    public synchronized void complete() {
        this.completed = true;
    }

    @Override
    public String toString() {
        return (completed ? "[x] " : "[ ] ") + description;
    }
}