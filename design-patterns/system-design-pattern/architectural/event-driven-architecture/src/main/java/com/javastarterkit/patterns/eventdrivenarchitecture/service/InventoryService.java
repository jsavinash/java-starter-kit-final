package com.javastarterkit.patterns.eventdrivenarchitecture.service;

import com.javastarterkit.patterns.eventdrivenarchitecture.core.EventBus;
import com.javastarterkit.patterns.eventdrivenarchitecture.core.EventListener;
import com.javastarterkit.patterns.eventdrivenarchitecture.events.OrderPlacedEvent;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Subscriber service that reserves inventory in response to order events.
 * Uses {@link ConcurrentHashMap} for thread-safe per-order reservation tracking.
 */
public final class InventoryService implements EventListener<OrderPlacedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryService.class);

    private final Map<String, AtomicInteger> reservations = new ConcurrentHashMap<>();
    private final AtomicInteger totalReserved = new AtomicInteger(0);

    /**
     * Registers this service with the event bus.
     *
     * @param eventBus the event bus to subscribe to
     */
    public InventoryService(EventBus eventBus) {
        eventBus.subscribe(OrderPlacedEvent.class, this);
    }

    @Override
    public void onEvent(OrderPlacedEvent event) {
        reservations.computeIfAbsent(event.orderId(), k -> new AtomicInteger(0))
                .addAndGet(event.quantity());
        totalReserved.addAndGet(event.quantity());
        LOGGER.info("InventoryService: Reserved {} items for order {}",
                event.quantity(), event.orderId());
    }

    /**
     * @param orderId the order identifier
     * @return total quantity reserved for the given order
     */
    public int getReservedQuantity(String orderId) {
        AtomicInteger quantity = reservations.get(orderId);
        return quantity == null ? 0 : quantity.get();
    }

    /**
     * @return total quantity reserved across all orders
     */
    public int getTotalReserved() {
        return totalReserved.get();
    }
}