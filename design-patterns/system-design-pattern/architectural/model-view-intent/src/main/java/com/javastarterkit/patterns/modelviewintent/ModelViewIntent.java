package com.javastarterkit.patterns.modelviewintent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Model-View-Intent (MVI) Pattern Example
 *
 * <p><b>Model-View-Intent</b> is a unidirectional data-flow architecture
 * built on three core concepts:
 * <ul>
 *   <li><b>Intent</b> — a user's action expressed as an immutable message
 *       (e.g., {@link Increment}, {@link Decrement}, {@link Reset}). Intents
 *       are the <i>only</i> way to change state.</li>
 *   <li><b>Model (State)</b> — an immutable snapshot of the UI state
 *       ({@link CounterState}, {@link TaskState}). The View always renders
 *       from this single source of truth.</li>
 *   <li><b>View</b> — renders the current state and dispatches intents.
 *       The View never mutates state directly.</li>
 * </ul>
 *
 * <p>Between the View and the Model sits a <b>Reducer</b> (also called
 * {@code reduce()}) — a pure function that takes the current state and an
 * intent, and produces a <b>new</b> state. This guarantees:
 * <ul>
 *   <li>State changes are predictable and traceable.</li>
 *   <li>State is immutable — no accidental mutation.</li>
 *   <li>The data flow is strictly one-directional:
 *       View → Intent → Reducer → State → View.</li>
 * </ul>
 *
 * <p>This self-contained example models a counter and a task list:
 * <ul>
 *   <li><b>Intents</b> — each user action is an immutable record</li>
 *   <li><b>State</b> — immutable snapshot with a {@code copyWith()} helper</li>
 *   <li><b>Reducer</b> — pure functions that map (state, intent) → state</li>
 *   <li><b>View</b> — {@link CounterView} and {@link TaskListView} render
 *       state and dispatch intents</li>
 *   <li><b>Store</b> — {@link MviStore} holds the current state, reduces
 *       intents, and notifies view observers</li>
 * </ul>
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class ModelViewIntent {

    /**
     * Demonstrates MVI: create a store for a counter and a task list, dispatch
     * intents, and show that each intent produces an immutable state update.
     */
    public static void demonstrate() {
        System.out.println("\n=== Model-View-Intent (MVI) Pattern ===");
        System.out.println("Unidirectional data flow: View -> Intent -> Reducer -> State -> View\n");

        // --- Counter example ---------------------------------------------------
        MviStore<CounterState, CounterIntent> counterStore = new MviStore<>(
                new CounterState(0), ModelViewIntent::reduceCounter);
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
                new TaskState(List.of()), ModelViewIntent::reduceTask);
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

    public static void main(String[] args) {
        demonstrate();
    }

    // =========================================================================
    // INTENTS — immutable user actions
    // =========================================================================

    /** Sealed base for counter intents. */
    sealed interface CounterIntent permits Increment, Decrement, Reset {
    }

    /** Intent: increment the counter. */
    record Increment() implements CounterIntent {
    }

    /** Intent: decrement the counter. */
    record Decrement() implements CounterIntent {
    }

    /** Intent: reset the counter to zero. */
    record Reset() implements CounterIntent {
    }

    /** Sealed base for task list intents. */
    sealed interface TaskIntent permits AddTask, CompleteTask {
    }

    /** Intent: add a new task. */
    record AddTask(String description) implements TaskIntent {
    }

    /** Intent: mark a task as complete. */
    record CompleteTask(int index) implements TaskIntent {
    }

    // =========================================================================
    // STATE (MODEL) — immutable snapshots of the UI
    // =========================================================================

    /** Immutable counter state. */
    record CounterState(int count) {
        CounterState copyWith(int count) {
            return new CounterState(count);
        }

        @Override
        public String toString() {
            return "CounterState{count=" + count + "}";
        }
    }

    /** Immutable task state. */
    record TaskState(List<TaskItem> tasks) {
        TaskState copyWith(List<TaskItem> tasks) {
            return new TaskState(List.copyOf(tasks));
        }

        @Override
        public String toString() {
            return "TaskState{tasks=" + tasks + "}";
        }
    }

    /** A single immutable task item. */
    record TaskItem(String description, boolean completed) {
        TaskItem complete() {
            return new TaskItem(description, true);
        }
    }

    // =========================================================================
    // REDUCERS — pure functions: (state, intent) -> new state
    // =========================================================================

    /**
     * Pure reducer for the counter. Given the current state and an intent,
     * returns an entirely <b>new</b> state — the original is never mutated.
     */
    static CounterState reduceCounter(CounterState state, CounterIntent intent) {
        return switch (intent) {
            case Increment ignored -> state.copyWith(state.count() + 1);
            case Decrement ignored -> state.copyWith(state.count() - 1);
            case Reset ignored -> state.copyWith(0);
        };
    }

    /**
     * Pure reducer for the task list. Each intent produces a new list;
     * the original list is never mutated.
     */
    static TaskState reduceTask(TaskState state, TaskIntent intent) {
        return switch (intent) {
            case AddTask(String description) -> {
                List<TaskItem> tasks = new ArrayList<>(state.tasks());
                tasks.add(new TaskItem(description, false));
                yield state.copyWith(tasks);
            }
            case CompleteTask(int index) -> {
                if (index < 0 || index >= state.tasks().size()) {
                    throw new IllegalArgumentException("Invalid task index: " + index);
                }
                List<TaskItem> tasks = new ArrayList<>(state.tasks());
                tasks.set(index, tasks.get(index).complete());
                yield state.copyWith(tasks);
            }
        };
    }

    // =========================================================================
    // STORE — holds state, reduces intents, notifies views
    // =========================================================================

    /**
     * The MVI store is the single source of truth. It:
     * <ol>
     *   <li>Holds the current immutable {@code S} state</li>
     *   <li>Reduces dispatched {@code I} intents with the given reducer</li>
     *   <li>Notifies registered view observers on every state change</li>
     * </ol>
     */
    static final class MviStore<S, I> {
        private S state;
        private final Function<IntentWithState<S, I>, S> reducer;
        private final List<ViewObserver<S>> observers = new ArrayList<>();

        MviStore(S initialState, Reducer<S, I> reducer) {
            this.state = initialState;
            this.reducer = (intentWrapper) -> reducer.reduce(intentWrapper.state(), intentWrapper.intent());
        }

        S state() {
            return state;
        }

        void addObserver(ViewObserver<S> observer) {
            observers.add(observer);
        }

        /** The only way to change state: dispatch an intent. */
        void dispatch(I intent) {
            state = reducer.apply(new IntentWithState<>(state, intent));
            for (ViewObserver<S> observer : observers) {
                observer.onStateChanged(state);
            }
        }
    }

    /** Carrier for a state + intent pair passed to the reducer. */
    record IntentWithState<S, I>(S state, I intent) {
    }

    /** Functional interface for a pure reducer. */
    @FunctionalInterface
    interface Reducer<S, I> {
        S reduce(S state, I intent);
    }

    /** Observer notified when the store's state changes. */
    interface ViewObserver<S> {
        void onStateChanged(S state);
    }

    // =========================================================================
    // VIEWS — render state and dispatch intents (never mutate state)
    // =========================================================================

    /** Counter view: renders state as text. */
    static final class CounterView implements ViewObserver<CounterState> {
        @Override
        public void onStateChanged(CounterState state) {
            render(state);
        }

        void render(CounterState state) {
            System.out.println("  Counter view: count = " + state.count());
        }
    }

    /** Task list view: renders tasks as text. */
    static final class TaskListView implements ViewObserver<TaskState> {
        @Override
        public void onStateChanged(TaskState state) {
            render(state);
        }

        void render(TaskState state) {
            System.out.println("  Task view (" + completedCount(state) + "/" + state.tasks().size() + "):");
            if (state.tasks().isEmpty()) {
                System.out.println("    (no tasks)");
                return;
            }
            for (int i = 0; i < state.tasks().size(); i++) {
                TaskItem task = state.tasks().get(i);
                String marker = task.completed() ? "[x]" : "[ ]";
                System.out.println("    " + i + ". " + marker + " " + task.description());
            }
        }

        private static long completedCount(TaskState state) {
            return state.tasks().stream().filter(TaskItem::completed).count();
        }
    }
}