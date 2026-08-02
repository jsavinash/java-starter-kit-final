package com.javastarterkit.patterns.flux.stores;

import com.javastarterkit.patterns.flux.actions.AddTodo;
import com.javastarterkit.patterns.flux.actions.ClearCompleted;
import com.javastarterkit.patterns.flux.actions.RemoveTodo;
import com.javastarterkit.patterns.flux.actions.ToggleTodo;
import com.javastarterkit.patterns.flux.actions.Action;
import com.javastarterkit.patterns.flux.core.Store;
import com.javastarterkit.patterns.flux.models.Filter;
import com.javastarterkit.patterns.flux.models.Todo;
import com.javastarterkit.patterns.flux.models.TodoState;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Store that manages the todo list state.
 *
 * <p>Handles todo-related actions (add, toggle, remove, clear completed) and
 * maintains an immutable {@link TodoState}. Thread-safe ID generation via {@link AtomicInteger}.
 */
public final class TodoStore extends Store<TodoState> {

    private final AtomicInteger nextId;

    /**
     * Creates a new TodoStore with an empty todo list.
     */
    public TodoStore() {
        super(new TodoState(new ArrayList<>()));
        this.nextId = new AtomicInteger(1);
    }

    @Override
    public void onAction(final Action action) {
        if (action instanceof AddTodo add) {
            addTodo(add.text());
        } else if (action instanceof ToggleTodo toggle) {
            toggleTodo(toggle.index());
        } else if (action instanceof RemoveTodo remove) {
            removeTodo(remove.index());
        } else if (action instanceof ClearCompleted) {
            clearCompleted();
        }
    }

    /**
     * Adds a new todo with the given text.
     *
     * @param text the todo text; must not be null or blank
     */
    private void addTodo(final String text) {
        final List<Todo> current = new ArrayList<>(getState().todos());
        final String id = String.valueOf(nextId.getAndIncrement());
        current.add(new Todo(id, text, false));
        setState(getState().withTodos(current));
    }

    /**
     * Toggles the completion status of the todo at the given index.
     *
     * @param index the zero-based index
     */
    private void toggleTodo(final int index) {
        final List<Todo> current = new ArrayList<>(getState().todos());
        if (index < 0 || index >= current.size()) {
            return;
        }
        final Todo existing = current.get(index);
        current.set(index, existing.toggle());
        setState(getState().withTodos(current));
    }

    /**
     * Removes the todo at the given index.
     *
     * @param index the zero-based index
     */
    private void removeTodo(final int index) {
        final List<Todo> current = new ArrayList<>(getState().todos());
        if (index < 0 || index >= current.size()) {
            return;
        }
        current.remove(index);
        setState(getState().withTodos(current));
    }

    /**
     * Removes all completed todos.
     */
    private void clearCompleted() {
        final List<Todo> current = getState().todos().stream()
                .filter(todo -> !todo.completed())
                .toList();
        setState(getState().withTodos(new ArrayList<>(current)));
    }
}