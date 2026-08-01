package com.javastarterkit.patterns.commandqueryresponsibilitysegregation.infrastructure;

import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.domain.event.DomainEvent;

import java.util.List;

/**
 * Append-only event store that persists domain event streams per aggregate.
 *
 * <h3>Design</h3>
 * <p>The EventStore is the <b>system of record</b> for the write side.
 * It stores only the events — never the aggregate state itself — enabling
 * full state reconstruction by replay at any time. This is the foundation
 * of Event Sourcing.
 *
 * <p>Each aggregate has its own immutable event stream keyed by aggregate ID.
 * Appends are atomic per aggregate, and the entire stream is immutable once
 * written.
 */
public interface EventStore {

    /**
     * Appends a batch of events to the end of the aggregate's event stream.
     *
     * <p>The append operation is atomic — either all events are persisted or
     * none are. If the aggregate stream does not yet exist, it is created.
     *
     * @param aggregateId the aggregate identifier (must not be {@code null})
     * @param events      the events to append (must not be empty)
     */
    void append(String aggregateId, List<DomainEvent> events);

    /**
     * Loads the complete event stream for the given aggregate.
     *
     * @param aggregateId the aggregate identifier
     * @return an immutable copy of all events for this aggregate,
     *         or an empty list if the aggregate does not exist
     */
    List<DomainEvent> load(String aggregateId);
}
