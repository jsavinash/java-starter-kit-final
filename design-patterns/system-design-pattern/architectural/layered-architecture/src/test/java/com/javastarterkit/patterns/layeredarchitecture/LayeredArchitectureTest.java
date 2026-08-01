package com.javastarterkit.patterns.layeredarchitecture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.javastarterkit.patterns.layeredarchitecture.LayeredArchitecture.InMemoryOrderRepository;
import com.javastarterkit.patterns.layeredarchitecture.LayeredArchitecture.Money;
import com.javastarterkit.patterns.layeredarchitecture.LayeredArchitecture.Order;
import com.javastarterkit.patterns.layeredarchitecture.LayeredArchitecture.OrderConsole;
import com.javastarterkit.patterns.layeredarchitecture.LayeredArchitecture.OrderController;
import com.javastarterkit.patterns.layeredarchitecture.LayeredArchitecture.OrderItem;
import com.javastarterkit.patterns.layeredarchitecture.LayeredArchitecture.OrderRepository;
import com.javastarterkit.patterns.layeredarchitecture.LayeredArchitecture.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests verifying the layered architecture pattern: each layer has a
 * distinct responsibility, layers depend only on the layer below, and the
 * presentation/persistence layers are swappable without changing business logic.
 */
class LayeredArchitectureTest {

    @Test
    @DisplayName("Money value object supports arithmetic operations")
    void moneySupportsArithmetic() {
        Money price = Money.of("10.50");
        Money total = price.multiply(3);
        assertEquals("31.50", total.toString());

        Money sum = Money.of("10.00").add(Money.of("5.50"));
        assertEquals("15.50", sum.toString());

        assertThrows(IllegalArgumentException.class, () -> Money.of("-1.00"));
    }

    @Test
    @DisplayName("Order domain object enforces business rules")
    void orderEnforcesBusinessRules() {
        Order order = new Order("ord-1", "Alice");
        order.addItem(new OrderItem("Laptop", Money.of("999.99"), 1));
        order.addItem(new OrderItem("Mouse", Money.of("29.99"), 2));

        assertEquals(2, order.items().size());
        assertEquals("1059.97", order.total().toString());

        order.cancel();
        assertTrue(order.isCancelled());
        assertThrows(IllegalStateException.class,
                () -> order.addItem(new OrderItem("Keyboard", Money.of("79.99"), 1)));
        assertThrows(IllegalStateException.class, order::cancel);
    }

    @Test
    @DisplayName("business layer orchestrates use cases through the persistence layer")
    void businessLayerOrchestratesUseCases() {
        OrderRepository repository = new InMemoryOrderRepository();
        OrderService service = new OrderService(repository);

        Order order = service.placeOrder("Alice");
        service.addItem(order.id(), "Laptop", "999.99", 1);
        service.addItem(order.id(), "Mouse", "29.99", 2);

        Order loaded = service.getOrder(order.id());
        assertEquals("Alice", loaded.customer());
        assertEquals(2, loaded.items().size());
        assertEquals("1059.97", loaded.total().toString());
    }

    @Test
    @DisplayName("business layer rejects invalid operations")
    void businessLayerRejectsInvalidOperations() {
        OrderRepository repository = new InMemoryOrderRepository();
        OrderService service = new OrderService(repository);

        Order order = service.placeOrder("Bob");
        assertThrows(IllegalArgumentException.class,
                () -> service.addItem(order.id(), "Keyboard", "79.99", 0));
        assertThrows(IllegalArgumentException.class, () -> service.getOrder("missing"));
    }

    @Test
    @DisplayName("presentation layer (REST) drives the business layer")
    void restControllerDrivesBusinessLayer() {
        OrderRepository repository = new InMemoryOrderRepository();
        OrderService service = new OrderService(repository);
        OrderController controller = new OrderController(service);

        String orderId = controller.post("/orders", "{\"customer\":\"Alice\"}");
        controller.post("/orders/" + orderId + "/items",
                "{\"product\":\"Laptop\",\"price\":\"999.99\",\"qty\":1}");

        Order order = service.getOrder(orderId);
        assertEquals(1, order.items().size());
        assertEquals("999.99", order.total().toString());
    }

    @Test
    @DisplayName("presentation layer (Console) drives the business layer")
    void consoleDrivesBusinessLayer() {
        OrderRepository repository = new InMemoryOrderRepository();
        OrderService service = new OrderService(repository);
        OrderConsole console = new OrderConsole(service);

        String orderId = console.placeOrder("Bob");
        console.addItem(orderId, "Keyboard", "79.99", 1);

        Order order = service.getOrder(orderId);
        assertEquals("Bob", order.customer());
        assertEquals("79.99", order.total().toString());
    }

    @Test
    @DisplayName("cancelling an order through the business layer updates state")
    void cancelOrderUpdatesState() {
        OrderRepository repository = new InMemoryOrderRepository();
        OrderService service = new OrderService(repository);

        Order order = service.placeOrder("Carol");
        service.cancelOrder(order.id());

        assertTrue(service.getOrder(order.id()).isCancelled());
    }

    @Test
    @DisplayName("demonstrate runs without throwing")
    void demonstrateRunsSuccessfully() {
        // Smoke test: the public demonstration should complete without errors.
        LayeredArchitecture.demonstrate();
    }
}