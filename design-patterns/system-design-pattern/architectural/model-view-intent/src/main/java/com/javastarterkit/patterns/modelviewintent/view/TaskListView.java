package com.javastarterkit.patterns.modelviewintent.view;

import com.javastarterkit.patterns.modelviewintent.core.ViewObserver;
import com.javastarterkit.patterns.modelviewintent.state.TaskItem;
import com.javastarterkit.patterns.modelviewintent.state.TaskState;

/**
 * Task list view: renders tasks as text.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public final class TaskListView implements ViewObserver<TaskState> {

    @Override
    public void onStateChanged(TaskState state) {
        render(state);
    }

    /**
     * Renders the task list state to the console.
     *
     * @param state the task state
     */
    public void render(TaskState state) {
        System.out.println("  Task view (" + completedCount(state) + "/" + state.tasks().size() + "):");
        if (state.tasks().isEmpty()) {
            System.out.println("    (no tasks)");
            return;
        }
        for (int i = 0; i < state.tasks().size(); i++) {
            TaskItem task = state.tasks().get(i);
            String marker = task.completed() ? "[x]" : "[ ]";
            System.out.println("    " + i + ". " + marker + " " + task.description());
        }
    }

    private static long completedCount(TaskState state) {
        return state.tasks().stream().filter(TaskItem::completed).count();
    }
}