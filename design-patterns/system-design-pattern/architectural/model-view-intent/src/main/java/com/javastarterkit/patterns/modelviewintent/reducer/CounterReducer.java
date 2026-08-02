package com.javastarterkit.patterns.modelviewintent.reducer;

import com.javastarterkit.patterns.modelviewintent.core.Reducer;
import com.javastarterkit.patterns.modelviewintent.intent.CounterIntent;
import com.javastarterkit.patterns.modelviewintent.intent.Decrement;
import com.javastarterkit.patterns.modelviewintent.intent.Increment;
import com.javastarterkit.patterns.modelviewintent.intent.Reset;
import com.javastarterkit.patterns.modelviewintent.state.CounterState;

/**
 * Pure reducer for the counter.
 *
 * <p>Given the current state and an intent, returns an entirely <b>new</b>
 * state — the original is never mutated. Uses pattern matching in switch
 * statements for exhaustive handling of sealed intents.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public final class CounterReducer implements Reducer<CounterState, CounterIntent> {

    @Override
    public CounterState reduce(CounterState state, CounterIntent intent) {
        return switch (intent) {
            case Increment ignored -> state.copyWith(state.count() + 1);
            case Decrement ignored -> state.copyWith(state.count() - 1);
            case Reset ignored -> state.copyWith(0);
        };
    }
}