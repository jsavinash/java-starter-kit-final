package com.javastarterkit.patterns.layeredarchitecture.presentation;

import com.javastarterkit.patterns.layeredarchitecture.business.OrderService;
import com.javastarterkit.patterns.layeredarchitecture.models.Order;

import java.util.Objects;

/**
 * Presentation layer: simulated REST controller.
 *
 * <p>This adapter translates HTTP-style requests into calls on the business
 * layer ({@link OrderService}). It knows nothing about storage or business
 * rules — it only parses input and delegates to the service.
 *
 * <p><b>Thread-Safety:</b> This controller is stateless (holds only a
 * reference to the thread-safe {@link OrderService}) and can be safely
 * shared across threads.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public final class OrderController {

    private final OrderService service;

    /**
     * Creates a new {@code OrderController} with the given service.
     *
     * @param service the business layer service
     * @throws NullPointerException if service is null
     */
    public OrderController(OrderService service) {
        this.service = Objects.requireNonNull(service, "OrderService must not be null");
    }

    /**
     * Simulated HTTP POST request.
     *
     * <p>Supported paths:
     * <ul>
     *   <li>{@code POST /orders} with body {@code {"customer":"Alice"}}</li>
     *   <li>{@code POST /orders/{id}/items} with body
     *       {@code {"product":"Laptop","price":"999.99","qty":1}}</li>
     * </ul>
     *
     * @param path the request path
     * @param body the JSON-like request body
     * @return the order ID
     * @throws IllegalArgumentException if the path is unsupported
     */
    public String post(String path, String body) {
        Objects.requireNonNull(path, "Path must not be null");
        Objects.requireNonNull(body, "Body must not be null");

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

    /**
     * Simulated HTTP GET request.
     *
     * <p>Supported paths:
     * <ul>
     *   <li>{@code GET /orders/{id}}</li>
     * </ul>
     *
     * @param path the request path
     * @throws IllegalArgumentException if the path is unsupported
     */
    public void get(String path) {
        Objects.requireNonNull(path, "Path must not be null");
        String orderId = path.split("/")[2];
        Order order = service.getOrder(orderId);
        System.out.println("  [REST] GET " + path + " -> 200 OK: " + order);
    }

    /**
     * Minimal JSON-ish field extractor: handles {@code {"key":"value"}}
     * and {@code {"key":value}} patterns.
     *
     * @param body the request body
     * @param key  the field name to extract
     * @return the extracted value
     */
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