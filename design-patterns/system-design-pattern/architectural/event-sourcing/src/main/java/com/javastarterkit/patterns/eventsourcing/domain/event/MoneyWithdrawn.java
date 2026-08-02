package com.javastarterkit.patterns.eventsourcing.domain.event;

import java.time.Instant;

/**
 * Domain event indicating that money was withdrawn from an account.
 *
 * @param aggregateId the unique account identifier
 * @param amount      the positive amount withdrawn
 * @param timestamp   when the withdrawal occurred
 * @param version     the event version
 */
public record MoneyWithdrawn(
        String aggregateId,
        int amount,
        Instant timestamp,
        long version) implements DomainEvent {
}