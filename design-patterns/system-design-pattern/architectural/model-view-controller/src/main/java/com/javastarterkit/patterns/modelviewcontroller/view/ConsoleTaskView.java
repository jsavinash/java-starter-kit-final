package com.javastarterkit.patterns.modelviewcontroller.view;

import com.javastarterkit.patterns.modelviewcontroller.model.Task;
import com.javastarterkit.patterns.modelviewcontroller.model.TaskList;

/**
 * Console view that renders tasks as plain text.
 *
 * <p>This is a <b>View</b> in the MVC pattern. It renders the model data
 * to the console and observes model changes. It has no knowledge of the
 * controller or business logic.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public final class ConsoleTaskView implements TaskView {

    @Override
    public void render(TaskList model) {
        System.out.println("  Console view (" + model.completedCount() + "/" + model.size() + " completed):");
        if (model.size() == 0) {
            System.out.println("    (no tasks)");
            return;
        }
        int index = 0;
        for (Task task : model.tasks()) {
            System.out.println("    " + index + ". " + task);
            index++;
        }
    }

    @Override
    public void onModelChanged(TaskList model) {
        // In a real UI, this would trigger a re-render automatically.
        // In this console example, we rely on explicit render() calls.
    }
}