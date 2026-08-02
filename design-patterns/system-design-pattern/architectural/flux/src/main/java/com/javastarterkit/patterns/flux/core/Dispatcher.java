package com.javastarterkit.patterns.flux.core;

import com.javastarterkit.patterns.flux.actions.Action;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Central dispatcher that routes actions to registered stores.
 *
 * <p>The Dispatcher is the single entry point for all actions in a Flux application.
 * It maintains a thread-safe registry of stores and broadcasts each dispatched action
 * to all registered stores.
 *
 * <p>This implementation uses {@link CopyOnWriteArrayList} for the stores registry,
 * providing safe concurrent iteration without explicit locking. This is optimal because:
 * <ul>
 *   <li>Stores are registered once during application startup (write rare)</li>
 *   <li>Dispatch operations iterate over all stores (read frequent)</li>
 *   <li>Iteration over CopyOnWriteArrayList is lock-free and snapshot-based</li>
 * </ul>
 */
public final class Dispatcher {

    private final List<Store<?>> stores;

    /**
     * Creates a new Dispatcher with an empty store registry.
     */
    public Dispatcher() {
        this.stores = new CopyOnWriteArrayList<>();
    }

    /**
     * Registers a store to receive dispatched actions.
     *
     * <p>Registration is thread-safe and can be performed concurrently with dispatch operations.
     * Stores registered after a dispatch begins will not receive that action.
     *
     * @param store the store to register; must not be null
     * @throws NullPointerException if store is null
     */
    public void register(final Store<?> store) {
        if (store == null) {
            throw new NullPointerException("store must not be null");
        }
        stores.add(store);
    }

    /**
     * Dispatches an action to all registered stores.
     *
     * <p>This method is thread-safe and can be called concurrently from multiple threads.
     * Each registered store receives the action exactly once, in the order of registration.
     *
     * @param action the action to dispatch; must not be null
     * @throws NullPointerException if action is null
     */
    public void dispatch(final Action action) {
        if (action == null) {
            throw new NullPointerException("action must not be null");
        }

        // Iterate over snapshot of stores to avoid ConcurrentModificationException
        for (final Store<?> store : stores) {
            store.onAction(action);
        }
    }
}