package com.javastarterkit.patterns.commandqueryresponsibilitysegregation.domain.event;

/**
 * Event emitted when a new account is opened.
 *
 * @param aggregateId   the unique ID assigned to the new account
 * @param owner         the name of the account owner
 * @param initialBalance the starting balance (must be non-negative)
 */
public record AccountOpened(String aggregateId, String owner, int initialBalance)
        implements DomainEvent {
}
