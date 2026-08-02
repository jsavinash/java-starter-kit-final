package com.javastarterkit.patterns.eventsourcing.infrastructure;

import com.javastarterkit.patterns.eventsourcing.domain.event.DomainEvent;
import com.javastarterkit.patterns.eventsourcing.exception.OptimisticLockException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Thread-safe, in-memory implementation of {@link EventStore}.
 *
 * <h3>Concurrency Strategy</h3>
 * <ul>
 *   <li>Each aggregate's event stream is stored as a {@code List} value in a
 *       {@link ConcurrentHashMap}. Concurrent appends to <i>different</i>
 *       aggregates never block each other.</li>
 *   <li>Concurrent appends to the <i>same</i> aggregate are serialized by the
 *       {@link PerAggregateLock} in {@link
 *       com.javastarterkit.patterns.eventsourcing.application.service.AccountService},
 *       which acquires a per-aggregate {@link java.util.concurrent.locks.ReentrantLock}
 *       around the entire load-mutate-save cycle.</li>
 *   <li>Secondary protection is provided by {@code ConcurrentMap.compute()},
 *       which atomically read-modify-writes the stream and detects version
 *       conflicts via optimistic locking ({@link OptimisticLockException}).</li>
 *   <li>Reads (event streaming for replay) use defensive copies, so they
 *       never observe a partially-constructed list.</li>
 * </ul>
 */
public final class InMemoryEventStore implements EventStore {

    /** Maps each aggregate ID to its ordered list of domain events. */
    private final ConcurrentMap<String, List<DomainEvent>> streams = new ConcurrentHashMap<>();

    @Override
    public void append(String aggregateId, List<DomainEvent> events, long expectedVersion) {
        if (events == null || events.isEmpty()) {
            throw new IllegalArgumentException("Cannot append an empty event list");
        }

        // compute() holds a per-bin lock, making the read-modify-write
        // atomic for a single key. Combined with PerAggregateLock, this
        // provides double protection against lost updates.
        streams.compute(aggregateId, (key, existing) -> {
            long latestVersion = existing == null || existing.isEmpty()
                    ? 0
                    : existing.get(existing.size() - 1).version();

            if (latestVersion != expectedVersion) {
                throw new OptimisticLockException(
                        "Version conflict for aggregate '" + aggregateId
                                + "': expected " + expectedVersion
                                + ", found " + latestVersion);
            }

            var newList = new ArrayList<DomainEvent>();
            if (existing != null) {
                newList.addAll(existing);
            }
            newList.addAll(events);
            return newList;
        });
    }

    @Override
    public List<DomainEvent> load(String aggregateId) {
        var events = streams.get(aggregateId);
        return events != null
                ? Collections.unmodifiableList(new ArrayList<>(events))
                : Collections.emptyList();
    }

    @Override
    public List<DomainEvent> loadFromVersion(String aggregateId, long fromVersion) {
        var events = streams.get(aggregateId);
        if (events == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(
                events.stream()
                        .filter(e -> e.version() > fromVersion)
                        .toList());
    }

    @Override
    public long latestVersion(String aggregateId) {
        var events = streams.get(aggregateId);
        if (events == null || events.isEmpty()) {
            return 0;
        }
        return events.get(events.size() - 1).version();
    }
}