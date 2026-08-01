package com.javastarterkit.patterns.composablearchitecture.composition;

import com.javastarterkit.patterns.composablearchitecture.core.Reducer;
import com.javastarterkit.patterns.composablearchitecture.core.store.Store;
import com.javastarterkit.patterns.composablearchitecture.ui.actions.OrderAction;
import com.javastarterkit.patterns.composablearchitecture.ui.models.OrderState;
import com.javastarterkit.patterns.composablearchitecture.ui.reducers.OrderReducer;

/**
 * High-level composition entry point for building a fully wired order store.
 *
 * <p>This service encapsulates the "wiring" concern of the composable
 * architecture: it composes the independent feature reducers into a single
 * parent reducer and produces a ready-to-use, thread-safe {@link Store}. Callers
 * interact only with this service (and the store it returns) rather than the
 * low-level composition operators.
 *
 * <p>The service itself is stateless and thread-safe: every call to
 * {@link #createStore()} returns a brand-new, independent store.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public final class OrderComposer {

    private OrderComposer() {
        // Prevent instantiation — this is a pure factory/service.
    }

    /**
     * Creates a new, independent, thread-safe store backed by the fully
     * composed order reducer and an initial empty order state.
     *
     * @return a newly created order store
     */
    public static Store<OrderState, OrderAction> createStore() {
        return new Store<>(new OrderState(), composedReducer());
    }

    /**
     * Creates a new store with a custom initial state.
     *
     * @param initialState the starting order state
     * @return a newly created order store seeded with the given state
     */
    public static Store<OrderState, OrderAction> createStore(OrderState initialState) {
        return new Store<>(initialState, composedReducer());
    }

    /**
     * Returns the stateless, thread-safe composed reducer. This reducer can be
     * shared across many stores/threads without any synchronization.
     *
     * @return the composed order reducer
     */
    public static Reducer<OrderState, OrderAction> composedReducer() {
        return OrderReducer.composed();
    }
}