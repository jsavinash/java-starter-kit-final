package com.javastarterkit.patterns.eventsourcing.domain.event;

import java.time.Instant;

/**
 * Base sealed interface for all domain events in the Event Sourcing system.
 *
 * <p>Each event represents an immutable fact that has already happened in the
 * system. Events are never modified or deleted — they form an append-only
 * ledger that constitutes the single source of truth for aggregate state.
 *
 * <h3>Sealed Hierarchy</h3>
 * <p>The {@code sealed} keyword restricts the permitted subtypes to exactly:
 * <ul>
 *   <li>{@link AccountOpened}</li>
 *   <li>{@link MoneyDeposited}</li>
 *   <li>{@link MoneyWithdrawn}</li>
 *   <li>{@link AccountClosed}</li>
 * </ul>
 * This guarantees exhaustive pattern-matching in {@code switch} expressions
 * at compile time, eliminating the need for a runtime {@code default} branch.
 *
 * <h3>Immutability</h3>
 * <p>All implementations are {@link java.lang.Record}s, which are inherently
 * immutable. Their state is fixed at construction time and cannot be changed
 * afterwards, ensuring a reliable audit trail.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public sealed interface DomainEvent permits AccountOpened, MoneyDeposited, MoneyWithdrawn, AccountClosed {

    /**
     * The aggregate ID this event belongs to.
     *
     * @return the aggregate ID (e.g., "acc-001")
     */
    String aggregateId();

    /**
     * The point in time when the event occurred.
     *
     * @return the event timestamp
     */
    Instant timestamp();

    /**
     * The monotonically increasing version number within the aggregate's stream.
     * Versions start at 1 and increment by 1 for each subsequent event.
     *
     * @return the event version
     */
    long version();
}