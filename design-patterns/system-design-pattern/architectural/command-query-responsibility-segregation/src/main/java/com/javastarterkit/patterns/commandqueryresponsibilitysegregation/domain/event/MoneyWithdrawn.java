package com.javastarterkit.patterns.commandqueryresponsibilitysegregation.domain.event;

/**
 * Event emitted when money is withdrawn from an account.
 *
 * @param aggregateId the ID of the account
 * @param amount      the positive amount withdrawn
 */
public record MoneyWithdrawn(String aggregateId, int amount) implements DomainEvent {
}
