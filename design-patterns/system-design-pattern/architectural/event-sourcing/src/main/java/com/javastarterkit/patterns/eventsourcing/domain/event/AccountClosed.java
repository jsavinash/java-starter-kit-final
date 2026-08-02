package com.javastarterkit.patterns.eventsourcing.domain.event;

import java.time.Instant;

/**
 * Domain event indicating that an account was closed.
 *
 * <p>Once closed, no further deposits or withdrawals are permitted on the
 * account. This is the terminal event in an account's stream.
 *
 * @param aggregateId the unique account identifier
 * @param timestamp   when the account was closed
 * @param version     the event version
 */
public record AccountClosed(
        String aggregateId,
        Instant timestamp,
        long version) implements DomainEvent {
}