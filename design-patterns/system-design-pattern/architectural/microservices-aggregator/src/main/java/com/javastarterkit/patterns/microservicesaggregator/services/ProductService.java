package com.javastarterkit.patterns.microservicesaggregator.services;

import com.javastarterkit.patterns.microservicesaggregator.models.Product;
import java.util.Optional;

/**
 * Contract for the product microservice.
 *
 * <p>Defines the operations the product service must support. The aggregator
 * depends on this abstraction (Dependency Inversion Principle), allowing
 * different implementations to be swapped without changing the aggregator.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public interface ProductService {

    /**
     * Finds a product by its unique identifier.
     *
     * @param productId the product identifier
     * @return an {@link Optional} containing the product, or empty if not found
     */
    Optional<Product> findById(String productId);
}