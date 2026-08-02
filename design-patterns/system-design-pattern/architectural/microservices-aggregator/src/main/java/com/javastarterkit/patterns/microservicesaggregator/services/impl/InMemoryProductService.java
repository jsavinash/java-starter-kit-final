package com.javastarterkit.patterns.microservicesaggregator.services.impl;

import com.javastarterkit.patterns.microservicesaggregator.models.Product;
import com.javastarterkit.patterns.microservicesaggregator.services.ProductService;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Thread-safe in-memory implementation of the product microservice.
 *
 * <p>Uses a {@link ConcurrentHashMap} for lock-free concurrent reads and
 * atomic writes. Suitable for testing and demos.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public final class InMemoryProductService implements ProductService {

    private final ConcurrentHashMap<String, Product> products = new ConcurrentHashMap<>();

    /**
     * Constructs the service with a default product catalog.
     */
    public InMemoryProductService() {
        products.put("p-1001", new Product("p-1001", "Laptop Pro", "High-performance laptop", "1299.99"));
        products.put("p-1002", new Product("p-1002", "Wireless Mouse", "Ergonomic wireless mouse", "29.99"));
        products.put("p-1003", new Product("p-1003", "Mechanical Keyboard", "RGB mechanical keyboard", "89.99"));
    }

    @Override
    public Optional<Product> findById(String productId) {
        Objects.requireNonNull(productId, "Product ID must not be null");
        return Optional.ofNullable(products.get(productId));
    }
}