package com.javastarterkit.patterns.modelviewcontroller.view;

import com.javastarterkit.patterns.modelviewcontroller.model.TaskList;

/**
 * View contract in the MVC pattern.
 *
 * <p>Views render the model data to the user and observe model changes.
 * Views have <b>no knowledge</b> of the controller or business logic.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public interface TaskView {

    /**
     * Renders the current state of the model.
     *
     * @param model the task list model to render
     */
    void render(TaskList model);

    /**
     * Called by the model when its state changes.
     *
     * @param model the task list model that changed
     */
    default void onModelChanged(TaskList model) {
        // Optional hook: re-render when the model changes.
    }
}