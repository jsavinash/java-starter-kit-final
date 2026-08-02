package com.javastarterkit.patterns.modelviewintent.view;

import com.javastarterkit.patterns.modelviewintent.core.ViewObserver;
import com.javastarterkit.patterns.modelviewintent.state.CounterState;

/**
 * Counter view: renders state as text.
 *
 * <p>This is a <b>View</b> in the MVI pattern. It renders the current state
 * and is notified when the state changes. The View never mutates state
 * directly — it only dispatches intents.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public final class CounterView implements ViewObserver<CounterState> {

    @Override
    public void onStateChanged(CounterState state) {
        render(state);
    }

    /**
     * Renders the counter state to the console.
     *
     * @param state the counter state
     */
    public void render(CounterState state) {
        System.out.println("  Counter view: count = " + state.count());
    }
}