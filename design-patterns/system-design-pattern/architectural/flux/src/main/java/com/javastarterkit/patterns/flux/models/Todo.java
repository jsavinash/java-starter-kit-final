package com.javastarterkit.patterns.flux.models;

import java.util.Objects;

/**
 * Immutable todo item record.
 *
 * <p>Represents a single todo item with a unique identifier, text content,
 * and completion status. Being a record, it is inherently immutable and thread-safe.
 *
 * @param id unique identifier for this todo
 * @param text the todo text content
 * @param completed whether this todo is marked as completed
 */
public record Todo(String id, String text, boolean completed) {

    /**
     * Creates a new Todo instance.
     *
     * @param id unique identifier; must not be null or blank
     * @param text todo text; must not be null
     * @param completed completion status
     * @throws NullPointerException if id is null or text is null
     * @throws IllegalArgumentException if id is blank
     */
    public Todo {
        if (id == null) {
            throw new NullPointerException("id must not be null");
        }
        if (id.isBlank()) {
            throw new IllegalArgumentException("id must not be blank");
        }
        if (text == null) {
            throw new NullPointerException("text must not be null");
        }
    }

    /**
     * Returns a new Todo with the same id and text but toggled completion status.
     *
     * @return a new Todo instance with flipped completion status
     */
    public Todo toggle() {
        return new Todo(id, text, !completed);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Todo todo = (Todo) o;
        return completed == todo.completed &&
                Objects.equals(id, todo.id) &&
                Objects.equals(text, todo.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, completed);
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", completed=" + completed +
                '}';
    }
}