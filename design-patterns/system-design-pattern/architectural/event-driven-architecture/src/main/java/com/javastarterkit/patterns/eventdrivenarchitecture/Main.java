package com.javastarterkit.patterns.eventdrivenarchitecture;

import com.javastarterkit.patterns.eventdrivenarchitecture.core.EventBus;
import com.javastarterkit.patterns.eventdrivenarchitecture.service.EmailService;
import com.javastarterkit.patterns.eventdrivenarchitecture.service.InventoryService;
import com.javastarterkit.patterns.eventdrivenarchitecture.service.OrderService;
import com.javastarterkit.patterns.eventdrivenarchitecture.service.ShippingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entry point demonstrating the end-to-end event-driven order flow.
 */
public final class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private Main() {
    }

    /**
     * Demonstrates the event-driven architecture with an order lifecycle.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        LOGGER.info("=== Event-Driven Architecture Pattern ===");

        EventBus eventBus = new EventBus();

        // Subscribers register first
        EmailService emailService = new EmailService(eventBus);
        InventoryService inventoryService = new InventoryService(eventBus);
        ShippingService shippingService = new ShippingService(eventBus);

        // Publisher
        OrderService orderService = new OrderService(eventBus);

        // End-to-end flow: place order -> OrderPlacedEvent -> email + inventory + shipping -> OrderShippedEvent -> email
        orderService.placeOrder("ORD-001", "john@example.com", 2);
        orderService.placeOrder("ORD-002", "jane@example.com", 5);

        LOGGER.info("--- Results ---");
        LOGGER.info("Email confirmations sent: {}", emailService.getConfirmationCount());
        LOGGER.info("Shipping notifications sent: {}", emailService.getShippingCount());
        LOGGER.info("Total inventory reserved: {}", inventoryService.getTotalReserved());
        LOGGER.info("Reserved for ORD-001: {}", inventoryService.getReservedQuantity("ORD-001"));
        LOGGER.info("Orders shipped: {}", shippingService.getShippedCount());
        LOGGER.info("ORD-001 shipped: {}", shippingService.isShipped("ORD-001"));

        eventBus.shutdown();
    }
}