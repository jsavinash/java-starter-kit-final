package com.javastarterkit.patterns.commandqueryresponsibilitysegregation.domain.event;

/**
 * Event emitted when money is deposited into an account.
 *
 * @param aggregateId the ID of the account
 * @param amount      the positive amount deposited
 */
public record MoneyDeposited(String aggregateId, int amount) implements DomainEvent {
}
