package com.javastarterkit.patterns.commandqueryresponsibilitysegregation.infrastructure;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Per-aggregate lock registry that provides pessimistic concurrency control
 * for the write side.
 *
 * <h3>Design</h3>
 * <p>In CQRS, multiple command handlers may concurrently target the same
 * aggregate (e.g., two simultaneous withdrawals from the same account). Without
 * serialization, the last write wins and business invariants can be violated
 * (e.g., double-spending). This component provides a {@link Lock} per aggregate
 * ID, ensuring that the entire read-mutate-write cycle for a single aggregate
 * is serialized:</p>
 * <pre>{@code
 * lock.lockFor("acc-001").lock();
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
 *
 * <h3>Production Consideration</h3>
 * <p>For long-running applications, lock entries accumulate in memory. In
 * production, this would be paired with an eviction strategy (e.g., remove
 * locks for aggregates not accessed in the last N minutes using a
 * time-based cleanup, or use {@link java.util.WeakHashMap} semantics).
 * For this LLD demonstration, locks are never removed — acceptable for
 * bounded aggregate sets.
 */
public final class PerAggregateLock {

    /**
     * Maps each aggregate ID to its dedicated {@link ReentrantLock}.
     * Using a {@code ConcurrentMap} ensures that lock lookup and creation
     * are atomic — two threads calling {@code lockFor("acc-001")}
     * simultaneously will receive the same lock instance.
     */
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
