package com.javastarterkit.patterns.flux.core;

import com.javastarterkit.patterns.flux.actions.Action;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * Abstract base store that holds state and manages subscribers.
 *
 * <p>Stores receive actions via {@link #onAction(Action)}, update their immutable state,
 * and notify all registered subscribers of state changes. This implementation uses:
 * <ul>
 *   <li>{@link AtomicReference} for lock-free atomic state swaps</li>
 *   <li>{@link CopyOnWriteArrayList} for thread-safe subscriber management</li>
 * </ul>
 *
 * @param <S> the type of state held by this store
 */
public abstract class Store<S> {

    private final AtomicReference<S> state;
    private final List<Consumer<S>> subscribers;

    /**
     * Creates a new store with the given initial state.
     *
     * @param initialState the initial state; must not be null
     * @throws NullPointerException if initialState is null
     */
    protected Store(final S initialState) {
        if (initialState == null) {
            throw new NullPointerException("initialState must not be null");
        }
        this.state = new AtomicReference<>(initialState);
        this.subscribers = new CopyOnWriteArrayList<>();
    }

    /**
     * Returns the current state.
     *
     * <p>The returned state is an immutable snapshot. Thread-safe.
     *
     * @return the current state
     */
    public final S getState() {
        return state.get();
    }

    /**
     * Updates the state atomically and notifies all subscribers.
     *
     * <p>This method is thread-safe. The state swap is atomic, ensuring that
     * concurrent readers see either the old or new state, never a partial update.
     *
     * <p>Subscribers are notified synchronously on the calling thread after the state swap.
     *
     * @param newState the new state; must not be null
     * @throws NullPointerException if newState is null
     */
    protected final void setState(final S newState) {
        if (newState == null) {
            throw new NullPointerException("newState must not be null");
        }

        final S previous = state.getAndSet(newState);

        // Only notify if state actually changed (optional optimization)
        if (!previous.equals(newState)) {
            notifySubscribers(newState);
        }
    }

    /**
     * Registers a subscriber to receive state updates.
     *
     * <p>Subscribers are notified synchronously after each state change. Registration is thread-safe.
     *
     * @param subscriber the subscriber to register; must not be null
     * @throws NullPointerException if subscriber is null
     */
    public final void subscribe(final Consumer<S> subscriber) {
        if (subscriber == null) {
            throw new NullPointerException("subscriber must not be null");
        }
        subscribers.add(subscriber);
    }

    /**
     * Unregisters a subscriber.
     *
     * <p>If the subscriber is not registered, this method is a no-op.
     *
     * @param subscriber the subscriber to unregister; must not be null
     * @throws NullPointerException if subscriber is null
     */
    public final void unsubscribe(final Consumer<S> subscriber) {
        if (subscriber == null) {
            throw new NullPointerException("subscriber must not be null");
        }
        subscribers.remove(subscriber);
    }

    /**
     * Handles an incoming action. Subclasses override this to implement state transitions.
     *
     * <p>The default implementation is a no-op.
     *
     * @param action the action to handle; must not be null
     */
    public void onAction(final Action action) {
        // Default: no-op
    }

    /**
     * Notifies all subscribers of a state change.
     *
     * <p>This method iterates over a snapshot of subscribers, ensuring that concurrent
     * registration/unregistration does not affect the current notification cycle.
     *
     * @param newState the new state to pass to subscribers
     */
    private void notifySubscribers(final S newState) {
        // CopyOnWriteArrayList provides a safe snapshot iterator
        for (final Consumer<S> subscriber : subscribers) {
            subscriber.accept(newState);
        }
    }
}