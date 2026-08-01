package com.javastarterkit.patterns.composablearchitecture;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.javastarterkit.patterns.composablearchitecture.ComposableArchitecture.Component;
import com.javastarterkit.patterns.composablearchitecture.ComposableArchitecture.CounterAction;
import com.javastarterkit.patterns.composablearchitecture.ComposableArchitecture.CounterReducer;
import com.javastarterkit.patterns.composablearchitecture.ComposableArchitecture.CounterState;
import com.javastarterkit.patterns.composablearchitecture.ComposableArchitecture.FormAction;
import com.javastarterkit.patterns.composablearchitecture.ComposableArchitecture.FormState;
import com.javastarterkit.patterns.composablearchitecture.ComposableArchitecture.Reducer;
import com.javastarterkit.patterns.composablearchitecture.ComposableArchitecture.Store;
import com.javastarterkit.patterns.composablearchitecture.ComposableArchitecture.TextAction;
import com.javastarterkit.patterns.composablearchitecture.ComposableArchitecture.TextReducer;
import com.javastarterkit.patterns.composablearchitecture.ComposableArchitecture.TextState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests verifying the composable architecture: individual feature reducers
 * work in isolation, and the composed form reducer routes actions correctly.
 */
class ComposableArchitectureTest {

    @Test
    @DisplayName("counter reducer increments and decrements")
    void counterReducerWorks() {
        CounterState state = new CounterState();

        state = CounterReducer.INSTANCE.reduce(state, new CounterAction.increment());
        assertEquals(1, state.count());

        state = CounterReducer.INSTANCE.reduce(state, new CounterAction.increment());
        assertEquals(2, state.count());

        state = CounterReducer.INSTANCE.reduce(state, new CounterAction.decrement());
        assertEquals(1, state.count());
    }

    @Test
    @DisplayName("text reducer changes and clears value")
    void textReducerWorks() {
        TextState state = new TextState();

        state = TextReducer.INSTANCE.reduce(state, new TextAction.change("Hello"));
        assertEquals("Hello", state.value());

        state = TextReducer.INSTANCE.reduce(state, new TextAction.clear());
        assertEquals("", state.value());
    }

    @Test
    @DisplayName("composed form reducer routes counter actions to counter slice")
    void composedReducerRoutesCounterActions() {
        Reducer<FormState, FormAction> formReducer = buildFormReducer();
        Store<FormState, FormAction> store = new Store<>(new FormState(), formReducer);

        store.dispatch(new FormAction.Counter(new CounterAction.increment()));
        store.dispatch(new FormAction.Counter(new CounterAction.increment()));

        assertEquals(2, store.state().counter().count());
        assertEquals("", store.state().text().value());
    }

    @Test
    @DisplayName("composed form reducer routes text actions to text slice")
    void composedReducerRoutesTextActions() {
        Reducer<FormState, FormAction> formReducer = buildFormReducer();
        Store<FormState, FormAction> store = new Store<>(new FormState(), formReducer);

        store.dispatch(new FormAction.Text(new TextAction.change("World")));

        assertEquals(0, store.state().counter().count());
        assertEquals("World", store.state().text().value());
    }

    @Test
    @DisplayName("composed form reducer keeps unrelated slices unchanged")
    void composedReducerKeepsUnrelatedSlicesUnchanged() {
        Reducer<FormState, FormAction> formReducer = buildFormReducer();
        Store<FormState, FormAction> store = new Store<>(new FormState(), formReducer);

        store.dispatch(new FormAction.Counter(new CounterAction.increment()));
        store.dispatch(new FormAction.Text(new TextAction.change("Hi")));
        store.dispatch(new FormAction.Counter(new CounterAction.decrement()));

        assertEquals(0, store.state().counter().count());
        assertEquals("Hi", store.state().text().value());
    }

    @Test
    @DisplayName("demonstrate runs without throwing")
    void demonstrateRunsSuccessfully() {
        // Smoke test: the public demonstration should complete without errors.
        ComposableArchitecture.demonstrate();
    }

    /** Builds the composed form reducer used across tests. */
    private Reducer<FormState, FormAction> buildFormReducer() {
        Component<CounterState, CounterAction> counterComponent =
                Component.of(CounterState::new, CounterState.class, CounterReducer.INSTANCE);
        Component<TextState, TextAction> textComponent =
                Component.of(TextState::new, TextState.class, TextReducer.INSTANCE);

        return Reducer.combine(
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
    }
}