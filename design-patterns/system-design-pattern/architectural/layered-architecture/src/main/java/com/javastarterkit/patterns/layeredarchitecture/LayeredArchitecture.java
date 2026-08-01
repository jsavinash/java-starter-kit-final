package com.javastarterkit.patterns.layeredarchitecture;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Layered Architecture Pattern Example
 *
 * <p><b>Layered Architecture</b> (also known as <b>N-tier architecture</b>)
 * organizes the application into horizontal layers, each with a distinct
 * responsibility. The most common arrangement is three layers:
 * <ul>
 *   <li><b>Presentation Layer</b> — handles user interaction (UI, REST, CLI)</li>
 *   <li><b>Business/Application Layer</b> — implements business rules and use cases</li>
 *   <li><b>Persistence/Data Layer</b> — manages data storage and retrieval</li>
 * </ul>
 *
 * <p>The key rule: each layer depends only on the layer directly below it.
 * The presentation layer calls the business layer; the business layer calls
 * the persistence layer. This creates a strict dependency direction that
 * keeps the architecture predictable and testable.
 *
 * <p>This self-contained example models a simple <b>e-commerce order system</b>:
 * <ul>
 *   <li><b>Presentation Layer</b> — {@link OrderController} (simulated REST)
 *       and {@link OrderConsole} (CLI) handle user input</li>
 *   <li><b>Business Layer</b> — {@link OrderService} implements use cases
 *       (place order, cancel order, get order) and enforces business rules</li>
 *   <li><b>Persistence Layer</b> — {@link OrderRepository} (interface) and
 *       {@link InMemoryOrderRepository} (implementation) manage storage</li>
 *   <li><b>Domain Objects</b> — {@link Order}, {@link OrderItem}, {@link Money}
 *       are shared across layers</li>
 * </ul>
 *
 * <p>Each layer is isolated: the presentation layer knows nothing about
 * storage, and the persistence layer knows nothing about HTTP or the console.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class LayeredArchitecture {

    /**
     * Demonstrates layered architecture: place an order through the REST
     * controller, cancel it through the console, and show that each layer
     * depends only on the layer below it.
     */
    public static void demonstrate() {
        System.out.println("\n=== Layered Architecture Pattern ===");
        System.out.println("Organize code into horizontal layers with strict dependencies\n");

        // --- Build the layers (bottom-up) --------------------------------------
        OrderRepository repository = new InMemoryOrderRepository();   // Persistence layer
        OrderService service = new OrderService(repository);          // Business layer
        OrderController controller = new OrderController(service);    // Presentation layer
        OrderConsole console = new OrderConsole(service);             // Presentation layer

        // --- Presentation layer: REST controller -------------------------------
        System.out.println("--- Presentation layer: REST controller ---");
        String orderId = controller.post("/orders", "{\"customer\":\"Alice\"}");
        controller.post("/orders/" + orderId + "/items", "{\"product\":\"Laptop\",\"price\":\"999.99\",\"qty\":1}");
        controller.post("/orders/" + orderId + "/items", "{\"product\":\"Mouse\",\"price\":\"29.99\",\"qty\":2}");
        controller.get("/orders/" + orderId);

        // --- Presentation layer: Console ---------------------------------------
        System.out.println("\n--- Presentation layer: Console ---");
        String orderId2 = console.placeOrder("Bob");
        console.addItem(orderId2, "Keyboard", "79.99", 1);
        console.printOrder(orderId2);

        // --- Business layer: cancel an order -----------------------------------
        System.out.println("\n--- Business layer: cancel order ---");
        service.cancelOrder(orderId2);
        console.printOrder(orderId2);

        System.out.println("\nBenefits:");
        System.out.println("- Each layer has a single, well-defined responsibility");
        System.out.println("- Layers depend only on the layer directly below");
        System.out.println("- Presentation and persistence are swappable");
        System.out.println("- Easy to test each layer in isolation");
    }

    public static void main(String[] args) {
        demonstrate();
    }

    // =========================================================================
    // DOMAIN OBJECTS — shared across all layers
    // =========================================================================

    /** Value object representing a non-negative amount of money. */
    record Money(BigDecimal amount) {
        Money {
            if (amount == null || amount.signum() < 0) {
                throw new IllegalArgumentException("Amount must be non-negative");
            }
        }

        static Money of(String value) {
            return new Money(new BigDecimal(value));
        }

        Money add(Money other) {
            return new Money(amount.add(other.amount));
        }

        Money multiply(int quantity) {
            return new Money(amount.multiply(BigDecimal.valueOf(quantity)));
        }

        @Override
        public String toString() {
            return amount.toPlainString();
        }
    }

    /** A single line item in an order. */
    record OrderItem(String product, Money price, int quantity) {
        Money total() {
            return price.multiply(quantity);
        }
    }

    /** An order placed by a customer. */
    static final class Order {
        private final String id;
        private final String customer;
        private final List<OrderItem> items = new ArrayList<>();
        private boolean cancelled;

        Order(String id, String customer) {
            this.id = id;
            this.customer = customer;
        }

        String id() {
            return id;
        }

        String customer() {
            return customer;
        }

        List<OrderItem> items() {
            return List.copyOf(items);
        }

        boolean isCancelled() {
            return cancelled;
        }

        void addItem(OrderItem item) {
            if (cancelled) {
                throw new IllegalStateException("Cannot add items to a cancelled order");
            }
            items.add(item);
        }

        void cancel() {
            if (cancelled) {
                throw new IllegalStateException("Order is already cancelled");
            }
            cancelled = true;
        }

        Money total() {
            return items.stream()
                    .map(OrderItem::total)
                    .reduce(new Money(BigDecimal.ZERO), Money::add);
        }

        @Override
        public String toString() {
            return "Order{id=" + id + ", customer=" + customer
                    + ", items=" + items.size() + ", total=" + total()
                    + ", cancelled=" + cancelled + "}";
        }
    }

    // =========================================================================
    // PERSISTENCE LAYER — data storage and retrieval
    // =========================================================================

    /** Persistence layer contract: how the business layer stores orders. */
    interface OrderRepository {
        void save(Order order);

        Optional<Order> findById(String id);
    }

    /** In-memory implementation of the persistence layer. */
    static final class InMemoryOrderRepository implements OrderRepository {
        private final Map<String, Order> store = new LinkedHashMap<>();

        @Override
        public void save(Order order) {
            store.put(order.id(), order);
        }

        @Override
        public Optional<Order> findById(String id) {
            return Optional.ofNullable(store.get(id));
        }
    }

    // =========================================================================
    // BUSINESS LAYER — business rules and use cases
    // =========================================================================

    /**
     * Business layer: implements use cases and enforces business rules.
     * Depends only on the persistence layer (OrderRepository).
     */
    static final class OrderService {
        private final OrderRepository repository;

        OrderService(OrderRepository repository) {
            this.repository = repository;
        }

        /** Use case: place a new order. */
        Order placeOrder(String customer) {
            Order order = new Order(UUID.randomUUID().toString(), customer);
            repository.save(order);
            return order;
        }

        /** Use case: add an item to an order. */
        Order addItem(String orderId, String product, String price, int quantity) {
            if (quantity <= 0) {
                throw new IllegalArgumentException("Quantity must be positive");
            }
            Order order = getOrder(orderId);
            order.addItem(new OrderItem(product, Money.of(price), quantity));
            repository.save(order);
            return order;
        }

        /** Use case: cancel an order. */
        Order cancelOrder(String orderId) {
            Order order = getOrder(orderId);
            order.cancel();
            repository.save(order);
            return order;
        }

        /** Use case: retrieve an order. */
        Order getOrder(String orderId) {
            return repository.findById(orderId)
                    .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));
        }
    }

    // =========================================================================
    // PRESENTATION LAYER — user interaction (REST + Console)
    // =========================================================================

    /**
     * Presentation layer: simulated REST controller. Translates HTTP-style
     * requests into calls on the business layer (OrderService).
     */
    static final class OrderController {
        private final OrderService service;

        OrderController(OrderService service) {
            this.service = service;
        }

        /** Simulated POST /orders. */
        String post(String path, String body) {
            if (path.equals("/orders")) {
                String customer = extract(body, "customer");
                Order order = service.placeOrder(customer);
                System.out.println("  [REST] POST " + path + " -> 201 Created: " + order.id());
                return order.id();
            }
            if (path.endsWith("/items")) {
                String orderId = path.split("/")[2];
                String product = extract(body, "product");
                String price = extract(body, "price");
                int qty = Integer.parseInt(extract(body, "qty"));
                Order order = service.addItem(orderId, product, price, qty);
                System.out.println("  [REST] POST " + path + " -> 200 OK: " + order);
                return order.id();
            }
            throw new IllegalArgumentException("Unsupported POST path: " + path);
        }

        /** Simulated GET /orders/{id}. */
        void get(String path) {
            String orderId = path.split("/")[2];
            Order order = service.getOrder(orderId);
            System.out.println("  [REST] GET " + path + " -> 200 OK: " + order);
        }

        /** Minimal JSON-ish field extractor: handles {"key":"value"} and {"key":value}. */
        private static String extract(String body, String key) {
            String quotedMarker = "\"" + key + "\":\"";
            int quotedStart = body.indexOf(quotedMarker);
            if (quotedStart >= 0) {
                int start = quotedStart + quotedMarker.length();
                int end = body.indexOf('"', start);
                return body.substring(start, end);
            }
            String unquotedMarker = "\"" + key + "\":";
            int start = body.indexOf(unquotedMarker) + unquotedMarker.length();
            int end = body.indexOf(',', start);
            if (end < 0) {
                end = body.indexOf('}', start);
            }
            return body.substring(start, end).trim();
        }
    }

    /**
     * Presentation layer: console/CLI adapter. Translates user commands into
     * calls on the business layer (OrderService).
     */
    static final class OrderConsole {
        private final OrderService service;

        OrderConsole(OrderService service) {
            this.service = service;
        }

        String placeOrder(String customer) {
            Order order = service.placeOrder(customer);
            System.out.println("  [CLI] Placed order " + order.id() + " for " + customer);
            return order.id();
        }

        void addItem(String orderId, String product, String price, int qty) {
            Order order = service.addItem(orderId, product, price, qty);
            System.out.println("  [CLI] Added " + product + " x" + qty + " -> " + order);
        }

        void printOrder(String orderId) {
            Order order = service.getOrder(orderId);
            System.out.println("  [CLI] Order " + order.id() + " for " + order.customer()
                    + " | total=" + order.total() + " | cancelled=" + order.isCancelled());
        }
    }
}