package com.javastarterkit.patterns.modelviewintent;

import com.javastarterkit.patterns.modelviewintent.core.MviStore;
import com.javastarterkit.patterns.modelviewintent.intent.AddTask;
import com.javastarterkit.patterns.modelviewintent.intent.CompleteTask;
import com.javastarterkit.patterns.modelviewintent.intent.CounterIntent;
import com.javastarterkit.patterns.modelviewintent.intent.Decrement;
import com.javastarterkit.patterns.modelviewintent.intent.Increment;
import com.javastarterkit.patterns.modelviewintent.intent.Reset;
import com.javastarterkit.patterns.modelviewintent.intent.TaskIntent;
import com.javastarterkit.patterns.modelviewintent.reducer.CounterReducer;
import com.javastarterkit.patterns.modelviewintent.reducer.TaskReducer;
import com.javastarterkit.patterns.modelviewintent.state.CounterState;
import com.javastarterkit.patterns.modelviewintent.state.TaskState;
import com.javastarterkit.patterns.modelviewintent.view.CounterView;
import com.javastarterkit.patterns.modelviewintent.view.TaskListView;

/**
 * Main entry point demonstrating the Model-View-Intent (MVI) pattern.
 *
 * <p>Wires the store, reducer, and views together and demonstrates the
 * unidirectional data flow: View → Intent → Reducer → State → View.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public final class ModelViewIntentApp {

    private ModelViewIntentApp() {
        // Prevent instantiation
    }

    /**
     * Demonstrates the MVI pattern end-to-end.
     */
    public static void demonstrate() {
        System.out.println("\n=== Model-View-Intent (MVI) Pattern ===");
        System.out.println("Unidirectional data flow: View -> Intent -> Reducer -> State -> View\n");

        // --- Counter example ---------------------------------------------------
        MviStore<CounterState, CounterIntent> counterStore = new MviStore<>(
                new CounterState(0), new CounterReducer());
        CounterView counterView = new CounterView();
        counterStore.addObserver(counterView);

        System.out.println("--- Counter: initial state ---");
        counterView.render(counterStore.state());

        System.out.println("\n--- User dispatches Increment ---");
        counterStore.dispatch(new Increment());
        counterView.render(counterStore.state());

        System.out.println("\n--- User dispatches Increment x2 ---");
        counterStore.dispatch(new Increment());
        counterStore.dispatch(new Increment());
        counterView.render(counterStore.state());

        System.out.println("\n--- User dispatches Decrement ---");
        counterStore.dispatch(new Decrement());
        counterView.render(counterStore.state());

        System.out.println("\n--- User dispatches Reset ---");
        counterStore.dispatch(new Reset());
        counterView.render(counterStore.state());

        // --- Task list example -------------------------------------------------
        System.out.println("\n--- Task list: add and complete tasks ---");
        MviStore<TaskState, TaskIntent> taskStore = new MviStore<>(
                new TaskState(java.util.List.of()), new TaskReducer());
        TaskListView taskView = new TaskListView();
        taskStore.addObserver(taskView);

        taskStore.dispatch(new AddTask("Buy groceries"));
        taskStore.dispatch(new AddTask("Write report"));
        taskStore.dispatch(new CompleteTask(0));
        taskView.render(taskStore.state());

        System.out.println("\nBenefits:");
        System.out.println("- Unidirectional data flow makes state changes predictable");
        System.out.println("- State is immutable (no accidental mutation)");
        System.out.println("- Reducers are pure functions (easy to test)");
        System.out.println("- Single source of truth for the View");
    }

    /**
     * Main entry point.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        demonstrate();
    }
}