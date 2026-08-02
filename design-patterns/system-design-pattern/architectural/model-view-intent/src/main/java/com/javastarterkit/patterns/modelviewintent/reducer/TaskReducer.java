package com.javastarterkit.patterns.modelviewintent.reducer;

import com.javastarterkit.patterns.modelviewintent.core.Reducer;
import com.javastarterkit.patterns.modelviewintent.exception.InvalidIntentException;
import com.javastarterkit.patterns.modelviewintent.intent.AddTask;
import com.javastarterkit.patterns.modelviewintent.intent.CompleteTask;
import com.javastarterkit.patterns.modelviewintent.intent.TaskIntent;
import com.javastarterkit.patterns.modelviewintent.state.TaskItem;
import com.javastarterkit.patterns.modelviewintent.state.TaskState;
import java.util.ArrayList;
import java.util.List;

/**
 * Pure reducer for the task list.
 *
 * <p>Each intent produces a new list; the original list is never mutated.
 * Uses pattern matching in switch statements for exhaustive handling.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public final class TaskReducer implements Reducer<TaskState, TaskIntent> {

    @Override
    public TaskState reduce(TaskState state, TaskIntent intent) {
        return switch (intent) {
            case AddTask(String description) -> {
                List<TaskItem> tasks = new ArrayList<>(state.tasks());
                tasks.add(new TaskItem(description, false));
                yield state.copyWith(tasks);
            }
            case CompleteTask(int index) -> {
                if (index < 0 || index >= state.tasks().size()) {
                    throw new InvalidIntentException("Invalid task index: " + index);
                }
                List<TaskItem> tasks = new ArrayList<>(state.tasks());
                tasks.set(index, tasks.get(index).complete());
                yield state.copyWith(tasks);
            }
        };
    }
}