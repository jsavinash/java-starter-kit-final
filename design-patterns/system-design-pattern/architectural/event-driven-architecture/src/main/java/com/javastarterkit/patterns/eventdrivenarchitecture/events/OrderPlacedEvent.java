package com.javastarterkit.patterns.eventdrivenarchitecture.events;

import com.javastarterkit.patterns.eventdrivenarchitecture.core.Event;
import java.time.Instant;
import java.util.UUID;

/**
 * Domain event published when a customer places an order.
 *
 * @param eventId    unique event identifier
 * @param occurredAt timestamp of the event
 * @param source     source component
 * @param orderId    the order identifier
 * @param customerEmail customer email for notifications
 * @param quantity   number of items ordered
 */
public record OrderPlacedEvent(
        UUID eventId,
        Instant occurredAt,
        String source,
        String orderId,
        String customerEmail,
        int quantity) implements Event {

    /**
     * Factory method creating a new OrderPlacedEvent.
     *
     * @param orderId       the order identifier
     * @param customerEmail customer email
     * @param quantity      number of items
     * @return a new event instance
     */
    public static OrderPlacedEvent of(String orderId, String customerEmail, int quantity) {
        return new OrderPlacedEvent(
                UUID.randomUUID(),
                Instant.now(),
                "OrderService",
                orderId,
                customerEmail,
                quantity);
    }
}