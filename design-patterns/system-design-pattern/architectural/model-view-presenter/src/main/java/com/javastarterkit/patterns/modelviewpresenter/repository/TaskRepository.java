package com.javastarterkit.patterns.modelviewpresenter.repository;

import com.javastarterkit.patterns.modelviewpresenter.model.Task;
import com.javastarterkit.patterns.modelviewpresenter.model.TaskStatus;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Task aggregate persistence.
 * Provides thread-safe operations for task CRUD with user-scoped queries.
 */
public interface TaskRepository {

    /**
     * Finds a task by its unique ID.
     */
    Optional<Task> findById(String taskId);

    /**
     * Finds all tasks belonging to a specific user.
     */
    List<Task> findByUserId(String userId);

    /**
     * Finds tasks by user ID and status.
     */
    List<Task> findByUserIdAndStatus(String userId, TaskStatus status);

    /**
     * Saves a task (create or update).
     */
    Task save(Task task);

    /**
     * Deletes a task by ID.
     */
    void deleteById(String taskId);

    /**
     * Counts total tasks for a user.
     */
    long countByUserId(String userId);
}