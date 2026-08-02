package com.javastarterkit.patterns.layeredarchitecture.presentation;

import com.javastarterkit.patterns.layeredarchitecture.business.OrderService;
import com.javastarterkit.patterns.layeredarchitecture.models.Order;

import java.util.Objects;

/**
 * Presentation layer: console/CLI adapter.
 *
 * <p>This adapter translates user commands into calls on the business layer
 * ({@link OrderService}). It provides a simple text-based interface for
 * placing orders, adding items, and printing order details.
 *
 * <p><b>Thread-Safety:</b> This adapter is stateless (holds only a reference
 * to the thread-safe {@link OrderService}) and can be safely shared across
 * threads.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public final class OrderConsole {

    private final OrderService service;

    /**
     * Creates a new {@code OrderConsole} with the given service.
     *
     * @param service the business layer service
     * @throws NullPointerException if service is null
     */
    public OrderConsole(OrderService service) {
        this.service = Objects.requireNonNull(service, "OrderService must not be null");
    }

    /**
     * Places a new order for the given customer.
     *
     * @param customer the customer name
     * @return the new order ID
     */
    public String placeOrder(String customer) {
        Order order = service.placeOrder(customer);
        System.out.println("  [CLI] Placed order " + order.id() + " for " + customer);
        return order.id();
    }

    /**
     * Adds a line item to an existing order.
     *
     * @param orderId the order identifier
     * @param product the product name
     * @param price   the unit price as a decimal string
     * @param qty     the quantity
     */
    public void addItem(String orderId, String product, String price, int qty) {
        Order order = service.addItem(orderId, product, price, qty);
        System.out.println("  [CLI] Added " + product + " x" + qty + " -> " + order);
    }

    /**
     * Prints the details of an order.
     *
     * @param orderId the order identifier
     */
    public void printOrder(String orderId) {
        Order order = service.getOrder(orderId);
        System.out.println("  [CLI] Order " + order.id() + " for " + order.customer()
                + " | total=" + order.total() + " | cancelled=" + order.isCancelled());
    }
}