package com.javastarterkit.patterns.eventdrivenarchitecture.service;

import com.javastarterkit.patterns.eventdrivenarchitecture.core.EventBus;
import com.javastarterkit.patterns.eventdrivenarchitecture.core.EventListener;
import com.javastarterkit.patterns.eventdrivenarchitecture.events.OrderPlacedEvent;
import com.javastarterkit.patterns.eventdrivenarchitecture.events.OrderShippedEvent;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Subscriber service that ships orders and emits {@link OrderShippedEvent}s.
 * Uses {@link ConcurrentHashMap#newKeySet()} for thread-safe shipped-order tracking.
 */
public final class ShippingService implements EventListener<OrderPlacedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShippingService.class);

    private final EventBus eventBus;
    private final Set<String> shippedOrders = ConcurrentHashMap.newKeySet();
    private final AtomicInteger shippedCount = new AtomicInteger(0);

    /**
     * Registers this service with the event bus.
     *
     * @param eventBus the event bus to subscribe to and publish to
     */
    public ShippingService(EventBus eventBus) {
        this.eventBus = eventBus;
        eventBus.subscribe(OrderPlacedEvent.class, this);
    }

    @Override
    public void onEvent(OrderPlacedEvent event) {
        String trackingNumber = "TRK-" + event.orderId();
        shippedOrders.add(event.orderId());
        shippedCount.incrementAndGet();
        LOGGER.info("ShippingService: Shipping order {} (tracking: {})",
                event.orderId(), trackingNumber);
        eventBus.publish(OrderShippedEvent.of(event.orderId(), trackingNumber));
    }

    /**
     * @param orderId the order identifier
     * @return {@code true} if the order has been shipped
     */
    public boolean isShipped(String orderId) {
        return shippedOrders.contains(orderId);
    }

    /**
     * @return number of orders shipped
     */
    public int getShippedCount() {
        return shippedCount.get();
    }
}