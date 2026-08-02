package com.javastarterkit.patterns.flux;

import com.javastarterkit.patterns.flux.actions.AddTodo;
import com.javastarterkit.patterns.flux.actions.ClearCompleted;
import com.javastarterkit.patterns.flux.actions.FilterAction;
import com.javastarterkit.patterns.flux.actions.RemoveTodo;
import com.javastarterkit.patterns.flux.actions.ToggleTodo;
import com.javastarterkit.patterns.flux.core.Dispatcher;
import com.javastarterkit.patterns.flux.models.Filter;
import com.javastarterkit.patterns.flux.stores.FilterStore;
import com.javastarterkit.patterns.flux.stores.TodoStore;

/**
 * Main entry point demonstrating the Flux pattern.
 *
 * <p>This demonstration shows the unidirectional data flow:
 * Actions → Dispatcher → Stores → Subscribers (Views)
 */
public final class Main {

    /**
     * Demonstrates the Flux pattern with a todo list application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(final String[] args) {
        System.out.println("\n=== Flux Pattern ===");
        System.out.println("Unidirectional data flow: Actions -> Dispatcher -> Store -> View\n");

        final Dispatcher dispatcher = new Dispatcher();
        final TodoStore todoStore = new TodoStore();
        final FilterStore filterStore = new FilterStore();

        // Subscribe views to store changes
        todoStore.subscribe(state -> System.out.println("  [TodoView] " + state));
        filterStore.subscribe(state -> System.out.println("  [FilterView] " + state));

        // Register stores with dispatcher
        dispatcher.register(todoStore);
        dispatcher.register(filterStore);

        // --- Dispatch actions ---------------------------------------------------
        System.out.println("--- Dispatching actions ---");

        dispatcher.dispatch(new AddTodo("Buy milk"));
        dispatcher.dispatch(new AddTodo("Write code"));
        dispatcher.dispatch(new AddTodo("Read book"));

        dispatcher.dispatch(new ToggleTodo(0));
        dispatcher.dispatch(new ToggleTodo(2));

        dispatcher.dispatch(new FilterAction.Set(Filter.COMPLETED));

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
}