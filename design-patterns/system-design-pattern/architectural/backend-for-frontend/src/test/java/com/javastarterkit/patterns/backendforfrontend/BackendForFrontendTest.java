package com.javastarterkit.patterns.backendforfrontend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.javastarterkit.patterns.backendforfrontend.BackendForFrontend.MobileBffController;
import com.javastarterkit.patterns.backendforfrontend.BackendForFrontend.MobileDashboard;
import com.javastarterkit.patterns.backendforfrontend.BackendForFrontend.NotFoundException;
import com.javastarterkit.patterns.backendforfrontend.BackendForFrontend.OrderService;
import com.javastarterkit.patterns.backendforfrontend.BackendForFrontend.OrderSummary;
import com.javastarterkit.patterns.backendforfrontend.BackendForFrontend.ProductService;
import com.javastarterkit.patterns.backendforfrontend.BackendForFrontend.ServiceRegistry;
import com.javastarterkit.patterns.backendforfrontend.BackendForFrontend.ServiceUnavailableException;
import com.javastarterkit.patterns.backendforfrontend.BackendForFrontend.UserService;
import com.javastarterkit.patterns.backendforfrontend.BackendForFrontend.WebBffController;
import com.javastarterkit.patterns.backendforfrontend.BackendForFrontend.WebDashboard;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Comprehensive unit tests for the Backend-for-Frontend pattern.
 *
 * <p>Tests cover:
 * <ul>
 *   <li>Microservice independence and data isolation</li>
 *   <li>BFF aggregation for web and mobile frontends</li>
 *   <li>Service registry thread-safety</li>
 *   <li>Concurrent access to microservices</li>
 *   <li>Error handling (not found, service unavailable)</li>
 *   <li>End-to-end demonstration smoke test</li>
 * </ul>
 */
class BackendForFrontendTest {

    // =========================================================================
    // Helper: create a fully seeded set of services for tests
    // =========================================================================

    private record SeededServices(
            UserService userService,
            OrderService orderService,
            ProductService productService) {
    }

    private SeededServices seedServices() {
        UserService userService = new UserService();
        OrderService orderService = new OrderService();
        ProductService productService = new ProductService();

        userService.createUser("alice", "Alice Johnson", "alice@example.com");
        userService.createUser("bob", "Bob Smith", "bob@example.com");

        productService.createProduct("p-1001", "Laptop Pro", "1299.99");
        productService.createProduct("p-1002", "Wireless Mouse", "29.99");

        orderService.createOrder("alice", "p-1001", 2, "1299.99");
        orderService.createOrder("alice", "p-1002", 1, "29.99");

        return new SeededServices(userService, orderService, productService);
    }

    // =========================================================================
    // Microservice tests
    // =========================================================================

    @Test
    @DisplayName("UserService creates and retrieves user profiles")
    void userServiceCreatesAndRetrieves() {
        UserService service = new UserService();
        service.createUser("alice", "Alice", "alice@example.com");

        assertEquals("Alice", service.getUser("alice").displayName());
        assertEquals("alice@example.com", service.getUser("alice").email());
        assertTrue(service.exists("alice"));
    }

    @Test
    @DisplayName("UserService throws NotFoundException for unknown users")
    void userServiceThrowsForUnknown() {
        UserService service = new UserService();
        assertThrows(NotFoundException.class, () -> service.getUser("unknown"));
    }

    @Test
    @DisplayName("OrderService creates and retrieves orders by user")
    void orderServiceCreatesAndRetrieves() {
        OrderService service = new OrderService();
        service.createOrder("alice", "p-1001", 2, "100.00");

        List<OrderSummary> orders = service.getOrdersByUser("alice");
        assertEquals(1, orders.size());
        assertEquals("200.00", orders.get(0).totalPrice());
        assertEquals("CONFIRMED", orders.get(0).status());
    }

    @Test
    @DisplayName("OrderService returns empty list for users with no orders")
    void orderServiceReturnsEmptyForNoOrders() {
        OrderService service = new OrderService();
        assertTrue(service.getOrdersByUser("nobody").isEmpty());
    }

    @Test
    @DisplayName("ProductService creates and retrieves products")
    void productServiceCreatesAndRetrieves() {
        ProductService service = new ProductService();
        service.createProduct("p-1001", "Laptop", "999.99");

        assertEquals("Laptop", service.getProduct("p-1001").name());
        assertTrue(service.findById("p-1001").isPresent());
        assertTrue(service.findById("p-9999").isEmpty());
    }

    // =========================================================================
    // BFF controller tests
    // =========================================================================

    @Test
    @DisplayName("WebBffController aggregates user, orders, and product details")
    void webBffAggregatesFullDashboard() {
        SeededServices services = seedServices();
        WebBffController webBff = new WebBffController(
                services.userService, services.orderService, services.productService);

        WebDashboard dashboard = webBff.getDashboard("alice");

        assertEquals("Alice Johnson", dashboard.user().displayName());
        assertEquals(2, dashboard.totalOrders());
        assertEquals("2629.97", dashboard.totalSpent());
        assertEquals(2, dashboard.orders().size());

        // Verify product names are enriched
        assertEquals("Laptop Pro", dashboard.orders().get(0).productName());
        assertEquals("Wireless Mouse", dashboard.orders().get(1).productName());
    }

    @Test
    @DisplayName("MobileBffController returns lightweight summary")
    void mobileBffReturnsLightweightSummary() {
        SeededServices services = seedServices();
        MobileBffController mobileBff = new MobileBffController(
                services.userService, services.orderService, services.productService);

        MobileDashboard dashboard = mobileBff.getDashboard("alice");

        assertEquals("Alice Johnson", dashboard.userName());
        assertEquals(2, dashboard.orderCount());
        assertEquals("29.99", dashboard.lastOrderTotal());
    }

    @Test
    @DisplayName("Web and Mobile BFFs return different response shapes for same data")
    void webAndMobileBffsReturnDifferentShapes() {
        SeededServices services = seedServices();
        WebBffController webBff = new WebBffController(
                services.userService, services.orderService, services.productService);
        MobileBffController mobileBff = new MobileBffController(
                services.userService, services.orderService, services.productService);

        WebDashboard web = webBff.getDashboard("alice");
        MobileDashboard mobile = mobileBff.getDashboard("alice");

        // Web has full order details; mobile has just count and last total
        assertEquals(2, web.orders().size());
        assertNotNull(web.user().email());

        // Mobile does not include email or individual orders
        assertEquals(2, mobile.orderCount());
        assertEquals("29.99", mobile.lastOrderTotal());
    }

    @Test
    @DisplayName("BFF controllers throw NotFoundException for unknown users")
    void bffThrowsForUnknownUser() {
        SeededServices services = seedServices();
        WebBffController webBff = new WebBffController(
                services.userService, services.orderService, services.productService);

        assertThrows(NotFoundException.class, () -> webBff.getDashboard("unknown"));
    }

    @Test
    @DisplayName("WebBffController handles users with no orders")
    void webBffHandlesNoOrders() {
        SeededServices services = seedServices();
        services.userService.createUser("carol", "Carol", "carol@example.com");
        WebBffController webBff = new WebBffController(
                services.userService, services.orderService, services.productService);

        WebDashboard dashboard = webBff.getDashboard("carol");

        assertEquals("Carol", dashboard.user().displayName());
        assertEquals(0, dashboard.totalOrders());
        assertEquals("0", dashboard.totalSpent());
        assertTrue(dashboard.orders().isEmpty());
    }

    // =========================================================================
    // Service registry tests
    // =========================================================================

    @Test
    @DisplayName("ServiceRegistry registers and looks up services")
    void serviceRegistryRegistersAndLooksUp() {
        ServiceRegistry registry = ServiceRegistry.getInstance();
        UserService userService = new UserService();

        registry.register("testUserService", userService);

        assertTrue(registry.isRegistered("testUserService"));
        UserService lookedUp = registry.lookup("testUserService", UserService.class);
        assertNotNull(lookedUp);

        registry.unregister("testUserService");
    }

    @Test
    @DisplayName("ServiceRegistry throws for unregistered services")
    void serviceRegistryThrowsForUnregistered() {
        ServiceRegistry registry = ServiceRegistry.getInstance();
        assertThrows(ServiceUnavailableException.class,
                () -> registry.lookup("nonexistent", UserService.class));
    }

    @Test
    @DisplayName("ServiceRegistry is a singleton (enum-based)")
    void serviceRegistryIsSingleton() {
        ServiceRegistry instance1 = ServiceRegistry.getInstance();
        ServiceRegistry instance2 = ServiceRegistry.getInstance();
        assertTrue(instance1 == instance2);
    }

    // =========================================================================
    // Thread-safety / concurrency tests
    // =========================================================================

    @Test
    @DisplayName("UserService supports concurrent user creation")
    void userServiceIsThreadSafe() throws InterruptedException {
        UserService service = new UserService();
        int threadCount = 50;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger successCount = new AtomicInteger(0);

        for (int i = 0; i < threadCount; i++) {
            final int idx = i;
            executor.submit(() -> {
                try {
                    service.createUser("user-" + idx, "User " + idx, "user" + idx + "@test.com");
                    successCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(5, TimeUnit.SECONDS);
        executor.shutdown();

        assertEquals(threadCount, successCount.get());
        assertEquals(threadCount, service.size());
    }

    @Test
    @DisplayName("OrderService supports concurrent order creation for same user")
    void orderServiceIsThreadSafe() throws InterruptedException {
        OrderService service = new OrderService();
        service.createOrder("alice", "p-1001", 1, "10.00"); // seed user

        int threadCount = 20;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    service.createOrder("alice", "p-1001", 1, "10.00");
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(5, TimeUnit.SECONDS);
        executor.shutdown();

        // 1 initial + 20 concurrent = 21 orders
        assertEquals(21, service.getOrdersByUser("alice").size());
    }

    @Test
    @DisplayName("ServiceRegistry supports concurrent registration")
    void serviceRegistryIsThreadSafe() throws InterruptedException {
        ServiceRegistry registry = ServiceRegistry.getInstance();
        int threadCount = 30;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int idx = i;
            executor.submit(() -> {
                try {
                    registry.register("concurrent-service-" + idx, new UserService());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(5, TimeUnit.SECONDS);
        executor.shutdown();

        for (int i = 0; i < threadCount; i++) {
            assertTrue(registry.isRegistered("concurrent-service-" + i));
            registry.unregister("concurrent-service-" + i);
        }
    }

    // =========================================================================
    // Smoke test
    // =========================================================================

    @Test
    @DisplayName("demonstrate runs without throwing")
    void demonstrateRunsSuccessfully() {
        BackendForFrontend.demonstrate();
    }
}