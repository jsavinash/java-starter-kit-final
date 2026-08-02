package com.javastarterkit.patterns.microservicesaggregator;

import com.javastarterkit.patterns.microservicesaggregator.aggregator.ProductAggregator;
import com.javastarterkit.patterns.microservicesaggregator.exception.ProductNotFoundException;
import com.javastarterkit.patterns.microservicesaggregator.models.ProductDetail;
import com.javastarterkit.patterns.microservicesaggregator.services.impl.InMemoryInventoryService;
import com.javastarterkit.patterns.microservicesaggregator.services.impl.InMemoryProductService;
import com.javastarterkit.patterns.microservicesaggregator.services.impl.InMemoryReviewService;

/**
 * Main entry point demonstrating the Microservices Aggregator pattern.
 *
 * <p>Wires the three downstream microservices (product, inventory, review)
 * into the {@link ProductAggregator} and demonstrates the end-to-end flow:
 * the client makes a single call to the aggregator, which fans out to all
 * three services in parallel and composes a unified {@link ProductDetail}.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public final class MicroservicesAggregatorApp {

    private MicroservicesAggregatorApp() {
        // Prevent instantiation
    }

    /**
     * Demonstrates the microservices aggregator pattern end-to-end.
     */
    public static void demonstrate() {
        System.out.println("\n=== Microservices Aggregator Pattern ===");
        System.out.println("Compose responses from multiple microservices into one\n");

        // --- Build the microservices -------------------------------------------
        InMemoryProductService productService = new InMemoryProductService();
        InMemoryInventoryService inventoryService = new InMemoryInventoryService();
        InMemoryReviewService reviewService = new InMemoryReviewService();

        // --- Build the aggregator ----------------------------------------------
        ProductAggregator aggregator = new ProductAggregator(productService, inventoryService, reviewService);

        try {
            // --- Client makes a single call to the aggregator ------------------
            System.out.println("--- Client calls aggregator for product 'p-1001' ---");
            ProductDetail detail = aggregator.getProductDetail("p-1001");
            System.out.println(detail);

            System.out.println("\n--- Client calls aggregator for product 'p-1002' ---");
            ProductDetail detail2 = aggregator.getProductDetail("p-1002");
            System.out.println(detail2);

            System.out.println("\n--- Client calls aggregator for product 'p-1003' ---");
            ProductDetail detail3 = aggregator.getProductDetail("p-1003");
            System.out.println(detail3);

            System.out.println("\n--- Client calls aggregator for unknown product ---");
            try {
                aggregator.getProductDetail("p-9999");
            } catch (ProductNotFoundException e) {
                System.out.println("  Error: " + e.getMessage());
            }
        } finally {
            aggregator.shutdown();
        }

        System.out.println("\nBenefits:");
        System.out.println("- Client makes one call instead of N calls");
        System.out.println("- Aggregator fans out to services in parallel (CompletableFuture)");
        System.out.println("- Microservices remain independent and focused");
        System.out.println("- Aggregator handles partial failures gracefully");
    }

    /**
     * Main entry point.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        demonstrate();
    }
}