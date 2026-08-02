package com.javastarterkit.patterns.flux.actions;

/**
 * Marker interface for all Flux actions.
 *
 * <p>Actions are immutable descriptors of user intents. They flow unidirectionally
 * from the View layer through the Dispatcher to the Stores.
 *
 * <p>This sealed interface permits only the defined action types, ensuring
 * exhaustive pattern matching in stores and preventing uncontrolled expansion
 * of the action space.
 */
public sealed interface Action permits
        AddTodo,
        ToggleTodo,
        RemoveTodo,
        ClearCompleted,
        FilterAction {
}