package com.javastarterkit.patterns.layeredarchitecture.business;

import com.javastarterkit.patterns.layeredarchitecture.exception.OrderNotFoundException;
import com.javastarterkit.patterns.layeredarchitecture.models.Money;
import com.javastarterkit.patterns.layeredarchitecture.models.Order;
import com.javastarterkit.patterns.layeredarchitecture.models.OrderItem;
import com.javastarterkit.patterns.layeredarchitecture.persistence.OrderRepository;

import java.util.Objects;
import java.util.UUID;

/**
 * Business layer service implementing order management use cases.
 *
 * <p>This service orchestrates the use cases of the system:
 * <ul>
 *   <li>{@code placeOrder} — create a new order</li>
 *   <li>{@code addItem} — add a line item to an existing order</li>
 *   <li>{@code cancelOrder} — cancel an existing order</li>
 *   <li>{@code getOrder} — retrieve an order by ID</li>
 * </ul>
 *
 * <p><b>Dependency Inversion:</b> This service depends only on the
 * {@link OrderRepository} abstraction, not on any concrete implementation.
 * It knows nothing about HTTP, the console, or the database.
 *
 * <p><b>Thread-Safety:</b> This service is stateless — it holds no mutable
 * state. All state lives in the thread-safe repository and the thread-safe
 * {@link Order} entity. Therefore, a single instance can be safely shared
 * across multiple threads.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public final class OrderService {

    private final OrderRepository repository;

    /**
     * Creates a new {@code OrderService} with the given repository.
     *
     * @param repository the persistence layer repository
     * @throws NullPointerException if repository is null
     */
    public OrderService(OrderRepository repository) {
        this.repository = Objects.requireNonNull(repository, "OrderRepository must not be null");
    }

    /**
     * Use case: place a new order for the given customer.
     *
     * @param customer the customer name
     * @return the newly created order
     * @throws NullPointerException if customer is null
     */
    public Order placeOrder(String customer) {
        Objects.requireNonNull(customer, "Customer must not be null");
        Order order = new Order(UUID.randomUUID().toString(), customer);
        repository.save(order);
        return order;
    }

    /**
     * Use case: add a line item to an existing order.
     *
     * @param orderId  the order identifier
     * @param product  the product name
     * @param price    the unit price as a decimal string
     * @param quantity the quantity (must be positive)
     * @return the updated order
     * @throws NullPointerException     if orderId, product, or price is null
     * @throws IllegalArgumentException if quantity is not positive
     * @throws OrderNotFoundException   if the order does not exist
     */
    public Order addItem(String orderId, String product, String price, int quantity) {
        Objects.requireNonNull(orderId, "Order ID must not be null");
        Objects.requireNonNull(product, "Product must not be null");
        Objects.requireNonNull(price, "Price must not be null");
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        Order order = getOrder(orderId);
        order.addItem(new OrderItem(product, Money.of(price), quantity));
        repository.save(order);
        return order;
    }

    /**
     * Use case: cancel an existing order.
     *
     * @param orderId the order identifier
     * @return the cancelled order
     * @throws NullPointerException   if orderId is null
     * @throws OrderNotFoundException if the order does not exist
     */
    public Order cancelOrder(String orderId) {
        Objects.requireNonNull(orderId, "Order ID must not be null");
        Order order = getOrder(orderId);
        order.cancel();
        repository.save(order);
        return order;
    }

    /**
     * Use case: retrieve an order by its identifier.
     *
     * @param orderId the order identifier
     * @return the order
     * @throws NullPointerException   if orderId is null
     * @throws OrderNotFoundException if the order does not exist
     */
    public Order getOrder(String orderId) {
        Objects.requireNonNull(orderId, "Order ID must not be null");
        return repository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found: " + orderId));
    }
}