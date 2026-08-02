package com.javastarterkit.patterns.modelviewpresenter.exception;

/**
 * Exception thrown when a task is not found.
 */
public class TaskNotFoundException extends MvpException {
    
    public TaskNotFoundException(String taskId) {
        super("Task not found with ID: " + taskId);
    }
}