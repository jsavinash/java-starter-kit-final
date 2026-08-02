package com.javastarterkit.patterns.layeredarchitecture.models;

import com.javastarterkit.patterns.layeredarchitecture.exception.OrderCancelledException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Domain entity representing an order placed by a customer.
 *
 * <p>This entity encapsulates the core business rules for an order:
 * <ul>
 *   <li>Items can only be added to a non-cancelled order</li>
 *   <li>An order can only be cancelled once</li>
 *   <li>The total is computed as the sum of all line-item totals</li>
 * </ul>
 *
 * <p><b>Thread-Safety:</b> All mutable state transitions are guarded by
 * {@code synchronized} blocks to ensure atomic, thread-safe operations when
 * the same order is accessed concurrently (e.g., two threads adding items).
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public final class Order {

    private final String id;
    private final String customer;
    private final List<OrderItem> items;
    private boolean cancelled;

    /**
     * Creates a new order with a generated UUID.
     *
     * @param customer the customer name
     * @throws NullPointerException if customer is null
     */
    public Order(String customer) {
        this(UUID.randomUUID().toString(), customer);
    }

    /**
     * Creates a new order with the specified ID and customer.
     *
     * @param id       the order identifier
     * @param customer the customer name
     * @throws NullPointerException if id or customer is null
     */
    public Order(String id, String customer) {
        this.id = Objects.requireNonNull(id, "Order ID must not be null");
        this.customer = Objects.requireNonNull(customer, "Customer must not be null");
        this.items = new ArrayList<>();
        this.cancelled = false;
    }

    /**
     * Returns the order identifier.
     *
     * @return the order ID
     */
    public String id() {
        return id;
    }

    /**
     * Returns the customer name.
     *
     * @return the customer name
     */
    public String customer() {
        return customer;
    }

    /**
     * Returns an immutable copy of the order items.
     *
     * @return an unmodifiable list of order items
     */
    public List<OrderItem> items() {
        synchronized (this) {
            return List.copyOf(items);
        }
    }

    /**
     * Returns whether this order has been cancelled.
     *
     * @return {@code true} if cancelled, {@code false} otherwise
     */
    public boolean isCancelled() {
        synchronized (this) {
            return cancelled;
        }
    }

    /**
     * Adds a line item to this order.
     *
     * @param item the item to add
     * @throws NullPointerException     if item is null
     * @throws OrderCancelledException  if the order has already been cancelled
     */
    public void addItem(OrderItem item) {
        Objects.requireNonNull(item, "OrderItem must not be null");
        synchronized (this) {
            if (cancelled) {
                throw new OrderCancelledException("Cannot add items to cancelled order: " + id);
            }
            items.add(item);
        }
    }

    /**
     * Cancels this order. A cancelled order cannot be modified further.
     *
     * @throws OrderCancelledException if the order is already cancelled
     */
    public void cancel() {
        synchronized (this) {
            if (cancelled) {
                throw new OrderCancelledException("Order is already cancelled: " + id);
            }
            cancelled = true;
        }
    }

    /**
     * Computes the total value of this order.
     *
     * @return the sum of all line-item totals
     */
    public Money total() {
        synchronized (this) {
            return items.stream()
                    .map(OrderItem::total)
                    .reduce(new Money(BigDecimal.ZERO), Money::add);
        }
    }

    @Override
    public String toString() {
        synchronized (this) {
            return "Order{id=" + id + ", customer=" + customer
                    + ", items=" + items.size() + ", total=" + total()
                    + ", cancelled=" + cancelled + "}";
        }
    }
}