package com.javastarterkit.patterns.microservicesaggregator.models;

import java.util.Objects;

/**
 * Immutable inventory data returned by the inventory microservice.
 *
 * <p>This record is inherently thread-safe and represents stock availability
 * for a product: quantity on hand and whether the product is in stock.
 *
 * @param productId the product identifier this inventory belongs to
 * @param quantity  the quantity available in stock
 * @param inStock   whether the product is currently in stock
 * @author Java Starter Kit
 * @version 1.0.0
 */
public record Inventory(String productId, int quantity, boolean inStock) {

    /**
     * Compact constructor validating the product ID is non-null and the
     * quantity is non-negative.
     */
    public Inventory {
        Objects.requireNonNull(productId, "Product ID must not be null");
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity must be non-negative");
        }
    }
}