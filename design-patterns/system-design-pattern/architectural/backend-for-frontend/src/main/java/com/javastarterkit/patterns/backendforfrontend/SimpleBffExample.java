package com.javastarterkit.patterns.backendforfrontend;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple Backend-for-Frontend (BFF) Example
 * <p>
 * A minimal, easy-to-understand demonstration of the BFF pattern.
 * <p>
 * The idea: Instead of having a frontend call multiple microservices directly,
 * you create a dedicated backend for each frontend type. Each BFF aggregates
 * data from multiple services and returns a single, tailored response.
 *
 * <pre>
 *  ┌──────────┐         ┌─────────────┐         ┌──────────────┐
 *  │ Web App  │────────▶│  Web BFF    │────────▶│ User Service │
 *  │          │         │ (aggregates)│────────▶│ Order Service│
 *  └──────────┘         └─────────────┘         └──────────────┘
 *
 *  ┌──────────┐         ┌─────────────┐         ┌──────────────┐
 *  │ Mobile   │────────▶│ Mobile BFF  │────────▶│ User Service │
 *  │ App      │         │ (aggregates)│────────▶│ Order Service│
 *  └──────────┘         └─────────────┘         └──────────────┘
 * </pre>
 */
public final class SimpleBffExample {

    // ═══════════════════════════════════════════════════════════════
    // 1. MICROSERVICES — Simple data sources (the "backend")
    // ═══════════════════════════════════════════════════════════════

    /**
     * Simulates a User microservice that returns user names.
     */
    static final class UserService {
        private final Map<String, String> users = new ConcurrentHashMap<>();

        void addUser(String userId, String name) {
            users.put(userId, name);
        }

        String getUserName(String userId) {
            return users.getOrDefault(userId, "Unknown");
        }
    }

    /**
     * Simulates an Order microservice that returns order counts.
     */
    static final class OrderService {
        private final Map<String, Integer> orderCounts = new ConcurrentHashMap<>();

        void addOrder(String userId) {
            orderCounts.merge(userId, 1, Integer::sum);
        }

        int getOrderCount(String userId) {
            return orderCounts.getOrDefault(userId, 0);
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // 2. BFF CONTROLLERS — One per frontend type
    // ═══════════════════════════════════════════════════════════════

    /**
     * BFF for the Web frontend.
     * Returns a FULL response with user name AND order count.
     */
    static final class WebBff {
        private final UserService userService;
        private final OrderService orderService;

        WebBff(UserService userService, OrderService orderService) {
            this.userService = userService;
            this.orderService = orderService;
        }

        /** Aggregates data from both services into one response. */
        String getDashboard(String userId) {
            String name = userService.getUserName(userId);
            int orders = orderService.getOrderCount(userId);
            return "Web Dashboard → User: " + name + " | Orders: " + orders;
        }
    }

    /**
     * BFF for the Mobile frontend.
     * Returns a LIGHTWEIGHT response with only the order count
     * (mobile apps want minimal data to save bandwidth).
     */
    static final class MobileBff {
        private final OrderService orderService;

        MobileBff(OrderService orderService) {
            this.orderService = orderService;
        }

        /** Returns only what the mobile app needs. */
        String getBadge(String userId) {
            int orders = orderService.getOrderCount(userId);
            return "Mobile Badge → Orders: " + orders;
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // 3. DEMO — Show the pattern in action
    // ═══════════════════════════════════════════════════════════════

    public static void demonstrate() {
        System.out.println("\n=== Simple BFF Pattern Example ===\n");

        // Step 1: Create microservices and add some data
        UserService userService = new UserService();
        OrderService orderService = new OrderService();

        userService.addUser("u1", "Alice");
        orderService.addOrder("u1");
        orderService.addOrder("u1");
        orderService.addOrder("u1");

        // Step 2: Create BFF controllers for each frontend
        WebBff webBff = new WebBff(userService, orderService);
        MobileBff mobileBff = new MobileBff(orderService);

        // Step 3: Each frontend calls its own BFF and gets a tailored response
        System.out.println("Web frontend calls WebBff:");
        System.out.println("  " + webBff.getDashboard("u1"));
        // Output: Web Dashboard → User: Alice | Orders: 3

        System.out.println("\nMobile frontend calls MobileBff:");
        System.out.println("  " + mobileBff.getBadge("u1"));
        // Output: Mobile Badge → Orders: 3

        System.out.println("\nKey takeaway:");
        System.out.println("  - Web BFF aggregates from 2 services (user + orders)");
        System.out.println("  - Mobile BFF only calls 1 service (orders) — less data, less bandwidth");
        System.out.println("  - Frontends don't know about microservices — they just call their BFF");
    }

    public static void main(String[] args) {
        demonstrate();
    }
}