package com.javastarterkit.patterns.flux;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Flux Pattern Example
 *
 * <p>Flux enforces a unidirectional data flow: user interactions dispatch
 * {@link Action}s through a central {@link Dispatcher}; {@link Store}s receive
 * those actions, update their state, and notify subscribers. Views render from
 * store state and never mutate it directly.
 *
 * <p>This example models a simple todo list application with two stores:
 * <ul>
 *   <li><b>TodoStore</b> — manages the list of todos</li>
 *   <li><b>FilterStore</b> — manages the current visibility filter</li>
 * </ul>
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class Flux {

    /**
     * Demonstrates the Flux flow: dispatch actions to stores, stores update
     * state and notify subscribers, views render from store state.
     */
    public static void demonstrate() {
        System.out.println("\n=== Flux Pattern ===");
        System.out.println("Unidirectional data flow: Actions -> Dispatcher -> Store -> View\n");

        Dispatcher dispatcher = new Dispatcher();

        TodoStore todoStore = new TodoStore();
        FilterStore filterStore = new FilterStore();

        // Subscribe views to store changes
        Consumer<TodoStore.TodoState> todoView = state -> System.out.println("  [TodoView] " + state);
        Consumer<FilterStore.FilterState> filterView = state -> System.out.println("  [FilterView] " + state);

        todoStore.subscribe(todoView);
        filterStore.subscribe(filterView);

        // --- Dispatch actions ---------------------------------------------------
        System.out.println("--- Dispatching actions ---");

        dispatcher.register(todoStore);
        dispatcher.register(filterStore);

        dispatcher.dispatch(new AddTodo("Buy milk"));
        dispatcher.dispatch(new AddTodo("Write code"));
        dispatcher.dispatch(new AddTodo("Read book"));

        dispatcher.dispatch(new ToggleTodo(0));
        dispatcher.dispatch(new ToggleTodo(2));

        dispatcher.dispatch(new SetFilter(Filter.COMPLETED));

        dispatcher.dispatch(new RemoveTodo(1));

        dispatcher.dispatch(new ClearCompleted());

        // --- Query final state --------------------------------------------------
        System.out.println("\n--- Final state ---");
        System.out.println("Todos: " + todoStore.getState().todos());
        System.out.println("Filter: " + filterStore.getState().filter());

        System.out.println("\nBenefits:");
        System.out.println("- Predictable state changes through a single dispatcher");
        System.out.println("- Decoupled stores communicate only via actions");
        System.out.println("- Easy to debug: every state change is an explicit action");
        System.out.println("- Scales well: add stores/actions without tight coupling");
    }

    public static void main(String[] args) {
        demonstrate();
    }

    // =========================================================================
    // Core abstractions: Action, Dispatcher, Store
    // =========================================================================

    /** Marker for all actions. */
    sealed interface Action permits AddTodo, ToggleTodo, RemoveTodo, SetFilter, ClearCompleted, FilterAction {
    }

    /** Central dispatcher: routes actions to registered stores. */
    static final class Dispatcher {
        private final List<Store<?>> stores = new ArrayList<>();

        void register(Store<?> store) {
            stores.add(store);
        }

        void dispatch(Action action) {
            System.out.println("  [Dispatch] " + action.getClass().getSimpleName());
            for (Store<?> store : stores) {
                store.onAction(action);
            }
        }
    }

    /** Base store: holds state, reducers, and subscribers. */
    abstract static class Store<S> {
        private S state;
        private final List<Consumer<S>> subscribers = new ArrayList<>();

        Store(S initialState) {
            this.state = initialState;
        }

        S getState() {
            return state;
        }

        void setState(S newState) {
            this.state = newState;
            notifySubscribers();
        }

        void subscribe(Consumer<S> subscriber) {
            subscribers.add(subscriber);
        }

        void onAction(Action action) {
            // Default: no-op
        }

        private void notifySubscribers() {
            for (Consumer<S> sub : subscribers) {
                sub.accept(state);
            }
        }
    }

    // =========================================================================
    // Todo Store
    // =========================================================================

    record Todo(String id, String text, boolean completed) {
    }

    static final class TodoStore extends Store<TodoStore.TodoState> {
        record TodoState(List<Todo> todos) {
            TodoState() {
                this(new ArrayList<>());
            }

            TodoState withTodos(List<Todo> newTodos) {
                return new TodoState(newTodos);
            }
        }

        private int nextId = 1;

        TodoStore() {
            super(new TodoState());
        }

        @Override
        void onAction(Action action) {
            switch (action) {
                case AddTodo a -> addTodo(a.text());
                case ToggleTodo t -> toggleTodo(t.index());
                case RemoveTodo r -> removeTodo(r.index());
                case ClearCompleted ignored -> clearCompleted();
                default -> { }
            }
        }

        private void addTodo(String text) {
            List<Todo> todos = new ArrayList<>(getState().todos());
            todos.add(new Todo(String.valueOf(nextId++), text, false));
            setState(getState().withTodos(todos));
        }

        private void toggleTodo(int index) {
            List<Todo> todos = new ArrayList<>(getState().todos());
            if (index < 0 || index >= todos.size()) return;
            Todo existing = todos.get(index);
            todos.set(index, new Todo(existing.id(), existing.text(), !existing.completed()));
            setState(getState().withTodos(todos));
        }

        private void removeTodo(int index) {
            List<Todo> todos = new ArrayList<>(getState().todos());
            if (index < 0 || index >= todos.size()) return;
            todos.remove(index);
            setState(getState().withTodos(todos));
        }

        private void clearCompleted() {
            List<Todo> todos = getState().todos().stream()
                    .filter(t -> !t.completed())
                    .toList();
            setState(getState().withTodos(todos));
        }
    }

    // =========================================================================
    // Filter Store
    // =========================================================================

    enum Filter {
        ALL, ACTIVE, COMPLETED
    }

    static final class FilterStore extends Store<FilterStore.FilterState> {
        record FilterState(Filter filter) {
            FilterState() {
                this(Filter.ALL);
            }

            FilterState withFilter(Filter newFilter) {
                return new FilterState(newFilter);
            }

            @Override
            public String toString() {
                return "Filter{filter=" + filter + "}";
            }
        }

        static final String APPLICATION_ID = "flux-app";

        FilterStore() {
            super(new FilterState());
        }

        @Override
        void onAction(Action action) {
            if (action instanceof FilterAction.Set set) {
                setState(getState().withFilter(set.filter()));
            }
        }
    }

    sealed interface FilterAction extends Action {
        record Set(Filter filter) implements FilterAction {
        }
    }

    // =========================================================================
    // Actions
    // =========================================================================

    record AddTodo(String text) implements Action {
    }

    record ToggleTodo(int index) implements Action {
    }

    record RemoveTodo(int index) implements Action {
    }

    record SetFilter(Filter filter) implements Action {
    }

    record ClearCompleted() implements Action {
    }
}