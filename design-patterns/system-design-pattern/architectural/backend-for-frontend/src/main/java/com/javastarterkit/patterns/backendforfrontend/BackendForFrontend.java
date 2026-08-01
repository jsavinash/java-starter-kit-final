package com.javastarterkit.patterns.backendforfrontend;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Backend-for-Frontend (BFF) Pattern — Production-Grade LLD Implementation
 *
 * <p>The <b>Backend-for-Frontend</b> pattern introduces a dedicated backend
 * service for each frontend client (web, mobile, CLI). Each BFF aggregates
 * data from multiple downstream microservices and tailors the response to
 * the specific needs of that frontend — reducing over-fetching, simplifying
 * client-side logic, and providing a single entry point per client type.
 *
 * <h2>Architecture</h2>
 * <ul>
 *   <li><b>Microservices</b> — {@link UserService}, {@link OrderService},
 *       {@link ProductService}: independent, thread-safe services with
 *       single responsibilities</li>
 *   <li><b>BFF Controllers</b> — {@link WebBffController},
 *       {@link MobileBffController}: client-specific aggregators that
 *       compose responses from multiple microservices</li>
 *   <li><b>Service Registry</b> — {@link ServiceRegistry}: thread-safe
 *       registry for microservice discovery using {@link ConcurrentHashMap}</li>
 *   <li><b>Models</b> — {@link UserProfile}, {@link OrderSummary},
 *       {@link ProductInfo}, {@link WebDashboard}, {@link MobileDashboard}:
 *       immutable data carriers</li>
 *   <li><b>Exceptions</b> — {@link ServiceUnavailableException},
 *       {@link NotFoundException}: domain-specific exceptions</li>
 * </ul>
 *
 * <h2>Thread-Safety Strategy</h2>
 * <ul>
 *   <li>All microservice data stores use {@link ConcurrentHashMap}</li>
 *   <li>The {@link ServiceRegistry} uses {@link ConcurrentHashMap} for
 *       thread-safe service registration and lookup</li>
 *   <li>All model objects are immutable Java records</li>
 *   <li>No shared mutable state — each BFF controller receives immutable
 *       data from services and composes a new immutable response</li>
 * </ul>
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class BackendForFrontend {

    /**
     * Demonstrates the BFF pattern: register microservices, create web and
     * mobile BFF controllers, and show how each frontend gets a tailored
     * response from the same underlying services.
     */
    public static void demonstrate() {
        System.out.println("\n=== Backend-for-Frontend (BFF) Pattern ===");
        System.out.println("Dedicated backends for each frontend client\n");

        // --- 1. Register microservices in the thread-safe registry --------
        ServiceRegistry registry = ServiceRegistry.getInstance();
        UserService userService = new UserService();
        OrderService orderService = new OrderService();
        ProductService productService = new ProductService();

        registry.register("userService", userService);
        registry.register("orderService", orderService);
        registry.register("productService", productService);

        // --- 2. Seed some data -------------------------------------------
        userService.createUser("alice", "Alice Johnson", "alice@example.com");
        userService.createUser("bob", "Bob Smith", "bob@example.com");
        orderService.createOrder("alice", "p-1001", 2, "1299.99");
        orderService.createOrder("alice", "p-1002", 1, "29.99");
        orderService.createOrder("bob", "p-1003", 1, "89.99");
        productService.createProduct("p-1001", "Laptop Pro", "1299.99");
        productService.createProduct("p-1002", "Wireless Mouse", "29.99");
        productService.createProduct("p-1003", "Mechanical Keyboard", "89.99");

        // --- 3. Create BFF controllers for each frontend -----------------
        WebBffController webBff = new WebBffController(userService, orderService, productService);
        MobileBffController mobileBff = new MobileBffController(userService, orderService, productService);

        // --- 4. Web frontend: full dashboard with all details ------------
        System.out.println("--- Web BFF: GET /web/dashboard?user=alice ---");
        WebDashboard webDashboard = webBff.getDashboard("alice");
        System.out.println(webDashboard);

        // --- 5. Mobile frontend: lightweight summary ----------------------
        System.out.println("\n--- Mobile BFF: GET /mobile/dashboard?user=alice ---");
        MobileDashboard mobileDashboard = mobileBff.getDashboard("alice");
        System.out.println(mobileDashboard);

        // --- 6. Error handling: user not found ----------------------------
        System.out.println("\n--- Web BFF: GET /web/dashboard?user=unknown ---");
        try {
            webBff.getDashboard("unknown");
        } catch (NotFoundException e) {
            System.out.println("  Error: " + e.getMessage());
        }

        System.out.println("\nBenefits:");
        System.out.println("- Each frontend gets a tailored response (no over-fetching)");
        System.out.println("- BFF encapsulates aggregation logic per client type");
        System.out.println("- Microservices remain independent and reusable");
        System.out.println("- Thread-safe registry enables concurrent service access");
    }

    public static void main(String[] args) {
        demonstrate();
    }

    // =========================================================================
    // EXCEPTIONS — domain-specific error types
    // =========================================================================

    /** Thrown when a requested resource is not found. */
    static final class NotFoundException extends RuntimeException {
        NotFoundException(String message) {
            super(message);
        }
    }

    /** Thrown when a downstream service is unavailable. */
    static final class ServiceUnavailableException extends RuntimeException {
        ServiceUnavailableException(String message) {
            super(message);
        }
    }

    // =========================================================================
    // MODELS — immutable data carriers (Java records)
    // =========================================================================

    /** Immutable user profile from the user service. */
    record UserProfile(String userId, String displayName, String email) {
    }

    /** Immutable order summary from the order service. */
    record OrderSummary(String orderId, String userId, String productId,
                        int quantity, String totalPrice, String status) {
    }

    /** Immutable product info from the product service. */
    record ProductInfo(String productId, String name, String price) {
    }

    /** Immutable order with product details — composed by the BFF. */
    record OrderWithProduct(String orderId, String productName,
                            int quantity, String totalPrice, String status) {
    }

    /** Immutable web dashboard response — full details for the web frontend. */
    record WebDashboard(
            UserProfile user,
            List<OrderWithProduct> orders,
            int totalOrders,
            String totalSpent) {

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("  WebDashboard{\n");
            sb.append("    user=").append(user.displayName()).append(" (").append(user.email()).append(")\n");
            sb.append("    totalOrders=").append(totalOrders).append("\n");
            sb.append("    totalSpent=").append(totalSpent).append("\n");
            sb.append("    orders=\n");
            for (OrderWithProduct order : orders) {
                sb.append("      - ").append(order.productName())
                        .append(" x").append(order.quantity())
                        .append(" = ").append(order.totalPrice())
                        .append(" [").append(order.status()).append("]\n");
            }
            sb.append("  }");
            return sb.toString();
        }
    }

    /** Immutable mobile dashboard response — lightweight for the mobile frontend. */
    record MobileDashboard(
            String userName,
            int orderCount,
            String lastOrderTotal) {

        @Override
        public String toString() {
            return "  MobileDashboard{userName=" + userName
                    + ", orderCount=" + orderCount
                    + ", lastOrderTotal=" + lastOrderTotal + "}";
        }
    }

    // =========================================================================
    // MICROSERVICES — independent, thread-safe, single-responsibility
    // =========================================================================

    /**
     * User service: manages user profiles.
     * Thread-safe via {@link ConcurrentHashMap}.
     */
    static final class UserService {
        private final Map<String, UserProfile> users = new ConcurrentHashMap<>();

        UserProfile createUser(String userId, String displayName, String email) {
            UserProfile profile = new UserProfile(userId, displayName, email);
            users.put(userId, profile);
            return profile;
        }

        UserProfile getUser(String userId) {
            UserProfile profile = users.get(userId);
            if (profile == null) {
                throw new NotFoundException("User not found: " + userId);
            }
            return profile;
        }

        boolean exists(String userId) {
            return users.containsKey(userId);
        }

        int size() {
            return users.size();
        }
    }

    /**
     * Order service: manages order summaries.
     * Thread-safe via {@link ConcurrentHashMap}.
     */
    static final class OrderService {
        private final Map<String, List<OrderSummary>> ordersByUser = new ConcurrentHashMap<>();

        OrderSummary createOrder(String userId, String productId, int quantity, String unitPrice) {
            String orderId = "ord-" + UUID.randomUUID().toString().substring(0, 8);
            BigDecimal total = new BigDecimal(unitPrice).multiply(BigDecimal.valueOf(quantity));
            OrderSummary order = new OrderSummary(
                    orderId, userId, productId, quantity, total.toPlainString(), "CONFIRMED");
            ordersByUser.computeIfAbsent(userId, k -> new ArrayList<>()).add(order);
            return order;
        }

        List<OrderSummary> getOrdersByUser(String userId) {
            return List.copyOf(ordersByUser.getOrDefault(userId, List.of()));
        }
    }

    /**
     * Product service: manages product catalog.
     * Thread-safe via {@link ConcurrentHashMap}.
     */
    static final class ProductService {
        private final Map<String, ProductInfo> products = new ConcurrentHashMap<>();

        ProductInfo createProduct(String productId, String name, String price) {
            ProductInfo product = new ProductInfo(productId, name, price);
            products.put(productId, product);
            return product;
        }

        ProductInfo getProduct(String productId) {
            ProductInfo product = products.get(productId);
            if (product == null) {
                throw new NotFoundException("Product not found: " + productId);
            }
            return product;
        }

        Optional<ProductInfo> findById(String productId) {
            return Optional.ofNullable(products.get(productId));
        }
    }

    // =========================================================================
    // SERVICE REGISTRY — thread-safe singleton using enum
    // =========================================================================

    /**
     * Thread-safe service registry implemented as an enum singleton.
     * Uses {@link ConcurrentHashMap} for concurrent service registration
     * and lookup. This follows the Effective Java singleton pattern.
     */
    enum ServiceRegistry {
        INSTANCE;

        private final Map<String, Object> services = new ConcurrentHashMap<>();

        void register(String name, Object service) {
            services.put(name, service);
        }

        @SuppressWarnings("unchecked")
        <T> T lookup(String name, Class<T> type) {
            Object service = services.get(name);
            if (service == null) {
                throw new ServiceUnavailableException("Service not registered: " + name);
            }
            return (T) service;
        }

        boolean isRegistered(String name) {
            return services.containsKey(name);
        }

        void unregister(String name) {
            services.remove(name);
        }

        static ServiceRegistry getInstance() {
            return INSTANCE;
        }
    }

    // =========================================================================
    // BFF CONTROLLERS — client-specific aggregators
    // =========================================================================

    /**
     * BFF for the <b>web frontend</b>. Composes a full dashboard with
     * user profile, all orders (with product names), and total spent.
     * This is a heavier response suitable for desktop browsers.
     */
    static final class WebBffController {
        private final UserService userService;
        private final OrderService orderService;
        private final ProductService productService;

        WebBffController(UserService userService, OrderService orderService,
                         ProductService productService) {
            this.userService = userService;
            this.orderService = orderService;
            this.productService = productService;
        }

        /** Aggregates user + orders + product details into a web dashboard. */
        WebDashboard getDashboard(String userId) {
            // 1. Fetch user profile
            UserProfile user = userService.getUser(userId);

            // 2. Fetch all orders for the user
            List<OrderSummary> orders = orderService.getOrdersByUser(userId);

            // 3. Enrich each order with product details
            List<OrderWithProduct> enrichedOrders = new ArrayList<>();
            BigDecimal totalSpent = BigDecimal.ZERO;

            for (OrderSummary order : orders) {
                ProductInfo product = productService.findById(order.productId())
                        .orElse(new ProductInfo(order.productId(), "Unknown", "0"));
                enrichedOrders.add(new OrderWithProduct(
                        order.orderId(),
                        product.name(),
                        order.quantity(),
                        order.totalPrice(),
                        order.status()));
                totalSpent = totalSpent.add(new BigDecimal(order.totalPrice()));
            }

            // 4. Compose the web-specific response
            return new WebDashboard(user, enrichedOrders, orders.size(), totalSpent.toPlainString());
        }
    }

    /**
     * BFF for the <b>mobile frontend</b>. Composes a lightweight summary
     * with just the user name, order count, and last order total.
     * This is a minimal response suitable for mobile devices with
     * limited bandwidth.
     */
    static final class MobileBffController {
        private final UserService userService;
        private final OrderService orderService;
        private final ProductService productService;

        MobileBffController(UserService userService, OrderService orderService,
                            ProductService productService) {
            this.userService = userService;
            this.orderService = orderService;
            this.productService = productService;
        }

        /** Aggregates a lightweight summary for the mobile dashboard. */
        MobileDashboard getDashboard(String userId) {
            // 1. Fetch user profile (only need the name)
            UserProfile user = userService.getUser(userId);

            // 2. Fetch orders (only need count and last total)
            List<OrderSummary> orders = orderService.getOrdersByUser(userId);

            // 3. Compose the mobile-specific response (minimal data)
            String lastOrderTotal = orders.isEmpty()
                    ? "0"
                    : orders.get(orders.size() - 1).totalPrice();

            return new MobileDashboard(user.displayName(), orders.size(), lastOrderTotal);
        }
    }
}