package com.javastarterkit.patterns.flux.actions;

import com.javastarterkit.patterns.flux.actions.Action;

/**
 * Action to remove all completed todo items.
 *
 * <p>This immutable record represents the user intent to clear the list
 * of todos that are marked as completed.
 */
public record ClearCompleted() implements Action {
}