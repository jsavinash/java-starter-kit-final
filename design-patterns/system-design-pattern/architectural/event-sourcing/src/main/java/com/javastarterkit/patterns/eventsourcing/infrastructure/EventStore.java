package com.javastarterkit.patterns.eventsourcing.infrastructure;

import com.javastarterkit.patterns.eventsourcing.domain.event.DomainEvent;

import java.util.List;

/**
 * Contract for an append-only event store.
 *
 * <p>Implementations must guarantee that:
 * <ul>
 *   <li>Events are appended atomically to a stream keyed by aggregate ID.</li>
 *   <li>Events are never modified or deleted — only appended.</li>
 *   <li>Append operations detect version conflicts via {@link
 *       com.javastarterkit.patterns.eventsourcing.exception.OptimisticLockException}.</li>
 *   <li>Read operations return defensive copies so callers can freely
 *       traverse the stream without risking external mutation.</li>
 * </ul>
 */
public interface EventStore {

    /**
     * Appends a batch of events to the specified aggregate's stream.
     *
     * <p>The {@code expectedVersion} is the version of the last event the
     * caller has seen. If the store's latest version no longer matches, a
     * concurrent writer has already appended events and the operation must
     * be retried by reloading the aggregate.
     *
     * @param aggregateId    the aggregate ID
     * @param events         the events to append (must be non-empty)
     * @param expectedVersion the expected latest version of the stream
     * @throws com.javastarterkit.patterns.eventsourcing.exception.OptimisticLockException
     *         if the store's latest version does not match {@code expectedVersion}
     */
    void append(String aggregateId, List<DomainEvent> events, long expectedVersion);

    /**
     * Returns the full event stream for an aggregate.
     *
     * @param aggregateId the aggregate ID
     * @return an immutable, ordered list of events (empty if no stream exists)
     */
    List<DomainEvent> load(String aggregateId);

    /**
     * Returns events with a version strictly greater than {@code fromVersion}.
     *
     * @param aggregateId the aggregate ID
     * @param fromVersion the exclusive lower bound
     * @return an immutable, ordered list of subsequent events
     */
    List<DomainEvent> loadFromVersion(String aggregateId, long fromVersion);

    /**
     * Returns the version of the most recent event in the stream for an aggregate.
     *
     * @param aggregateId the aggregate ID
     * @return the latest version, or {@code 0} if the stream does not exist
     */
    long latestVersion(String aggregateId);
}