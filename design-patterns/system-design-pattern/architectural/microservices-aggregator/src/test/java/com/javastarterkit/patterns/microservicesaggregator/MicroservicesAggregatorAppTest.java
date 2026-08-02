package com.javastarterkit.patterns.microservicesaggregator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.javastarterkit.patterns.microservicesaggregator.aggregator.ProductAggregator;
import com.javastarterkit.patterns.microservicesaggregator.exception.ProductNotFoundException;
import com.javastarterkit.patterns.microservicesaggregator.exception.ServiceUnavailableException;
import com.javastarterkit.patterns.microservicesaggregator.models.Inventory;
import com.javastarterkit.patterns.microservicesaggregator.models.Product;
import com.javastarterkit.patterns.microservicesaggregator.models.ProductDetail;
import com.javastarterkit.patterns.microservicesaggregator.models.Review;
import com.javastarterkit.patterns.microservicesaggregator.services.InventoryService;
import com.javastarterkit.patterns.microservicesaggregator.services.ProductService;
import com.javastarterkit.patterns.microservicesaggregator.services.ReviewService;
import com.javastarterkit.patterns.microservicesaggregator.services.impl.InMemoryInventoryService;
import com.javastarterkit.patterns.microservicesaggregator.services.impl.InMemoryProductService;
import com.javastarterkit.patterns.microservicesaggregator.services.impl.InMemoryReviewService;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Comprehensive test suite for the Microservices Aggregator pattern.
 *
 * <p>Covers: model validation, individual microservice behavior, aggregator
 * composition, graceful degradation, error handling, and concurrency.
 */
@DisplayName("Microservices Aggregator Tests")
class MicroservicesAggregatorAppTest {

    private ProductAggregator aggregator;

    @BeforeEach
    void setUp() {
        aggregator = new ProductAggregator(
                new InMemoryProductService(),
                new InMemoryInventoryService(),
                new InMemoryReviewService());
    }

    @AfterEach
    void tearDown() {
        aggregator.shutdown();
    }

    // =========================================================================
    // MODEL VALIDATION
    // =========================================================================

    @Test
    @DisplayName("Product record rejects null fields")
    void productRejectsNullFields() {
        assertThatThrownBy(() -> new Product(null, "name", "desc", "1.0"))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Product ID must not be null");
    }

    @Test
    @DisplayName("Inventory record rejects negative quantity")
    void inventoryRejectsNegativeQuantity() {
        assertThatThrownBy(() -> new Inventory("p-1", -1, true))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Quantity must be non-negative");
    }

    @Test
    @DisplayName("Review record rejects invalid rating")
    void reviewRejectsInvalidRating() {
        assertThatThrownBy(() -> new Review("Alice", 6, "comment"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Rating must be between 1 and 5");
    }

    @Test
    @DisplayName("ProductDetail returns defensive copy of reviews")
    void productDetailReturnsDefensiveCopy() {
        List<Review> mutable = new java.util.ArrayList<>(List.of(new Review("A", 5, "c")));
        ProductDetail detail = new ProductDetail("p-1", "n", "d", "1.0", 1, true, 1, 5.0, mutable);

        assertThatThrownBy(() -> detail.reviews().add(new Review("B", 4, "x")))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    // =========================================================================
    // MICROSERVICE BEHAVIOR
    // =========================================================================

    @Test
    @DisplayName("Product service returns product data independently")
    void productServiceReturnsData() {
        ProductService service = new InMemoryProductService();

        Optional<Product> product = service.findById("p-1001");
        assertThat(product).isPresent();
        assertThat(product.get().name()).isEqualTo("Laptop Pro");
        assertThat(product.get().price()).isEqualTo("1299.99");

        assertThat(service.findById("p-9999")).isEmpty();
    }

    @Test
    @DisplayName("Inventory service returns stock data independently")
    void inventoryServiceReturnsData() {
        InventoryService service = new InMemoryInventoryService();

        Optional<Inventory> inventory = service.findByProductId("p-1002");
        assertThat(inventory).isPresent();
        assertThat(inventory.get().quantity()).isZero();
        assertThat(inventory.get().inStock()).isFalse();
    }

    @Test
    @DisplayName("Review service returns reviews independently")
    void reviewServiceReturnsData() {
        ReviewService service = new InMemoryReviewService();

        List<Review> reviews = service.findByProductId("p-1001");
        assertThat(reviews).hasSize(2);

        assertThat(service.findByProductId("p-1003")).isEmpty();
    }

    // =========================================================================
    // AGGREGATOR COMPOSITION
    // =========================================================================

    @Test
    @DisplayName("Aggregator composes responses from all three services")
    void aggregatorComposesResponses() {
        ProductDetail detail = aggregator.getProductDetail("p-1001");

        // Product service data
        assertThat(detail.id()).isEqualTo("p-1001");
        assertThat(detail.name()).isEqualTo("Laptop Pro");
        assertThat(detail.price()).isEqualTo("1299.99");

        // Inventory service data
        assertThat(detail.stockQuantity()).isEqualTo(15);
        assertThat(detail.inStock()).isTrue();

        // Review service data
        assertThat(detail.reviewCount()).isEqualTo(2);
        assertThat(detail.averageRating()).isEqualTo(4.5);
        assertThat(detail.reviews()).hasSize(2);
    }

    @Test
    @DisplayName("Aggregator handles out-of-stock products")
    void aggregatorHandlesOutOfStock() {
        ProductDetail detail = aggregator.getProductDetail("p-1002");

        assertThat(detail.name()).isEqualTo("Wireless Mouse");
        assertThat(detail.stockQuantity()).isZero();
        assertThat(detail.inStock()).isFalse();
    }

    @Test
    @DisplayName("Aggregator handles products with no reviews (zero rating)")
    void aggregatorHandlesNoReviews() {
        ProductDetail detail = aggregator.getProductDetail("p-1003");

        assertThat(detail.name()).isEqualTo("Mechanical Keyboard");
        assertThat(detail.reviewCount()).isZero();
        assertThat(detail.averageRating()).isZero();
    }

    @Test
    @DisplayName("Aggregator throws ProductNotFoundException for unknown products")
    void aggregatorThrowsForUnknownProduct() {
        assertThatThrownBy(() -> aggregator.getProductDetail("p-9999"))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessage("Product not found: p-9999");
    }

    @Test
    @DisplayName("Aggregator defaults missing inventory to out-of-stock")
    void aggregatorDefaultsMissingInventory() {
        // Product exists but no inventory entry
        ProductService productService = productId -> Optional.of(
                new Product("p-9998", "Special", "desc", "10.0"));
        InventoryService inventoryService = productId -> Optional.empty();
        ReviewService reviewService = productId -> List.of();

        ProductAggregator custom = new ProductAggregator(productService, inventoryService, reviewService);
        try {
            ProductDetail detail = custom.getProductDetail("p-9998");
            assertThat(detail.stockQuantity()).isZero();
            assertThat(detail.inStock()).isFalse();
        } finally {
            custom.shutdown();
        }
    }

    // =========================================================================
    // ERROR HANDLING
    // =========================================================================

    @Test
    @DisplayName("Aggregator throws ServiceUnavailableException on downstream timeout")
    void aggregatorThrowsOnTimeout() {
        ProductService productService = productId -> Optional.of(
                new Product("p-1", "n", "d", "1.0"));
        InventoryService inventoryService = productId -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return Optional.of(new Inventory("p-1", 1, true));
        };
        ReviewService reviewService = productId -> List.of();

        ProductAggregator slow = new ProductAggregator(
                productService, inventoryService, reviewService,
                Executors.newSingleThreadExecutor(), 1);
        try {
            assertThatThrownBy(() -> slow.getProductDetail("p-1"))
                    .isInstanceOf(ServiceUnavailableException.class)
                    .hasMessageContaining("InventoryService");
        } finally {
            slow.shutdown();
        }
    }

    @Test
    @DisplayName("Aggregator rejects null product ID")
    void aggregatorRejectsNullProductId() {
        assertThatThrownBy(() -> aggregator.getProductDetail(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Product ID must not be null");
    }

    // =========================================================================
    // CONCURRENCY
    // =========================================================================

    @Test
    @DisplayName("Aggregator handles 100 concurrent requests safely")
    void aggregatorHandlesConcurrentRequests() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger successCount = new AtomicInteger();

        for (int i = 0; i < threadCount; i++) {
            final int index = i;
            executor.submit(() -> {
                try {
                    String productId = "p-100" + (index % 3 + 1);
                    ProductDetail detail = aggregator.getProductDetail(productId);
                    assertThat(detail).isNotNull();
                    assertThat(detail.id()).isEqualTo(productId);
                    successCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        assertThat(latch.await(10, TimeUnit.SECONDS)).isTrue();
        executor.shutdown();
        assertThat(successCount.get()).isEqualTo(threadCount);
    }

    @Test
    @DisplayName("Aggregator returns consistent results under concurrent load")
    void aggregatorReturnsConsistentResults() throws InterruptedException {
        int threadCount = 50;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger mismatches = new AtomicInteger();

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    ProductDetail detail = aggregator.getProductDetail("p-1001");
                    if (!detail.name().equals("Laptop Pro")
                            || detail.stockQuantity() != 15
                            || detail.reviewCount() != 2) {
                        mismatches.incrementAndGet();
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        assertThat(latch.await(10, TimeUnit.SECONDS)).isTrue();
        executor.shutdown();
        assertThat(mismatches.get()).isZero();
    }

    // =========================================================================
    // END-TO-END
    // =========================================================================

    @Test
    @DisplayName("Demonstrate runs without throwing")
    void demonstrateRunsSuccessfully() {
        MicroservicesAggregatorApp.demonstrate();
    }
}