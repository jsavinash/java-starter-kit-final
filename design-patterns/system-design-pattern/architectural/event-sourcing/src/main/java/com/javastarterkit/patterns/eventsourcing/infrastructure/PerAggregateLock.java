package com.javastarterkit.patterns.eventsourcing.infrastructure;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Per-aggregate lock registry providing pessimistic concurrency control
 * for the write side.
 *
 * <h3>Design</h3>
 * <p>In Event Sourcing, multiple command handlers may concurrently target the
 * same aggregate (e.g., two simultaneous withdrawals from the same account).
 * Without serialization, the last write wins and business invariants can be
 * violated (e.g., double-spending). This component provides a {@link Lock}
 * per aggregate ID, ensuring that the entire read-mutate-write cycle for a
 * single aggregate is serialized:</p>
 * <pre>{@code
 * var lock = locks.lockFor("acc-001");
 * lock.lock();
 * try {
 *     // load, mutate, save — no other thread can interleave
 * } finally {
 *     lock.unlock();
 * }
 * }</pre>
 *
 * <h3>Concurrency Strategy</h3>
 * <ul>
 *   <li>The lock map is a {@link ConcurrentHashMap}, making lock acquisition
 *       lock-free for aggregates that have existing locks.</li>
 *   <li>{@code computeIfAbsent} atomically creates a new {@link ReentrantLock}
 *       for a previously unseen aggregate ID.</li>
 *   <li>Different aggregates are completely independent — operations on
 *       {@code acc-001} never block operations on {@code acc-002}.</li>
 * </ul>
 */
public final class PerAggregateLock {

    /** Maps each aggregate ID to its dedicated {@link ReentrantLock}. */
    private final ConcurrentMap<String, Lock> locks = new ConcurrentHashMap<>();

    /**
     * Returns the lock for the given aggregate ID, creating one if it does
     * not yet exist.
     *
     * @param aggregateId the aggregate identifier
     * @return the lock dedicated to this aggregate
     */
    public Lock lockFor(String aggregateId) {
        return locks.computeIfAbsent(aggregateId, id -> new ReentrantLock());
    }

    /**
     * Removes the lock entry for the given aggregate ID, freeing memory.
     *
     * <p>This should only be called when no thread holds the lock and the
     * aggregate is no longer expected to receive commands.
     *
     * @param aggregateId the aggregate identifier
     */
    public void release(String aggregateId) {
        locks.remove(aggregateId);
    }
}