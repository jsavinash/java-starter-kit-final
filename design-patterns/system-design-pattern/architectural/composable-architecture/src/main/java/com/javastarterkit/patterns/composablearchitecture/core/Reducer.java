package com.javastarterkit.patterns.composablearchitecture.core;

import com.javastarterkit.patterns.composablearchitecture.core.component.Component;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A pure function that maps {@code (State, Action) -> State}.
 *
 * <p>A {@code Reducer} is the heart of the composable architecture: given the
 * current immutable state and an incoming action, it computes and returns the
 * next immutable state. Reducers are strictly <b>pure</b> — they have no side
 * effects, perform no I/O, and never mutate the input state. This purity makes
 * them trivially unit-testable, deterministic, and safe for concurrent
 * execution.
 *
 * <p>Reducers support two composition operators that are the foundation of
 * composability:
 * <ul>
 *   <li><b>{@link #combine(Reducer, Reducer)}</b> — chains two reducers over
 *       the <em>same</em> state/action space. The second reducer sees the
 *       state produced by the first. Useful for applying cross-cutting
 *       concerns (validation, audit logging) to a single piece of state.</li>
 *   <li><b>{@link #pullback(Component, Function, BiFunction, Function)}</b> —
 *       adapts a <em>child</em> reducer living in a child state/action space
 *       to a <em>parent</em> state/action space. This is the key operator that
 *       lets independent features be composed into larger features without
 *       coupling.</li>
 * </ul>
 *
 * <p><b>Thread-safety:</b> Reducers are stateless pure functions (typically
 * singleton enums). Because they hold no mutable state, invoking the same
 * reducer concurrently from multiple threads is always safe.
 *
 * @param <S> the state type this reducer operates on
 * @param <A> the action type this reducer handles
 * @author Java Starter Kit
 * @version 1.0.0
 */
@FunctionalInterface
public interface Reducer<S extends State, A extends Action> {

    /**
     * Computes the next state from the current state and an action.
     *
     * @param state  the current immutable state (never mutated)
     * @param action the incoming action
     * @return the new immutable state
     */
    S reduce(S state, A action);

    /**
     * Combines two reducers over the same state/action space. Each reducer is
     * applied in sequence; the second sees the state produced by the first.
     *
     * <p>The result is itself a pure reducer, so chains can be composed
     * further. Order matters: {@code combine(first, second)} applies
     * {@code first} then {@code second}.
     *
     * @param first  the reducer applied first
     * @param second the reducer applied second to the output of {@code first}
     * @param <S>    the shared state type
     * @param <A>    the shared action type
     * @return a combined reducer
     */
    static <S extends State, A extends Action> Reducer<S, A> combine(
            Reducer<S, A> first, Reducer<S, A> second) {
        Objects.requireNonNull(first, "first reducer must not be null");
        Objects.requireNonNull(second, "second reducer must not be null");
        return (state, action) -> {
            S afterFirst = first.reduce(state, action);
            return second.reduce(afterFirst, action);
        };
    }

    /**
     * Adapts a child reducer to a parent state/action space by lifting it
     * through a {@link Component}. This is the central composition primitive.
     *
     * <p>For a given parent action, the provided {@code mapAction} extracts the
     * corresponding child action (returning {@code null} if the action is not
     * relevant to this child). The {@code extract} function pulls the child's
     * state slice out of the parent, the child reducer computes the new child
     * state, and {@code inject} writes it back into a fresh copy of the parent.
     *
     * <p>If {@code mapAction} returns {@code null}, the action does not concern
     * this child and the original parent state is returned unchanged — allowing
     * multiple pulled-back reducers to be {@linkplain #combine combined} safely.
     *
     * @param component the child component containing the child reducer
     * @param extract   pulls the child state slice out of the parent
     * @param inject    writes the new child state back into a new parent
     * @param mapAction maps a parent action to a child action (or {@code null})
     * @param <PS>      the parent state type
     * @param <PA>      the parent action type
     * @param <CS>      the child state type
     * @param <CA>      the child action type
     * @return a reducer over the parent state/action space
     */
    static <PS extends State, PA extends Action, CS extends State, CA extends Action>
    Reducer<PS, PA> pullback(
            Component<CS, CA> component,
            Function<PS, CS> extract,
            BiFunction<PS, CS, PS> inject,
            Function<PA, CA> mapAction) {
        Objects.requireNonNull(component, "component must not be null");
        Objects.requireNonNull(extract, "extract must not be null");
        Objects.requireNonNull(inject, "inject must not be null");
        Objects.requireNonNull(mapAction, "mapAction must not be null");

        return (parentState, parentAction) -> {
            CA childAction = mapAction.apply(parentAction);
            if (childAction == null) {
                // Action not relevant to this child: leave parent untouched.
                return parentState;
            }
            CS currentChild = extract.apply(parentState);
            CS nextChild = component.reducer().reduce(currentChild, childAction);
            return inject.apply(parentState, nextChild);
        };
    }
}