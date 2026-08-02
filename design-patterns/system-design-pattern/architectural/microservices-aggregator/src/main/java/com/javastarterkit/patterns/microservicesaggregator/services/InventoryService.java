package com.javastarterkit.patterns.microservicesaggregator.services;

import com.javastarterkit.patterns.microservicesaggregator.models.Inventory;
import java.util.Optional;

/**
 * Contract for the inventory microservice.
 *
 * <p>Defines the operations the inventory service must support. The aggregator
 * depends on this abstraction (Dependency Inversion Principle), allowing
 * different implementations to be swapped without changing the aggregator.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public interface InventoryService {

    /**
     * Finds inventory by the product identifier.
     *
     * @param productId the product identifier
     * @return an {@link Optional} containing the inventory, or empty if not found
     */
    Optional<Inventory> findByProductId(String productId);
}