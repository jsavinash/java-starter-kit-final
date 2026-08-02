package com.javastarterkit.patterns.microservicesaggregator.models;

import java.util.List;
import java.util.Objects;

/**
 * Immutable unified response composed by the aggregator and returned to the client.
 *
 * <p>This record aggregates data from the product, inventory, and review
 * microservices into a single payload. It is inherently thread-safe.
 *
 * @param id            the product identifier
 * @param name          the product name
 * @param description   the product description
 * @param price         the product price
 * @param stockQuantity the available stock quantity
 * @param inStock       whether the product is in stock
 * @param reviewCount   the number of reviews
 * @param averageRating the average rating (0.0 if no reviews)
 * @param reviews       the list of reviews (immutable)
 * @author Java Starter Kit
 * @version 1.0.0
 */
public record ProductDetail(
        String id,
        String name,
        String description,
        String price,
        int stockQuantity,
        boolean inStock,
        int reviewCount,
        double averageRating,
        List<Review> reviews) {

    /**
     * Compact constructor validating all fields and returning a defensive
     * copy of the reviews list.
     */
    public ProductDetail {
        Objects.requireNonNull(id, "Product ID must not be null");
        Objects.requireNonNull(name, "Product name must not be null");
        Objects.requireNonNull(description, "Product description must not be null");
        Objects.requireNonNull(price, "Product price must not be null");
        Objects.requireNonNull(reviews, "Reviews must not be null");
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("Stock quantity must be non-negative");
        }
        if (reviewCount < 0) {
            throw new IllegalArgumentException("Review count must be non-negative");
        }
        reviews = List.copyOf(reviews);
    }

    @Override
    public String toString() {
        return "ProductDetail{"
                + "id='" + id + '\''
                + ", name='" + name + '\''
                + ", description='" + description + '\''
                + ", price='" + price + '\''
                + ", stockQuantity=" + stockQuantity
                + ", inStock=" + inStock
                + ", reviewCount=" + reviewCount
                + ", averageRating=" + String.format("%.1f", averageRating)
                + ", reviews=" + reviews
                + '}';
    }
}