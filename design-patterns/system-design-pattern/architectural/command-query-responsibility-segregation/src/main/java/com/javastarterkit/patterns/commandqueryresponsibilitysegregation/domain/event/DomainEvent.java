package com.javastarterkit.patterns.commandqueryresponsibilitysegregation.domain.event;

/**
 * Sealed base type for all domain events.
 *
 * <p>Every event carries the aggregate ID it pertains to, enabling projections
 * to route events to the correct read-model entry.  The type is sealed so the
 * compiler can verify exhaustiveness when pattern-matching on events.
 */
public sealed interface DomainEvent
        permits AccountOpened, MoneyDeposited, MoneyWithdrawn, AccountClosed {

    /**
     * @return the ID of the aggregate this event pertains to
     */
    String aggregateId();
}
