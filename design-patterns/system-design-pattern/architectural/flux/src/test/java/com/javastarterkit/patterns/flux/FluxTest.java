package com.javastarterkit.patterns.flux;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.javastarterkit.patterns.flux.actions.AddTodo;
import com.javastarterkit.patterns.flux.actions.ClearCompleted;
import com.javastarterkit.patterns.flux.actions.FilterAction;
import com.javastarterkit.patterns.flux.actions.RemoveTodo;
import com.javastarterkit.patterns.flux.actions.ToggleTodo;
import com.javastarterkit.patterns.flux.core.Dispatcher;
import com.javastarterkit.patterns.flux.models.Filter;
import com.javastarterkit.patterns.flux.models.FilterState;
import com.javastarterkit.patterns.flux.models.Todo;
import com.javastarterkit.patterns.flux.models.TodoState;
import com.javastarterkit.patterns.flux.stores.FilterStore;
import com.javastarterkit.patterns.flux.stores.TodoStore;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests verifying the Flux pattern: actions dispatched through a central
 * dispatcher update stores, and subscribers are notified of state changes.
 */
class FluxTest {

    private Dispatcher dispatcher;
    private TodoStore todoStore;
    private FilterStore filterStore;

    @BeforeEach
    void setUp() {
        dispatcher = new Dispatcher();
        todoStore = new TodoStore();
        filterStore = new FilterStore();

        dispatcher.register(todoStore);
        dispatcher.register(filterStore);
    }

    @Test
    @DisplayName("dispatching AddTodo creates a new todo")
    void addTodoCreatesNewTodo() {
        dispatcher.dispatch(new AddTodo("Buy milk"));

        List<Todo> todos = todoStore.getState().todos();
        assertEquals(1, todos.size());
        assertEquals("Buy milk", todos.getFirst().text());
        assertFalse(todos.getFirst().completed());
    }

    @Test
    @DisplayName("dispatching ToggleTodo flips completion status")
    void toggleTodoFlipsCompletion() {
        dispatcher.dispatch(new AddTodo("Task"));
        dispatcher.dispatch(new ToggleTodo(0));

        assertTrue(todoStore.getState().todos().getFirst().completed());
    }

    @Test
    @DisplayName("dispatching RemoveTodo deletes the todo at index")
    void removeTodoDeletesAtIndex() {
        dispatcher.dispatch(new AddTodo("A"));
        dispatcher.dispatch(new AddTodo("B"));
        dispatcher.dispatch(new AddTodo("C"));
        dispatcher.dispatch(new RemoveTodo(1));

        List<Todo> todos = todoStore.getState().todos();
        assertEquals(2, todos.size());
        assertEquals("A", todos.getFirst().text());
        assertEquals("C", todos.getLast().text());
    }

    @Test
    @DisplayName("dispatching ClearCompleted removes completed todos only")
    void clearCompletedRemovesOnlyCompleted() {
        dispatcher.dispatch(new AddTodo("A"));
        dispatcher.dispatch(new AddTodo("B"));
        dispatcher.dispatch(new ToggleTodo(0)); // complete A
        dispatcher.dispatch(new ClearCompleted());

        List<Todo> todos = todoStore.getState().todos();
        assertEquals(1, todos.size());
        assertEquals("B", todos.getFirst().text());
    }

    @Test
    @DisplayName("dispatching FilterAction.Set updates the filter store")
    void setFilterUpdatesFilterStore() {
        dispatcher.dispatch(new FilterAction.Set(Filter.COMPLETED));
        assertEquals(Filter.COMPLETED, filterStore.getState().filter());
    }

    @Test
    @DisplayName("todo store notifies subscribers on state change")
    void todoStoreNotifiesSubscribers() {
        List<TodoState> received = new ArrayList<>();
        todoStore.subscribe(received::add);

        dispatcher.dispatch(new AddTodo("Subscribed todo"));

        assertEquals(1, received.size());
        assertEquals(1, received.getFirst().todos().size());
    }

    @Test
    @DisplayName("filter store notifies subscribers on state change")
    void filterStoreNotifiesSubscribers() {
        List<FilterState> received = new ArrayList<>();
        filterStore.subscribe(received::add);

        dispatcher.dispatch(new FilterAction.Set(Filter.ACTIVE));

        assertEquals(1, received.size());
        assertEquals(Filter.ACTIVE, received.getFirst().filter());
    }

    @Test
    @DisplayName("multiple subscribers are notified")
    void multipleSubscribersAreNotified() {
        List<TodoState> received1 = new ArrayList<>();
        List<TodoState> received2 = new ArrayList<>();

        todoStore.subscribe(received1::add);
        todoStore.subscribe(received2::add);

        dispatcher.dispatch(new AddTodo("Todo"));

        assertEquals(1, received1.size());
        assertEquals(1, received2.size());
        assertEquals(1, received1.getFirst().todos().size());
        assertEquals(1, received2.getFirst().todos().size());
    }

    @Test
    @DisplayName("demonstrate runs without throwing")
    void demonstrateRunsSuccessfully() {
        Main.main(new String[0]);
    }
}