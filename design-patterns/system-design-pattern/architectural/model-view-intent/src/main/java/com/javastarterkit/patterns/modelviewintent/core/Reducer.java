package com.javastarterkit.patterns.modelviewintent.core;

/**
 * Functional interface for a pure reducer.
 *
 * <p>A reducer is a pure function that takes the current state and an intent,
 * and produces a <b>new</b> state. The original state is never mutated.
 *
 * @param <S> the state type
 * @param <I> the intent type
 * @author Java Starter Kit
 * @version 1.0.0
 */
@FunctionalInterface
public interface Reducer<S, I> {

    /**
     * Reduces the current state with the given intent to produce a new state.
     *
     * @param state  the current state
     * @param intent the intent to apply
     * @return the new state
     */
    S reduce(S state, I intent);
}