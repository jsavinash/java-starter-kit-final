package com.javastarterkit.patterns.flux.models;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Immutable state container for the TodoStore.
 *
 * <p>Holds the complete list of todos. Being a record, it is inherently immutable.
 *
 * @param todos the list of todos; never null, may be empty but never null
 */
public record TodoState(List<Todo> todos) {

    /**
     * Canonical constructor that validates the list.
     *
     * <p>Note: As a record, the component field is implicitly final and cannot be reassigned
     * in the compact constructor. Callers should ensure they pass an unmodifiable list.
     *
     * @param todos the list of todos
     */
    public TodoState {
        Objects.requireNonNull(todos, "todos must not be null");
    }

    /**
     * Returns a new TodoState with the given todos list.
     *
     * @param newTodos the new list of todos; must not be null
     * @return a new TodoState instance
     */
    public TodoState withTodos(final List<Todo> newTodos) {
        return new TodoState(newTodos);
    }

    @Override
    public String toString() {
        return "TodoState{" +
                "todos=" + todos +
                '}';
    }
}