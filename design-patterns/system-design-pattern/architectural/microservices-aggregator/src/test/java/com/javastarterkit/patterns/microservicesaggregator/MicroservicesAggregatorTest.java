package com.javastarterkit.patterns.microservicesaggregator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.javastarterkit.patterns.microservicesaggregator.MicroservicesAggregator.Inventory;
import com.javastarterkit.patterns.microservicesaggregator.MicroservicesAggregator.InventoryService;
import com.javastarterkit.patterns.microservicesaggregator.MicroservicesAggregator.Product;
import com.javastarterkit.patterns.microservicesaggregator.MicroservicesAggregator.ProductAggregator;
import com.javastarterkit.patterns.microservicesaggregator.MicroservicesAggregator.ProductDetail;
import com.javastarterkit.patterns.microservicesaggregator.MicroservicesAggregator.ProductService;
import com.javastarterkit.patterns.microservicesaggregator.MicroservicesAggregator.Review;
import com.javastarterkit.patterns.microservicesaggregator.MicroservicesAggregator.ReviewService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests verifying the microservices aggregator pattern: each microservice
 * is independent, the aggregator fans out and composes responses, and the
 * client receives a unified payload from a single call.
 */
class MicroservicesAggregatorTest {

    @Test
    @DisplayName("product service returns product data independently")
    void productServiceReturnsData() {
        ProductService service = new ProductService();

        Optional<Product> product = service.findById("p-1001");
        assertTrue(product.isPresent());
        assertEquals("Laptop Pro", product.get().name());
        assertEquals("1299.99", product.get().price());

        assertTrue(service.findById("p-9999").isEmpty());
    }

    @Test
    @DisplayName("inventory service returns stock data independently")
    void inventoryServiceReturnsData() {
        InventoryService service = new InventoryService();

        Optional<Inventory> inventory = service.findByProductId("p-1002");
        assertTrue(inventory.isPresent());
        assertEquals(0, inventory.get().quantity());
        assertFalse(inventory.get().inStock());
    }

    @Test
    @DisplayName("review service returns reviews independently")
    void reviewServiceReturnsData() {
        ReviewService service = new ReviewService();

        List<Review> reviews = service.findByProductId("p-1001");
        assertEquals(2, reviews.size());

        // Product with no reviews returns an empty list
        assertTrue(service.findByProductId("p-1003").isEmpty());
    }

    @Test
    @DisplayName("aggregator composes responses from all three services")
    void aggregatorComposesResponses() {
        ProductAggregator aggregator = new ProductAggregator(
                new ProductService(), new InventoryService(), new ReviewService());

        ProductDetail detail = aggregator.getProductDetail("p-1001");

        // Product service data
        assertEquals("p-1001", detail.id());
        assertEquals("Laptop Pro", detail.name());
        assertEquals("1299.99", detail.price());

        // Inventory service data
        assertEquals(15, detail.stockQuantity());
        assertTrue(detail.inStock());

        // Review service data
        assertEquals(2, detail.reviewCount());
        assertEquals(4.5, detail.averageRating());
        assertEquals(2, detail.reviews().size());
    }

    @Test
    @DisplayName("aggregator handles out-of-stock products")
    void aggregatorHandlesOutOfStock() {
        ProductAggregator aggregator = new ProductAggregator(
                new ProductService(), new InventoryService(), new ReviewService());

        ProductDetail detail = aggregator.getProductDetail("p-1002");

        assertEquals("Wireless Mouse", detail.name());
        assertEquals(0, detail.stockQuantity());
        assertFalse(detail.inStock());
    }

    @Test
    @DisplayName("aggregator handles products with no reviews (zero rating)")
    void aggregatorHandlesNoReviews() {
        ProductAggregator aggregator = new ProductAggregator(
                new ProductService(), new InventoryService(), new ReviewService());

        ProductDetail detail = aggregator.getProductDetail("p-1003");

        assertEquals("Mechanical Keyboard", detail.name());
        assertEquals(0, detail.reviewCount());
        assertEquals(0.0, detail.averageRating());
    }

    @Test
    @DisplayName("aggregator throws for unknown products")
    void aggregatorThrowsForUnknownProduct() {
        ProductAggregator aggregator = new ProductAggregator(
                new ProductService(), new InventoryService(), new ReviewService());

        assertThrows(IllegalArgumentException.class, () -> aggregator.getProductDetail("p-9999"));
    }

    @Test
    @DisplayName("demonstrate runs without throwing")
    void demonstrateRunsSuccessfully() {
        // Smoke test: the public demonstration should complete without errors.
        MicroservicesAggregator.demonstrate();
    }
}