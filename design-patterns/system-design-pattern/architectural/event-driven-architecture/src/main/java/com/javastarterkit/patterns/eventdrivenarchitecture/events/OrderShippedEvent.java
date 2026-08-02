package com.javastarterkit.patterns.eventdrivenarchitecture.events;

import com.javastarterkit.patterns.eventdrivenarchitecture.core.Event;
import java.time.Instant;
import java.util.UUID;

/**
 * Domain event published when an order is shipped.
 *
 * @param eventId    unique event identifier
 * @param occurredAt timestamp of the event
 * @param source     source component
 * @param orderId    the order identifier
 * @param trackingNumber shipping tracking number
 */
public record OrderShippedEvent(
        UUID eventId,
        Instant occurredAt,
        String source,
        String orderId,
        String trackingNumber) implements Event {

    /**
     * Factory method creating a new OrderShippedEvent.
     *
     * @param orderId        the order identifier
     * @param trackingNumber shipping tracking number
     * @return a new event instance
     */
    public static OrderShippedEvent of(String orderId, String trackingNumber) {
        return new OrderShippedEvent(
                UUID.randomUUID(),
                Instant.now(),
                "ShippingService",
                orderId,
                trackingNumber);
    }
}