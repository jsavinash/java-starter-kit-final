package com.javastarterkit.patterns.eventsourcing.domain.event;

import java.time.Instant;

/**
 * Domain event indicating that money was deposited into an account.
 *
 * @param aggregateId the unique account identifier
 * @param amount      the positive amount deposited
 * @param timestamp   when the deposit occurred
 * @param version     the event version
 */
public record MoneyDeposited(
        String aggregateId,
        int amount,
        Instant timestamp,
        long version) implements DomainEvent {
}