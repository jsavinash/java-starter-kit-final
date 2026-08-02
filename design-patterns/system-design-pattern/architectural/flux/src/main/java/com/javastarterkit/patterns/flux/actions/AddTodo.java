package com.javastarterkit.patterns.flux.actions;

/**
 * Action to add a new todo item.
 *
 * <p>This immutable record represents the user intent to create a new todo
 * with the specified text content.
 *
 * @param text the todo text content; must not be null or blank
 */
public record AddTodo(String text) implements Action {

    /**
     * Creates a new AddTodo action.
     *
     * @param text the todo text; must not be null or blank
     * @throws NullPointerException if text is null
     * @throws IllegalArgumentException if text is blank
     */
    public AddTodo {
        if (text == null) {
            throw new NullPointerException("text must not be null");
        }
        if (text.isBlank()) {
            throw new IllegalArgumentException("text must not be blank");
        }
    }
}