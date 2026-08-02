package com.javastarterkit.patterns.microservicesaggregator.services.impl;

import com.javastarterkit.patterns.microservicesaggregator.models.Inventory;
import com.javastarterkit.patterns.microservicesaggregator.services.InventoryService;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Thread-safe in-memory implementation of the inventory microservice.
 *
 * <p>Uses a {@link ConcurrentHashMap} for lock-free concurrent reads and
 * atomic writes. Suitable for testing and demos.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public final class InMemoryInventoryService implements InventoryService {

    private final ConcurrentHashMap<String, Inventory> stock = new ConcurrentHashMap<>();

    /**
     * Constructs the service with a default inventory catalog.
     */
    public InMemoryInventoryService() {
        stock.put("p-1001", new Inventory("p-1001", 15, true));
        stock.put("p-1002", new Inventory("p-1002", 0, false));
        stock.put("p-1003", new Inventory("p-1003", 42, true));
    }

    @Override
    public Optional<Inventory> findByProductId(String productId) {
        Objects.requireNonNull(productId, "Product ID must not be null");
        return Optional.ofNullable(stock.get(productId));
    }
}