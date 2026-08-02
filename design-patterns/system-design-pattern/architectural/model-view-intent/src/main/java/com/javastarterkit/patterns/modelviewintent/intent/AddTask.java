package com.javastarterkit.patterns.modelviewintent.intent;

import java.util.Objects;

/**
 * Intent: add a new task.
 *
 * @param description the task description
 * @author Java Starter Kit
 * @version 1.0.0
 */
public record AddTask(String description) implements TaskIntent {

    public AddTask {
        Objects.requireNonNull(description, "Task description must not be null");
        if (description.isBlank()) {
            throw new IllegalArgumentException("Task description must not be blank");
        }
    }
}