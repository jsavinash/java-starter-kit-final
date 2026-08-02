package com.javastarterkit.patterns.modelviewintent.intent;

/**
 * Intent: mark a task as complete.
 *
 * @param index the task index to complete
 * @author Java Starter Kit
 * @version 1.0.0
 */
public record CompleteTask(int index) implements TaskIntent {

    public CompleteTask {
        if (index < 0) {
            throw new IllegalArgumentException("Task index must be non-negative");
        }
    }
}