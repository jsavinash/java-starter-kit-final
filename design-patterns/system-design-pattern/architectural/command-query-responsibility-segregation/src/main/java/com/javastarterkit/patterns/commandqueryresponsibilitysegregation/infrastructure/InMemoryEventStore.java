package com.javastarterkit.patterns.commandqueryresponsibilitysegregation.infrastructure;

import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.domain.event.DomainEvent;

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
 *       {@link PerAggregateLock} in {@link AccountRepository}, which acquires
 *       a per-aggregate {@link java.util.concurrent.locks.ReentrantLock}
 *       around the entire load-mutate-save cycle. This is the primary
 *       concurrency control; the EventStore adds a secondary thread-safety
 *       guarantee via atomic {@code compute} operations.</li>
 *   <li>Reads (event streaming for replay) use defensive copies, so they
 *       never observe a partially-constructed list.</li>
 * </ul>
 */
public final class InMemoryEventStore implements EventStore {

    /** Maps each aggregate ID to its ordered list of domain events. */
    private final ConcurrentMap<String, List<DomainEvent>> streams = new ConcurrentHashMap<>();

    @Override
    public void append(String aggregateId, List<DomainEvent> events) {
        if (events.isEmpty()) {
            return;
        }
        // compute() holds a per-bin lock, making the read-modify-write
        // atomic for a single key. Combined with PerAggregateLock, this
        // provides double protection against lost updates.
        streams.compute(aggregateId, (key, existing) -> {
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
}
