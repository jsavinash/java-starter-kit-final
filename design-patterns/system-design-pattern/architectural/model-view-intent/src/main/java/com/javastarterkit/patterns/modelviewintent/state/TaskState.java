package com.javastarterkit.patterns.modelviewintent.state;

import java.util.List;

/**
 * Immutable task list state.
 *
 * <p>This is the <b>Model (State)</b> in the MVI pattern for the task list
 * feature. It holds an immutable snapshot of all tasks.
 *
 * @param tasks the list of tasks
 * @author Java Starter Kit
 * @version 1.0.0
 */
public record TaskState(List<TaskItem> tasks) {

    /**
     * Compact constructor that creates a defensive copy of the tasks list.
     */
    public TaskState {
        tasks = List.copyOf(tasks);
    }

    /**
     * Returns a new state with the given tasks.
     *
     * @param tasks the new tasks
     * @return a new TaskState
     */
    public TaskState copyWith(List<TaskItem> tasks) {
        return new TaskState(tasks);
    }

    @Override
    public String toString() {
        return "TaskState{tasks=" + tasks + "}";
    }
}