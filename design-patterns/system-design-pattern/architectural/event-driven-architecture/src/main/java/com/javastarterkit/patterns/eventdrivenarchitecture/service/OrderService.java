package com.javastarterkit.patterns.eventdrivenarchitecture.service;

import com.javastarterkit.patterns.eventdrivenarchitecture.core.EventBus;
import com.javastarterkit.patterns.eventdrivenarchitecture.events.OrderPlacedEvent;
import com.javastarterkit.patterns.eventdrivenarchitecture.exception.InvalidOrderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Publisher service that places orders and emits {@link OrderPlacedEvent}s.
 */
public final class OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    private final EventBus eventBus;

    /**
     * @param eventBus the event bus to publish events to
     */
    public OrderService(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    /**
     * Places an order and publishes an {@link OrderPlacedEvent}.
     *
     * @param orderId       the order identifier
     * @param customerEmail customer email
     * @param quantity      number of items
     * @throws InvalidOrderException if any argument is invalid
     */
    public void placeOrder(String orderId, String customerEmail, int quantity) {
        validate(orderId, customerEmail, quantity);
        LOGGER.info("Placing order {} for {} items", orderId, quantity);
        eventBus.publish(OrderPlacedEvent.of(orderId, customerEmail, quantity));
    }

    private void validate(String orderId, String customerEmail, int quantity) {
        if (orderId == null || orderId.isBlank()) {
            throw new InvalidOrderException("Order ID must not be blank");
        }
        if (customerEmail == null || customerEmail.isBlank()) {
            throw new InvalidOrderException("Customer email must not be blank");
        }
        if (quantity <= 0) {
            throw new InvalidOrderException("Quantity must be positive");
        }
    }
}