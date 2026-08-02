package com.javastarterkit.patterns.modelviewpresenter.model;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Immutable task model representing a user task.
 * Uses record for immutability with builder-style with* methods for state transitions.
 *
 * @param id          unique task identifier
 * @param userId      owning user identifier
 * @param title       task title
 * @param description optional task description
 * @param status      current task status
 * @param priority    task priority level
 * @param createdAt   creation timestamp
 * @param updatedAt   last update timestamp
 * @param completedAt completion timestamp (null if not completed)
 * @param dueDate     optional due date
 */
public record Task(
        String id,
        String userId,
        String title,
        String description,
        TaskStatus status,
        Priority priority,
        Instant createdAt,
        Instant updatedAt,
        Instant completedAt,
        Instant dueDate
) {

    /**
     * Creates a new task with generated ID and timestamps.
     */
    public static Task create(String userId, String title, String description, Priority priority, Instant dueDate) {
        Objects.requireNonNull(userId, "userId must not be null");
        Objects.requireNonNull(title, "title must not be null");
        Objects.requireNonNull(priority, "priority must not be null");
        Instant now = Instant.now();
        return new Task(
                UUID.randomUUID().toString(),
                userId,
                title,
                description,
                TaskStatus.PENDING,
                priority,
                now,
                now,
                null,
                dueDate
        );
    }

    /**
     * Returns a new Task with updated title.
     */
    public Task withTitle(String newTitle) {
        return new Task(id, userId, newTitle, description, status, priority, createdAt, Instant.now(), completedAt, dueDate);
    }

    /**
     * Returns a new Task with updated description.
     */
    public Task withDescription(String newDescription) {
        return new Task(id, userId, title, newDescription, status, priority, createdAt, Instant.now(), completedAt, dueDate);
    }

    /**
     * Returns a new Task with updated status.
     */
    public Task withStatus(TaskStatus newStatus) {
        Instant newCompletedAt = (newStatus == TaskStatus.COMPLETED) ? Instant.now() : null;
        return new Task(id, userId, title, description, newStatus, priority, createdAt, Instant.now(), newCompletedAt, dueDate);
    }

    /**
     * Returns a new Task with updated priority.
     */
    public Task withPriority(Priority newPriority) {
        return new Task(id, userId, title, description, status, newPriority, createdAt, Instant.now(), completedAt, dueDate);
    }

    /**
     * Returns a new Task with updated due date.
     */
    public Task withDueDate(Instant newDueDate) {
        return new Task(id, userId, title, description, status, priority, createdAt, Instant.now(), completedAt, newDueDate);
    }

    /**
     * Returns a new Task marked as completed.
     */
    public Task markCompleted() {
        return withStatus(TaskStatus.COMPLETED);
    }

    /**
     * Checks if the task is overdue.
     */
    public boolean isOverdue() {
        return dueDate != null && dueDate.isBefore(Instant.now()) && status != TaskStatus.COMPLETED;
    }

    /**
     * Checks if the task is completed.
     */
    public boolean isCompleted() {
        return status == TaskStatus.COMPLETED;
    }
}