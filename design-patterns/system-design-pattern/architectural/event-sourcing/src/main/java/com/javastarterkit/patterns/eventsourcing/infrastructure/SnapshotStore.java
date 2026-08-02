package com.javastarterkit.patterns.eventsourcing.infrastructure;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Thread-safe in-memory store for {@link Snapshot}s.
 *
 * <p>Snapshots are keyed by aggregate ID. Each aggregate has at most one
 * snapshot — the most recent one. A {@link ConcurrentHashMap} provides
 * thread-safe {@code put} and {@code get} operations without external locking.
 */
public final class SnapshotStore {

    /** Maps each aggregate ID to its most recent snapshot. */
    private final ConcurrentMap<String, Snapshot> snapshots = new ConcurrentHashMap<>();

    /**
     * Stores a snapshot for the given aggregate ID, replacing any previous one.
     *
     * @param aggregateId the aggregate ID
     * @param snapshot    the snapshot to store
     */
    public void save(String aggregateId, Snapshot snapshot) {
        snapshots.put(aggregateId, snapshot);
    }

    /**
     * Loads the most recent snapshot for the given aggregate, if any.
     *
     * @param aggregateId the aggregate ID
     * @return an {@link Optional} containing the snapshot, or empty
     */
    public Optional<Snapshot> load(String aggregateId) {
        return Optional.ofNullable(snapshots.get(aggregateId));
    }
}