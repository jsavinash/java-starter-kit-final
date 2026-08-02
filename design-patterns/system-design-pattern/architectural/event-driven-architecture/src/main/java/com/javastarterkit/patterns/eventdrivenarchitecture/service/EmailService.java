package com.javastarterkit.patterns.eventdrivenarchitecture.service;

import com.javastarterkit.patterns.eventdrivenarchitecture.core.EventBus;
import com.javastarterkit.patterns.eventdrivenarchitecture.events.OrderPlacedEvent;
import com.javastarterkit.patterns.eventdrivenarchitecture.events.OrderShippedEvent;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Subscriber service that sends email notifications in response to order events.
 * Registers separate lambda listeners for each event type to avoid
 * generic interface erasure conflicts.
 */
public final class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private final AtomicInteger confirmationCount = new AtomicInteger(0);
    private final AtomicInteger shippingCount = new AtomicInteger(0);

    /**
     * Registers this service with the event bus.
     *
     * @param eventBus the event bus to subscribe to
     */
    public EmailService(EventBus eventBus) {
        eventBus.subscribe(OrderPlacedEvent.class, this::onOrderPlaced);
        eventBus.subscribe(OrderShippedEvent.class, this::onOrderShipped);
    }

    private void onOrderPlaced(OrderPlacedEvent event) {
        confirmationCount.incrementAndGet();
        LOGGER.info("EmailService: Sending order confirmation to {} for order {}",
                event.customerEmail(), event.orderId());
    }

    private void onOrderShipped(OrderShippedEvent event) {
        shippingCount.incrementAndGet();
        LOGGER.info("EmailService: Sending shipping notification for order {} (tracking: {})",
                event.orderId(), event.trackingNumber());
    }

    /**
     * @return number of order confirmation emails sent
     */
    public int getConfirmationCount() {
        return confirmationCount.get();
    }

    /**
     * @return number of shipping notification emails sent
     */
    public int getShippingCount() {
        return shippingCount.get();
    }
}