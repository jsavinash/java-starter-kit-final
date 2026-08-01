package com.javastarterkit.patterns.composablearchitecture.core.component;

import com.javastarterkit.patterns.composablearchitecture.core.Action;
import com.javastarterkit.patterns.composablearchitecture.core.Reducer;
import com.javastarterkit.patterns.composablearchitecture.core.State;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Bundles a feature's initial-state factory, state type, and reducer into a
 * self-contained unit that can be composed into larger features.
 *
 * <p>A {@code Component} is the top-level building block of the composable
 * architecture. It captures everything needed to embed a feature in a parent:
 * <ul>
 *   <li><b>{@link #initialState()}</b> — a factory producing a fresh child state.</li>
 *   <li><b>{@link #stateType()}</b> — the concrete class of the child state
 *       (useful for reflection-based tooling or serialization).</li>
 *   <li><b>{@link #reducer()}</b> — the pure reducer that evolves the child state.</li>
 * </ul>
 *
 * <p>A child component is lifted into a parent via
 * {@link Reducer#pullback(Component, java.util.function.Function, java.util.function.BiFunction, java.util.function.Function)},
 * which adapts its reducer to the parent state/action space.
 *
 * <p><b>Thread-safety:</b> {@code Component} instances are immutable after
 * construction, making them safe to share and reuse across threads and stores.
 *
 * @param <S> the state type owned by this component
 * @param <A> the action type handled by this component
 * @author Java Starter Kit
 * @version 1.0.0
 */
public final class Component<S extends State, A extends Action> {

    private final Supplier<S> initialState;
    private final Class<S> stateType;
    private final Reducer<S, A> reducer;

    private Component(Supplier<S> initialState, Class<S> stateType, Reducer<S, A> reducer) {
        this.initialState = Objects.requireNonNull(initialState, "initialState must not be null");
        this.stateType = Objects.requireNonNull(stateType, "stateType must not be null");
        this.reducer = Objects.requireNonNull(reducer, "reducer must not be null");
    }

    /**
     * Creates a new component from its initial-state factory, state type, and reducer.
     *
     * @param initialState the factory producing a fresh child state
     * @param stateType    the concrete child-state class
     * @param reducer      the pure reducer for this feature
     * @param <S>          the child state type
     * @param <A>          the child action type
     * @return a new immutable component
     */
    public static <S extends State, A extends Action> Component<S, A> of(
            Supplier<S> initialState, Class<S> stateType, Reducer<S, A> reducer) {
        return new Component<>(initialState, stateType, reducer);
    }

    /**
     * @return a factory that produces a fresh initial child state
     */
    public Supplier<S> initialState() {
        return initialState;
    }

    /**
     * @return the concrete child-state class
     */
    public Class<S> stateType() {
        return stateType;
    }

    /**
     * @return the pure reducer that evolves the child state
     */
    public Reducer<S, A> reducer() {
        return reducer;
    }
}