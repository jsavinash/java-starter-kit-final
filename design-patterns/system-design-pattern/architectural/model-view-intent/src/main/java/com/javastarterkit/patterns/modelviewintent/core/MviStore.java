package com.javastarterkit.patterns.modelviewintent.core;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The MVI store is the single source of truth.
 *
 * <p>It holds the current immutable state, reduces dispatched intents with
 * the given reducer, and notifies registered view observers on every state
 * change. The data flow is strictly unidirectional:
 * <pre>
 *   View → Intent → Reducer → State → View
 * </pre>
 *
 * <p><b>Thread-Safety Strategy:</b>
 * <ul>
 *   <li>Uses {@link AtomicReference} for lock-free, atomic state swaps.</li>
 *   <li>Uses {@link CopyOnWriteArrayList} for thread-safe observer registration
 *       and notification.</li>
 *   <li>State objects are expected to be immutable.</li>
 * </ul>
 *
 * @param <S> the state type
 * @param <I> the intent type
 * @author Java Starter Kit
 * @version 1.0.0
 */
public final class MviStore<S, I> {

    private final AtomicReference<S> state;
    private final Reducer<S, I> reducer;
    private final CopyOnWriteArrayList<ViewObserver<S>> observers = new CopyOnWriteArrayList<>();

    /**
     * Constructs the store with an initial state and a reducer.
     *
     * @param initialState the initial state
     * @param reducer      the pure reducer function
     */
    public MviStore(S initialState, Reducer<S, I> reducer) {
        this.state = new AtomicReference<>(Objects.requireNonNull(initialState, "Initial state must not be null"));
        this.reducer = Objects.requireNonNull(reducer, "Reducer must not be null");
    }

    /**
     * Returns the current state.
     *
     * @return the current state
     */
    public S state() {
        return state.get();
    }

    /**
     * Registers a view observer to be notified on state changes.
     *
     * @param observer the observer to register
     */
    public void addObserver(ViewObserver<S> observer) {
        observers.add(Objects.requireNonNull(observer, "Observer must not be null"));
    }

    /**
     * Removes a view observer.
     *
     * @param observer the observer to remove
     */
    public void removeObserver(ViewObserver<S> observer) {
        observers.remove(observer);
    }

    /**
     * Returns an immutable list of registered observers.
     *
     * @return the observers
     */
    public List<ViewObserver<S>> observers() {
        return List.copyOf(observers);
    }

    /**
     * The only way to change state: dispatch an intent.
     *
     * <p>The reducer produces a new state from the current state and the
     * intent. The state is swapped atomically, and all observers are notified.
     *
     * @param intent the intent to dispatch
     */
    public void dispatch(I intent) {
        Objects.requireNonNull(intent, "Intent must not be null");
        S currentState = state.get();
        S newState = reducer.reduce(currentState, intent);
        Objects.requireNonNull(newState, "Reducer must not return null state");
        state.set(newState);
        notifyObservers(newState);
    }

    private void notifyObservers(S newState) {
        for (ViewObserver<S> observer : observers) {
            observer.onStateChanged(newState);
        }
    }
}