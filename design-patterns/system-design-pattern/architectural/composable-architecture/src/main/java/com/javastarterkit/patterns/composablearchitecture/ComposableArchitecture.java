package com.javastarterkit.patterns.composablearchitecture;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Composable Architecture Pattern Example
 *
 * <p>Structures an application around small, independent {@link Component}s, each
 * owning its own {@link State}, {@link Action}s, and a {@link Reducer} that
 * describes how state changes in response to actions. Components are composed
 * hierarchically — a parent reducer pulls state out of global state, delegates
 * actions to child reducers, and combines the results — so complex features are
 * built by assembling simple, testable pieces.
 *
 * <p>The core building blocks (inspired by Point-Free's "The Composable
 * Architecture") are:
 * <ul>
 *   <li><b>State</b> — a record describing the data a feature owns</li>
 *   <li><b>Action</b> — a sealed interface of all possible user/system events</li>
 *   <li><b>Reducer</b> — a pure function {@code (State, Action) -> State}</li>
 *   <li><b>Store</b> — holds the current state and dispatches actions through the reducer</li>
 *   <li><b>Component</b> — bundles a state slice, action subset, and reducer so it can be composed</li>
 * </ul>
 *
 * <p>This example models a simple counter and a text-input feature, then composes
 * them into a single "form" feature to demonstrate how independent components are
 * combined without coupling.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class ComposableArchitecture {

    /**
     * Demonstrates the composable architecture: build two independent feature
     * components, compose them into a parent feature, and drive them via a store.
     */
    public static void demonstrate() {
        System.out.println("\n=== Composable Architecture Pattern ===");
        System.out.println("Build features from small, independent, composable components\n");

        // --- Build independent feature components -------------------------------
        Component<CounterState, CounterAction> counterComponent =
                Component.of(CounterState::new, CounterState.class, CounterReducer.INSTANCE);

        Component<TextState, TextAction> textComponent =
                Component.of(TextState::new, TextState.class, TextReducer.INSTANCE);

        // --- Compose them into a parent Form feature ----------------------------
        Reducer<FormState, FormAction> formReducer = Reducer.combine(
                Reducer.pullback(
                        counterComponent,
                        FormState::counter,
                        FormState::withCounter,
                        action -> action instanceof FormAction.Counter c ? c.action() : null),
                Reducer.pullback(
                        textComponent,
                        FormState::text,
                        FormState::withText,
                        action -> action instanceof FormAction.Text t ? t.action() : null));

        // --- Drive the composed store -------------------------------------------
        Store<FormState, FormAction> store = new Store<>(new FormState(), formReducer);

        System.out.println("Initial state: " + store.state());

        store.dispatch(new FormAction.Counter(new CounterAction.increment()));
        System.out.println("After increment: " + store.state());

        store.dispatch(new FormAction.Counter(new CounterAction.increment()));
        store.dispatch(new FormAction.Counter(new CounterAction.decrement()));
        System.out.println("After +1, -1:    " + store.state());

        store.dispatch(new FormAction.Text(new TextAction.change("Hello")));
        System.out.println("After text:      " + store.state());

        store.dispatch(new FormAction.Text(new TextAction.clear()));
        System.out.println("After clear:     " + store.state());

        System.out.println("\nBenefits:");
        System.out.println("- Each feature is isolated, testable, and reusable");
        System.out.println("- Features are composed without coupling via pullback/combine");
        System.out.println("- State changes are centralized and predictable (single reducer)");
        System.out.println("- Easy to reason about: (State, Action) -> State");
    }

    public static void main(String[] args) {
        demonstrate();
    }

    // =========================================================================
    // Core abstractions: State, Action, Reducer, Store, Component
    // =========================================================================

    /** Marker for state records. */
    interface State {
    }

    /** Marker for action sealed interfaces. */
    interface Action {
    }

    /**
     * A pure function that returns the next state given the current state and an
     * action. Reducers must be free of side effects so they are trivially testable.
     */
    @FunctionalInterface
    interface Reducer<S extends State, A extends Action> {
        S reduce(S state, A action);

        /**
         * Combines two reducers over the same state/action space. Each reducer is
         * applied in sequence; the second sees the state produced by the first.
         */
        static <S extends State, A extends Action> Reducer<S, A> combine(
                Reducer<S, A> first, Reducer<S, A> second) {
            return (state, action) -> {
                S afterFirst = first.reduce(state, action);
                return second.reduce(afterFirst, action);
            };
        }

        /**
         * Adapts a child reducer to a parent state/action space. {@code extract}
         * pulls the child state out of the parent, the child reducer runs, and
         * {@code inject} writes the new child state back into the parent. This is
         * the key composition operator that lets independent features be combined.
         */
        static <PS extends State, PA extends Action, CS extends State, CA extends Action>
        Reducer<PS, PA> pullback(
                Component<CS, CA> component,
                Function<PS, CS> extract,
                BiFunction<PS, CS, PS> inject,
                Function<PA, CA> mapAction) {
            return (parentState, parentAction) -> {
                CA childAction = mapAction.apply(parentAction);
                if (childAction == null) {
                    return parentState; // action not relevant to this child
                }
                CS currentChild = extract.apply(parentState);
                CS nextChild = component.reducer().reduce(currentChild, childAction);
                return inject.apply(parentState, nextChild);
            };
        }
    }

    /**
     * Holds the current state and applies the reducer when actions are dispatched.
     * In a real app this would also notify observers; here it keeps things minimal.
     */
    static final class Store<S extends State, A extends Action> {
        private S state;
        private final Reducer<S, A> reducer;

        Store(S initialState, Reducer<S, A> reducer) {
            this.state = initialState;
            this.reducer = reducer;
        }

        S state() {
            return state;
        }

        void dispatch(A action) {
            state = reducer.reduce(state, action);
        }
    }

    /**
     * Bundles a feature's initial-state factory and reducer so it can be composed
     * into larger features via {@link Reducer#pullback}.
     */
    static final class Component<S extends State, A extends Action> {
        private final Supplier<S> initialState;
        private final Class<S> stateType;
        private final Reducer<S, A> reducer;

        private Component(Supplier<S> initialState, Class<S> stateType, Reducer<S, A> reducer) {
            this.initialState = initialState;
            this.stateType = stateType;
            this.reducer = reducer;
        }

        static <S extends State, A extends Action> Component<S, A> of(
                Supplier<S> initialState, Class<S> stateType, Reducer<S, A> reducer) {
            return new Component<>(initialState, stateType, reducer);
        }

        Supplier<S> initialState() {
            return initialState;
        }

        Class<S> stateType() {
            return stateType;
        }

        Reducer<S, A> reducer() {
            return reducer;
        }
    }

    // =========================================================================
    // Feature 1: Counter — a simple increment/decrement component
    // =========================================================================

    record CounterState(int count) implements State {
        CounterState() {
            this(0);
        }

        CounterState withCount(int newCount) {
            return new CounterState(newCount);
        }

        @Override
        public String toString() {
            return "Counter(count=" + count + ")";
        }
    }

    sealed interface CounterAction extends Action {
        record increment() implements CounterAction {
        }

        record decrement() implements CounterAction {
        }
    }

    enum CounterReducer implements Reducer<CounterState, CounterAction> {
        INSTANCE;

        @Override
        public CounterState reduce(CounterState state, CounterAction action) {
            return switch (action) {
                case CounterAction.increment ignored -> state.withCount(state.count() + 1);
                case CounterAction.decrement ignored -> state.withCount(state.count() - 1);
            };
        }
    }

    // =========================================================================
    // Feature 2: Text — a simple text-input component
    // =========================================================================

    record TextState(String value) implements State {
        TextState() {
            this("");
        }

        TextState withValue(String newValue) {
            return new TextState(newValue);
        }

        @Override
        public String toString() {
            return "Text(value='" + value + "')";
        }
    }

    sealed interface TextAction extends Action {
        record change(String text) implements TextAction {
        }

        record clear() implements TextAction {
        }
    }

    enum TextReducer implements Reducer<TextState, TextAction> {
        INSTANCE;

        @Override
        public TextState reduce(TextState state, TextAction action) {
            return switch (action) {
                case TextAction.change a -> state.withValue(a.text());
                case TextAction.clear ignored -> state.withValue("");
            };
        }
    }

    // =========================================================================
    // Composed Feature: Form — combines Counter + Text
    // =========================================================================

    record FormState(CounterState counter, TextState text) implements State {
        FormState() {
            this(new CounterState(), new TextState());
        }

        FormState withCounter(CounterState newCounter) {
            return new FormState(newCounter, text);
        }

        FormState withText(TextState newText) {
            return new FormState(counter, newText);
        }

        @Override
        public String toString() {
            return "Form{" + counter + ", " + text + "}";
        }
    }

    /**
     * Parent action that wraps child actions. Each variant carries the child
     * action so the parent reducer can route it to the right child via pullback.
     */
    sealed interface FormAction extends Action {
        record Counter(CounterAction action) implements FormAction {
        }

        record Text(TextAction action) implements FormAction {
        }
    }
}