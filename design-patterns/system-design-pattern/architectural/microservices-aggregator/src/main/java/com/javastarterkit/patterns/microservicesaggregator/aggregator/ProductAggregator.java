package com.javastarterkit.patterns.microservicesaggregator.aggregator;

import com.javastarterkit.patterns.microservicesaggregator.exception.ProductNotFoundException;
import com.javastarterkit.patterns.microservicesaggregator.exception.ServiceUnavailableException;
import com.javastarterkit.patterns.microservicesaggregator.models.Inventory;
import com.javastarterkit.patterns.microservicesaggregator.models.Product;
import com.javastarterkit.patterns.microservicesaggregator.models.ProductDetail;
import com.javastarterkit.patterns.microservicesaggregator.models.Review;
import com.javastarterkit.patterns.microservicesaggregator.services.InventoryService;
import com.javastarterkit.patterns.microservicesaggregator.services.ProductService;
import com.javastarterkit.patterns.microservicesaggregator.services.ReviewService;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * The aggregator service that composes responses from multiple microservices.
 *
 * <p>This is the core of the Microservices Aggregator pattern. The client
 * makes a single call to {@link #getProductDetail(String)}, and the aggregator
 * fans out to the product, inventory, and review services in parallel using
 * {@link CompletableFuture}, collects their responses, and composes a unified
 * {@link ProductDetail} payload.
 *
 * <p><b>Thread-Safety Strategy:</b>
 * <ul>
 *   <li>The aggregator is immutable after construction (all fields are final).</li>
 *   <li>Downstream calls are executed in parallel via a shared {@link ExecutorService}.</li>
 *   <li>Each downstream call is isolated in its own {@link CompletableFuture}.</li>
 *   <li>All returned data objects are immutable records.</li>
 * </ul>
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public final class ProductAggregator {

    private static final int DEFAULT_TIMEOUT_SECONDS = 5;
    private static final int DEFAULT_THREAD_POOL_SIZE = 4;

    private final ProductService productService;
    private final InventoryService inventoryService;
    private final ReviewService reviewService;
    private final ExecutorService executorService;
    private final long timeoutSeconds;

    /**
     * Constructs the aggregator with the three downstream microservices.
     *
     * @param productService   the product microservice
     * @param inventoryService the inventory microservice
     * @param reviewService    the review microservice
     */
    public ProductAggregator(ProductService productService,
                             InventoryService inventoryService,
                             ReviewService reviewService) {
        this(productService, inventoryService, reviewService,
                Executors.newFixedThreadPool(DEFAULT_THREAD_POOL_SIZE),
                DEFAULT_TIMEOUT_SECONDS);
    }

    /**
     * Constructs the aggregator with explicit executor and timeout.
     *
     * @param productService   the product microservice
     * @param inventoryService the inventory microservice
     * @param reviewService    the review microservice
     * @param executorService  the executor for parallel fan-out
     * @param timeoutSeconds   the timeout for downstream calls
     */
    public ProductAggregator(ProductService productService,
                             InventoryService inventoryService,
                             ReviewService reviewService,
                             ExecutorService executorService,
                             long timeoutSeconds) {
        this.productService = Objects.requireNonNull(productService, "ProductService must not be null");
        this.inventoryService = Objects.requireNonNull(inventoryService, "InventoryService must not be null");
        this.reviewService = Objects.requireNonNull(reviewService, "ReviewService must not be null");
        this.executorService = Objects.requireNonNull(executorService, "ExecutorService must not be null");
        if (timeoutSeconds <= 0) {
            throw new IllegalArgumentException("Timeout must be positive");
        }
        this.timeoutSeconds = timeoutSeconds;
    }

    /**
     * Aggregates product, inventory, and review data into a single detail.
     *
     * <p>The product service is called first (fail-fast if the product does
     * not exist). Inventory and review services are called in parallel.
     *
     * @param productId the product identifier
     * @return the composed {@link ProductDetail}
     * @throws ProductNotFoundException if the product does not exist
     * @throws ServiceUnavailableException if a downstream service times out
     */
    public ProductDetail getProductDetail(String productId) {
        Objects.requireNonNull(productId, "Product ID must not be null");

        // Step 1: Fetch product (fail-fast if not found)
        Product product = fetchProduct(productId);

        // Step 2: Fan out to inventory and review services in parallel
        CompletableFuture<Inventory> inventoryFuture = CompletableFuture
                .supplyAsync(() -> fetchInventory(productId), executorService);
        CompletableFuture<List<Review>> reviewsFuture = CompletableFuture
                .supplyAsync(() -> fetchReviews(productId), executorService);

        // Step 3: Wait for both with timeout
        Inventory inventory = await(inventoryFuture, "InventoryService");
        List<Review> reviews = await(reviewsFuture, "ReviewService");

        // Step 4: Compose the unified response
        return compose(product, inventory, reviews);
    }

    /**
     * Shuts down the internal executor service.
     */
    public void shutdown() {
        executorService.shutdown();
    }

    private Product fetchProduct(String productId) {
        return productService.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found: " + productId));
    }

    private Inventory fetchInventory(String productId) {
        return inventoryService.findByProductId(productId)
                .orElse(new Inventory(productId, 0, false));
    }

    private List<Review> fetchReviews(String productId) {
        return reviewService.findByProductId(productId);
    }

    private <T> T await(CompletableFuture<T> future, String serviceName) {
        try {
            return future.get(timeoutSeconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new ServiceUnavailableException(
                    "Downstream service timed out: " + serviceName, e);
        }
    }

    private ProductDetail compose(Product product, Inventory inventory, List<Review> reviews) {
        double averageRating = reviews.isEmpty()
                ? 0.0
                : reviews.stream().mapToInt(Review::rating).average().orElse(0.0);

        return new ProductDetail(
                product.id(),
                product.name(),
                product.description(),
                product.price(),
                inventory.quantity(),
                inventory.inStock(),
                reviews.size(),
                averageRating,
                reviews);
    }
}