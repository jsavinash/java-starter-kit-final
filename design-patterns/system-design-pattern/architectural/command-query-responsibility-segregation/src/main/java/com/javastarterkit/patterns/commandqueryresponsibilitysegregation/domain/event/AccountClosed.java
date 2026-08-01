package com.javastarterkit.patterns.commandqueryresponsibilitysegregation.domain.event;

/**
 * Event emitted when an account is closed.
 *
 * @param aggregateId the ID of the account that was closed
 */
public record AccountClosed(String aggregateId) implements DomainEvent {
}
