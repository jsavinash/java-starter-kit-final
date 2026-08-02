package com.javastarterkit.patterns.microservicesaggregator.models;

import java.util.Objects;

/**
 * Immutable product data returned by the product microservice.
 *
 * <p>This record is inherently thread-safe and represents the product
 * catalog information: id, name, description, and price.
 *
 * @param id          the unique product identifier
 * @param name        the product name
 * @param description the product description
 * @param price       the product price as a string (e.g., "1299.99")
 * @author Java Starter Kit
 * @version 1.0.0
 */
public record Product(String id, String name, String description, String price) {

    /**
     * Compact constructor validating all fields are non-null.
     */
    public Product {
        Objects.requireNonNull(id, "Product ID must not be null");
        Objects.requireNonNull(name, "Product name must not be null");
        Objects.requireNonNull(description, "Product description must not be null");
        Objects.requireNonNull(price, "Product price must not be null");
    }
}