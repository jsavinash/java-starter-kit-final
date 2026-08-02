package com.javastarterkit.patterns.layeredarchitecture.persistence;

import com.javastarterkit.patterns.layeredarchitecture.models.Order;

import java.util.Optional;

/**
 * Persistence layer contract for storing and retrieving orders.
 *
 * <p>This interface defines the boundary between the business layer and the
 * persistence layer. The business layer depends only on this abstraction
 * (Dependency Inversion Principle), allowing different implementations
 * (in-memory, JDBC, JPA, etc.) to be swapped without changing business logic.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public interface OrderRepository {

    /**
     * Persists the given order. If an order with the same ID already exists,
     * it is replaced.
     *
     * @param order the order to save
     * @throws NullPointerException if order is null
     */
    void save(Order order);

    /**
     * Retrieves an order by its identifier.
     *
     * @param id the order identifier
     * @return an {@link Optional} containing the order if found, or empty
     * @throws NullPointerException if id is null
     */
    Optional<Order> findById(String id);
}