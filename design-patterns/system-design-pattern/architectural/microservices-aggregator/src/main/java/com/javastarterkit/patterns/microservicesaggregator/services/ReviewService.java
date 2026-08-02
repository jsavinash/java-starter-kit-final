package com.javastarterkit.patterns.microservicesaggregator.services;

import com.javastarterkit.patterns.microservicesaggregator.models.Review;
import java.util.List;

/**
 * Contract for the review microservice.
 *
 * <p>Defines the operations the review service must support. The aggregator
 * depends on this abstraction (Dependency Inversion Principle), allowing
 * different implementations to be swapped without changing the aggregator.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public interface ReviewService {

    /**
     * Finds all reviews for a product.
     *
     * @param productId the product identifier
     * @return an immutable list of reviews (empty if none)
     */
    List<Review> findByProductId(String productId);
}