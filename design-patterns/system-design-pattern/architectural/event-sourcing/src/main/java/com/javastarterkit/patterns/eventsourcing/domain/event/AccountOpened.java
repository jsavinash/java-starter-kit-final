package com.javastarterkit.patterns.eventsourcing.domain.event;

import java.time.Instant;

/**
 * Domain event indicating that a new bank account was opened.
 *
 * <p>This is the first event in an account's stream and establishes the
 * aggregate's identity, owner, and initial balance.
 *
 * @param aggregateId    the unique account identifier
 * @param owner          the account owner's name
 * @param initialBalance the starting balance (non-negative)
 * @param timestamp      when the account was opened
 * @param version        the event version (always 1 for a new stream)
 */
public record AccountOpened(
        String aggregateId,
        String owner,
        int initialBalance,
        Instant timestamp,
        long version) implements DomainEvent {
}