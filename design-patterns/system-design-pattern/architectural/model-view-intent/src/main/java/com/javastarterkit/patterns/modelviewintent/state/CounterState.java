package com.javastarterkit.patterns.modelviewintent.state;

/**
 * Immutable counter state.
 *
 * <p>This is the <b>Model (State)</b> in the MVI pattern. It is an immutable
 * snapshot of the UI state. The View always renders from this single source
 * of truth.
 *
 * @param count the current counter value
 * @author Java Starter Kit
 * @version 1.0.0
 */
public record CounterState(int count) {

    /**
     * Returns a new state with the given count.
     *
     * @param count the new count
     * @return a new CounterState
     */
    public CounterState copyWith(int count) {
        return new CounterState(count);
    }

    @Override
    public String toString() {
        return "CounterState{count=" + count + "}";
    }
}