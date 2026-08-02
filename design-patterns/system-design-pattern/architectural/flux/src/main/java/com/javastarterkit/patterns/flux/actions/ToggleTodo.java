package com.javastarterkit.patterns.flux.actions;

/**
 * Action to toggle the completion status of a todo item.
 *
 * <p>This immutable record represents the user intent to flip the completed
 * flag of the todo at the specified index.
 *
 * @param index the zero-based index of the todo to toggle; must be non-negative
 */
public record ToggleTodo(int index) implements Action {

    /**
     * Creates a new ToggleTodo action.
     *
     * @param index the zero-based index; must be non-negative
     * @throws IllegalArgumentException if index is negative
     */
    public ToggleTodo {
        if (index < 0) {
            throw new IllegalArgumentException("index must not be negative");
        }
    }
}