package com.javastarterkit.patterns.flux.actions;

/**
 * Action to remove a todo item at a specific index.
 *
 * <p>This immutable record represents the user intent to delete the todo
 * at the specified zero-based index.
 *
 * @param index the zero-based index of the todo to remove; must be non-negative
 */
public record RemoveTodo(int index) implements Action {

    /**
     * Creates a new RemoveTodo action.
     *
     * @param index the zero-based index; must be non-negative
     * @throws IllegalArgumentException if index is negative
     */
    public RemoveTodo {
        if (index < 0) {
            throw new IllegalArgumentException("index must not be negative");
        }
    }
}