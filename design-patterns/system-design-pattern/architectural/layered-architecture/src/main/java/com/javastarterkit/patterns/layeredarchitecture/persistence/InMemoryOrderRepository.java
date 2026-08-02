package com.javastarterkit.patterns.layeredarchitecture.persistence;

import com.javastarterkit.patterns.layeredarchitecture.models.Order;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Thread-safe in-memory implementation of {@link OrderRepository}.
 *
 * <p>Uses a {@link ConcurrentHashMap} for lock-free concurrent reads and
 * atomic writes. This implementation is suitable for testing, demos, and
 * single-node applications. A production implementation would use the same
 * interface with a database backend (JDBC, JPA, etc.).
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public final class InMemoryOrderRepository implements OrderRepository {

    private final ConcurrentHashMap<String, Order> store = new ConcurrentHashMap<>();

    @Override
    public void save(Order order) {
        Objects.requireNonNull(order, "Order must not be null");
        store.put(order.id(), order);
    }

    @Override
    public Optional<Order> findById(String id) {
        Objects.requireNonNull(id, "Order ID must not be null");
        return Optional.ofNullable(store.get(id));
    }
}