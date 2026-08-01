package com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.projection;

import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.domain.event.DomainEvent;

/**
 * Functional contract for reacting to a specific domain event type.
 *
 * <p>Projections register one {@code EventHandler} per event subtype they
 * care about via {@link com.javastarterkit.patterns.commandqueryresponsibilitysegregation.infrastructure.EventBus
 * EventBus}. The handler is invoked synchronously when the corresponding
 * event is published on the bus.
 *
 * @param <E> the domain event type this handler consumes
 */
@FunctionalInterface
public interface EventHandler<E extends DomainEvent> {

    /**
     * Called when an event of the registered type is published.
     *
     * @param event the domain event (never {@code null})
     */
    void handle(E event);
}
