package com.javastarterkit.patterns.microservicesaggregator.services.impl;

import com.javastarterkit.patterns.microservicesaggregator.models.Review;
import com.javastarterkit.patterns.microservicesaggregator.services.ReviewService;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Thread-safe in-memory implementation of the review microservice.
 *
 * <p>Uses a {@link ConcurrentHashMap} for lock-free concurrent reads and
 * atomic writes. Returns immutable lists via {@link List#copyOf}.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public final class InMemoryReviewService implements ReviewService {

    private final ConcurrentHashMap<String, List<Review>> reviews = new ConcurrentHashMap<>();

    /**
     * Constructs the service with a default review catalog.
     */
    public InMemoryReviewService() {
        reviews.put("p-1001", List.of(
                new Review("Alice", 5, "Excellent laptop!"),
                new Review("Bob", 4, "Great performance, a bit heavy")));
        reviews.put("p-1002", List.of(
                new Review("Carol", 3, "Works fine, battery life is short")));
        reviews.put("p-1003", List.of());
    }

    @Override
    public List<Review> findByProductId(String productId) {
        Objects.requireNonNull(productId, "Product ID must not be null");
        return List.copyOf(reviews.getOrDefault(productId, List.of()));
    }
}