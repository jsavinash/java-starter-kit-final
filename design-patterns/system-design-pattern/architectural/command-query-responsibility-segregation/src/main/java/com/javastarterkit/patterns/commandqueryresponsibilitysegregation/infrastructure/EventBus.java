package com.javastarterkit.patterns.commandqueryresponsibilitysegregation.infrastructure;

import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.projection.EventHandler;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.domain.event.DomainEvent;

/**
 * In-process publish/subscribe event bus that connects the command (write)
 * side to the query (read) side.
 *
 * <h3>Design</h3>
 * <ul>
 *   <li>Subscribers register via {@link #register(Class, EventHandler)} for a
 *       specific event subtype. A single event type may have multiple
 *       subscribers (fan-out).</li>
 *   <li>Events are published synchronously — the {@link #publish} method
 *       blocks until all subscribers have been notified. This provides
 *       <i>immediate consistency</i>: after a command completes, the read
 *       model is guaranteed to reflect the new state.</li>
 *   <li>The subscriber registry uses {@link CopyOnWriteArrayList} so that
 *       concurrent publishes across threads do not require external
 *       synchronization on the iteration path.</li>
 * </ul>
 *
 * <h3>Production Considerations</h3>
 * <p>In a production deployment this would be replaced by an asynchronous
 * message broker (Kafka, RabbitMQ, Pulsar). The synchronous in-memory
 * implementation is sufficient for demonstrating the CQRS flow and for unit
 * testing.
 */
public interface EventBus {

    /**
     * Registers a handler that will be notified whenever an event of the
     * given type is published.
     *
     * @param eventType the concrete event class to subscribe to
     * @param handler   the callback invoked on each matching event
     * @param <E>       the event type
     */
    <E extends DomainEvent> void register(Class<E> eventType, EventHandler<E> handler);

    /**
     * Publishes a domain event to all subscribers registered for the
     * event's concrete type.
     *
     * @param event the event to publish (never {@code null})
     */
    void publish(DomainEvent event);
}
