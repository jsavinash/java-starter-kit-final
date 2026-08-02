package com.javastarterkit.patterns.eventdrivenarchitecture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.javastarterkit.patterns.eventdrivenarchitecture.core.EventBus;
import com.javastarterkit.patterns.eventdrivenarchitecture.core.EventListener;
import com.javastarterkit.patterns.eventdrivenarchitecture.events.OrderPlacedEvent;
import com.javastarterkit.patterns.eventdrivenarchitecture.exception.InvalidOrderException;
import com.javastarterkit.patterns.eventdrivenarchitecture.service.EmailService;
import com.javastarterkit.patterns.eventdrivenarchitecture.service.InventoryService;
import com.javastarterkit.patterns.eventdrivenarchitecture.service.OrderService;
import com.javastarterkit.patterns.eventdrivenarchitecture.service.ShippingService;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

/**
 * End-to-end tests for the event-driven architecture.
 */
class EventBusTest {

    @Test
    void placeOrderTriggersAllSubscribers() {
        EventBus eventBus = new EventBus();
        EmailService emailService = new EmailService(eventBus);
        InventoryService inventoryService = new InventoryService(eventBus);
        ShippingService shippingService = new ShippingService(eventBus);
        OrderService orderService = new OrderService(eventBus);

        orderService.placeOrder("ORD-001", "john@example.com", 2);

        assertThat(emailService.getConfirmationCount()).isEqualTo(1);
        assertThat(emailService.getShippingCount()).isEqualTo(1);
        assertThat(inventoryService.getReservedQuantity("ORD-001")).isEqualTo(2);
        assertThat(inventoryService.getTotalReserved()).isEqualTo(2);
        assertThat(shippingService.isShipped("ORD-001")).isTrue();
        assertThat(shippingService.getShippedCount()).isEqualTo(1);

        eventBus.shutdown();
    }

    @Test
    void multipleOrdersAccumulateState() {
        EventBus eventBus = new EventBus();
        EmailService emailService = new EmailService(eventBus);
        InventoryService inventoryService = new InventoryService(eventBus);
        ShippingService shippingService = new ShippingService(eventBus);
        OrderService orderService = new OrderService(eventBus);

        orderService.placeOrder("ORD-001", "a@b.com", 2);
        orderService.placeOrder("ORD-002", "c@d.com", 5);

        assertThat(emailService.getConfirmationCount()).isEqualTo(2);
        assertThat(emailService.getShippingCount()).isEqualTo(2);
        assertThat(inventoryService.getTotalReserved()).isEqualTo(7);
        assertThat(inventoryService.getReservedQuantity("ORD-001")).isEqualTo(2);
        assertThat(inventoryService.getReservedQuantity("ORD-002")).isEqualTo(5);
        assertThat(shippingService.getShippedCount()).isEqualTo(2);

        eventBus.shutdown();
    }

    @Test
    void invalidOrderThrows() {
        EventBus eventBus = new EventBus();
        OrderService orderService = new OrderService(eventBus);

        assertThatThrownBy(() -> orderService.placeOrder("", "a@b.com", 1))
                .isInstanceOf(InvalidOrderException.class)
                .hasMessageContaining("Order ID");
        assertThatThrownBy(() -> orderService.placeOrder("ORD-1", "", 1))
                .isInstanceOf(InvalidOrderException.class)
                .hasMessageContaining("email");
        assertThatThrownBy(() -> orderService.placeOrder("ORD-1", "a@b.com", 0))
                .isInstanceOf(InvalidOrderException.class)
                .hasMessageContaining("Quantity");

        eventBus.shutdown();
    }

    @Test
    void unsubscribeStopsDelivery() {
        EventBus eventBus = new EventBus();
        AtomicInteger count = new AtomicInteger(0);
        EventListener<OrderPlacedEvent> listener = event -> count.incrementAndGet();
        eventBus.subscribe(OrderPlacedEvent.class, listener);

        OrderPlacedEvent event = OrderPlacedEvent.of("ORD-1", "a@b.com", 1);
        eventBus.publish(event);
        assertThat(count.get()).isEqualTo(1);

        boolean removed = eventBus.unsubscribe(OrderPlacedEvent.class, listener);
        assertThat(removed).isTrue();

        eventBus.publish(event);
        assertThat(count.get()).isEqualTo(1);

        eventBus.shutdown();
    }

    @Test
    void listenerExceptionIsIsolated() {
        EventBus eventBus = new EventBus();
        AtomicInteger goodCount = new AtomicInteger(0);
        eventBus.subscribe(OrderPlacedEvent.class, event -> {
            throw new IllegalStateException("boom");
        });
        eventBus.subscribe(OrderPlacedEvent.class, event -> goodCount.incrementAndGet());

        eventBus.publish(OrderPlacedEvent.of("ORD-1", "a@b.com", 1));

        assertThat(goodCount.get()).isEqualTo(1);

        eventBus.shutdown();
    }
}