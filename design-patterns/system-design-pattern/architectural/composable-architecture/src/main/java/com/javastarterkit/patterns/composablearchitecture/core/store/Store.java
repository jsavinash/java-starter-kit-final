package com.javastarterkit.patterns.composablearchitecture.core.store;

import com.javastarterkit.patterns.composablearchitecture.core.Action;
import com.javastarterkit.patterns.composablearchitecture.core.Reducer;
import com.javastarterkit.patterns.composablearchitecture.core.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

/**
 * Thread-safe holder of the current state that applies a {@link Reducer} when
 * actions are dispatched and notifies subscribers of state transitions.
 *
 * <p>A {@code Store} is the runtime engine of the composable architecture:
 * <ul>
 *   <li><b>{@link #dispatch(Action)}</b> — feeds an action through the reducer
 *       to produce the next immutable state, then publishes it to observers.</li>
 *   <li><b>{@link #state()}</b> — returns the current immutable state snapshot.</li>
 *   <li><b>{@link #subscribe(Consumer)}</b> — registers a listener invoked on
 *       every state change with the new state.</li>
 * </ul>
 *
 * <h2>Concurrency Strategy</h2>
 * <p>The store is built for <b>multi-producer, multi-consumer</b> usage:
 * <ul>
 *   <li><b>Immutable state</b> — every state is a value object; there is no
 *       in-place mutation, so any thread can safely read a snapshot at any
 *       time without locks.</li>
 *   <li><b>{@link ReentrantLock}</b> — a single reentrant lock serializes the
 *       read-modify-write cycle of {@code dispatch}. Because reducers are pure
 *       and fast, hold times are tiny and contention is minimal. The lock is
 *       reentrant so a reducer or listener that internally dispatches another
 *       action will not deadlock.</li>
 *   <li><b>{@link CopyOnWriteArrayList}</b> — subscriber lists are copied on
 *       modification, so readers (notification delivery) never block and
 *       iterate over a stable snapshot.</li>
 *   <li><b>{@link AtomicLong}</b> — a monotonically increasing revision counter
 *       tracks every state change, useful for change-detection and caching.</li>
 * </ul>
 *
 * <p>States are delivered to subscribers by {@code publishState} while holding
 * the lock. To keep the design simple and deterministic, listeners are
 * notified synchronously; for high-throughput or blocking listeners, compose
 * the store with an asynchronous dispatcher at a higher layer.
 *
 * @param <S> the state type held by this store
 * @param <A> the action type this store accepts
 * @author Java Starter Kit
 * @version 1.0.0
 */
public final class Store<S extends State, A extends Action> {

    private static final Logger log = LoggerFactory.getLogger(Store.class);

    /** Serializes the read-modify-write cycle for {@link #dispatch}. */
    private final ReentrantLock lock = new ReentrantLock();

    /** Holds all state-change subscribers. Copy-on-write for lock-free reads. */
    private final List<Consumer<S>> subscribers = new CopyOnWriteArrayList<>();

    /** Monotonically increasing revision counter for state changes. */
    private final AtomicLong revision = new AtomicLong(0L);

    /** The pure reducer that evolves the state. */
    private final Reducer<S, A> reducer;

    /** The current immutable state. Guarded by {@link #lock}. */
    private volatile S state;

    /** True once {@link #close()} has been invoked. Guarded by {@link #lock}. */
    private boolean closed;

    /**
     * Creates a store with an initial state and a reducer.
     *
     * @param initialState the starting immutable state
     * @param reducer      the pure reducer
     */
    public Store(S initialState, Reducer<S, A> reducer) {
        this.state = Objects.requireNonNull(initialState, "initialState must not be null");
        this.reducer = Objects.requireNonNull(reducer, "reducer must not be null");
    }

    /**
     * Returns the current immutable state snapshot.
     *
     * @return the current state
     */
    public S state() {
        return state;
    }

    /**
     * Returns the current state revision. The revision increments by one on
     * every successful {@link #dispatch}. Useful for change-detection and
     * caching strategies.
     *
     * @return the current revision
     */
    public long revision() {
        return revision.get();
    }

    /**
     * Feeds an action through the reducer, producing the next immutable state,
     * and notifies all subscribers. If the store is closed, the action is
     * ignored with a warning.
     *
     * @param action the action to dispatch (must not be null)
     * @throws IllegalStateException if the reducer returns a {@code null} state
     */
    public void dispatch(A action) {
        Objects.requireNonNull(action, "action must not be null");

        lock.lock();
        try {
            if (closed) {
                log.warn("Dispatch ignored: store is closed — action={}", action);
                return;
            }
            S nextState = reducer.reduce(state, action);
            if (nextState == null) {
                throw new IllegalStateException(
                        "Reducer returned null state for action=" + action);
            }
            // Use value equality: if the reducer produced an equal state (e.g., a
            // no-op action), there is no observable change, so skip notification.
            if (!nextState.equals(state)) {
                state = nextState;
                long newRevision = revision.incrementAndGet();
                publishState(nextState, newRevision);
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Registers a subscriber that will be invoked with the new state on every
     * state transition.
     *
     * @param subscriber the state-change listener (must not be null)
     */
    public void subscribe(Consumer<S> subscriber) {
        subscribers.add(Objects.requireNonNull(subscriber, "subscriber must not be null"));
    }

    /**
     * Removes a previously registered subscriber.
     *
     * @param subscriber the listener to remove
     */
    public void unsubscribe(Consumer<S> subscriber) {
        subscribers.remove(subscriber);
    }

    /**
     * Closes this store, preventing further dispatches. Subscribers are
     * retained (their notifications simply stop). An idempotent operation.
     *
     * @throws InterruptedException if the current thread is interrupted while
     *                              waiting to acquire the lock
     */
    public void close() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            closed = true;
            log.info("Store closed at revision={}", revision.get());
        } finally {
            lock.unlock();
        }
    }

    /**
     * Notifies every subscriber with the new state and revision. Invoked while
     * holding {@link #lock}; {@link CopyOnWriteArrayList} guarantees a stable
     * snapshot for iteration without blocking concurrent modifications.
     */
    private void publishState(S newState, long newRevision) {
        log.debug("State transition revision={} -> {}", newRevision, newState);
        for (Consumer<S> subscriber : subscribers) {
            try {
                subscriber.accept(newState);
            } catch (RuntimeException ex) {
                log.error("Subscriber threw while handling state={}", newState, ex);
            }
        }
    }
}