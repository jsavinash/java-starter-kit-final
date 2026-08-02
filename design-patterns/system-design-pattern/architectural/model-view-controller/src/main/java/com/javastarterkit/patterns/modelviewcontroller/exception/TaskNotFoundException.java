package com.javastarterkit.patterns.modelviewcontroller.exception;

/**
 * Thrown when a requested task cannot be found in the task list model.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class TaskNotFoundException extends MvcException {

    /**
     * Constructs a new task-not-found exception with the specified message.
     *
     * @param message the detail message
     */
    public TaskNotFoundException(String message) {
        super(message);
    }
}