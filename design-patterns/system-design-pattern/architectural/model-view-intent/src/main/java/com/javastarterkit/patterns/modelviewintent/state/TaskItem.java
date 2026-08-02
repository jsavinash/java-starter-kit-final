package com.javastarterkit.patterns.modelviewintent.state;

/**
 * A single immutable task item.
 *
 * @param description the task description
 * @param completed   whether the task is completed
 * @author Java Starter Kit
 * @version 1.0.0
 */
public record TaskItem(String description, boolean completed) {

    /**
     * Returns a new task item marked as completed.
     *
     * @return a new TaskItem with completed=true
     */
    public TaskItem complete() {
        return new TaskItem(description, true);
    }
}