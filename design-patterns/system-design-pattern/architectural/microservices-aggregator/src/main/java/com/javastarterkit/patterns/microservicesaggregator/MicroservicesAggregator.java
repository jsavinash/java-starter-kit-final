package com.javastarterkit.patterns.microservicesaggregator;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Microservices Aggregator Pattern Example
 *
 * <p>The <b>Microservices Aggregator</b> pattern introduces an <b>aggregator
 * service</b> that composes responses from multiple microservices into a
 * single response for the client. Instead of the client making N separate
 * calls to N services, it makes one call to the aggregator, which fans out
 * to the underlying services, collects their responses, and combines them
 * into a unified payload.
 *
 * <p>This self-contained example models a <b>product detail page</b> that
 * aggregates data from three microservices:
 * <ul>
 *   <li><b>Product Service</b> — provides product name, description, price</li>
 *   <li><b>Inventory Service</b> — provides stock availability and quantity</li>
 *   <li><b>Review Service</b> — provides customer ratings and reviews</li>
 * </ul>
 *
 * <p>The <b>ProductAggregator</b> service calls all three microservices and
 * combines their responses into a single {@link ProductDetail} object that
 * the client can consume with one request.
 *
 * <p>Key benefit: the client makes one call instead of three, reducing
 * network round-trips and simplifying the client-side logic.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class MicroservicesAggregator {

    /**
     * Demonstrates the microservices aggregator pattern: the aggregator
     * composes responses from product, inventory, and review services into
     * a single product detail response.
     */
    public static void demonstrate() {
        System.out.println("\n=== Microservices Aggregator Pattern ===");
        System.out.println("Compose responses from multiple microservices into one\n");

        // --- Build the microservices -------------------------------------------
        ProductService productService = new ProductService();
        InventoryService inventoryService = new InventoryService();
        ReviewService reviewService = new ReviewService();

        // --- Build the aggregator ----------------------------------------------
        ProductAggregator aggregator = new ProductAggregator(productService, inventoryService, reviewService);

        // --- Client makes a single call to the aggregator ----------------------
        System.out.println("--- Client calls aggregator for product 'p-1001' ---");
        ProductDetail detail = aggregator.getProductDetail("p-1001");
        System.out.println(detail);

        System.out.println("\n--- Client calls aggregator for product 'p-1002' ---");
        ProductDetail detail2 = aggregator.getProductDetail("p-1002");
        System.out.println(detail2);

        System.out.println("\n--- Client calls aggregator for unknown product ---");
        try {
            aggregator.getProductDetail("p-9999");
        } catch (IllegalArgumentException e) {
            System.out.println("  Error: " + e.getMessage());
        }

        System.out.println("\nBenefits:");
        System.out.println("- Client makes one call instead of N calls");
        System.out.println("- Aggregator encapsulates fan-out and composition logic");
        System.out.println("- Microservices remain independent and focused");
        System.out.println("- Aggregator can handle partial failures gracefully");
    }

    public static void main(String[] args) {
        demonstrate();
    }

    // =========================================================================
    // MICROSERVICES — independent services, each with a single responsibility
    // =========================================================================

    /** Product service: provides product catalog data. */
    static final class ProductService {
        private final Map<String, Product> products = new LinkedHashMap<>();

        ProductService() {
            products.put("p-1001", new Product("p-1001", "Laptop Pro", "High-performance laptop", "1299.99"));
            products.put("p-1002", new Product("p-1002", "Wireless Mouse", "Ergonomic wireless mouse", "29.99"));
            products.put("p-1003", new Product("p-1003", "Mechanical Keyboard", "RGB mechanical keyboard", "89.99"));
        }

        Optional<Product> findById(String productId) {
            return Optional.ofNullable(products.get(productId));
        }
    }

    /** Inventory service: provides stock availability. */
    static final class InventoryService {
        private final Map<String, Inventory> stock = new LinkedHashMap<>();

        InventoryService() {
            stock.put("p-1001", new Inventory("p-1001", 15, true));
            stock.put("p-1002", new Inventory("p-1002", 0, false));
            stock.put("p-1003", new Inventory("p-1003", 42, true));
        }

        Optional<Inventory> findByProductId(String productId) {
            return Optional.ofNullable(stock.get(productId));
        }
    }

    /** Review service: provides customer ratings and reviews. */
    static final class ReviewService {
        private final Map<String, List<Review>> reviews = new LinkedHashMap<>();

        ReviewService() {
            reviews.put("p-1001", List.of(
                    new Review("Alice", 5, "Excellent laptop!"),
                    new Review("Bob", 4, "Great performance, a bit heavy")));
            reviews.put("p-1002", List.of(
                    new Review("Carol", 3, "Works fine, battery life is short")));
            reviews.put("p-1003", List.of());
        }

        List<Review> findByProductId(String productId) {
            return reviews.getOrDefault(productId, List.of());
        }
    }

    // =========================================================================
    // DATA OBJECTS — returned by each microservice
    // =========================================================================

    /** Product data from the product service. */
    record Product(String id, String name, String description, String price) {
    }

    /** Inventory data from the inventory service. */
    record Inventory(String productId, int quantity, boolean inStock) {
    }

    /** Review data from the review service. */
    record Review(String author, int rating, String comment) {
    }

    // =========================================================================
    // AGGREGATOR — composes responses from multiple microservices
    // =========================================================================

    /**
     * The aggregator service. It calls the product, inventory, and review
     * services and combines their responses into a single {@link ProductDetail}.
     * The client only needs to make one call to this service.
     */
    static final class ProductAggregator {
        private final ProductService productService;
        private final InventoryService inventoryService;
        private final ReviewService reviewService;

        ProductAggregator(ProductService productService, InventoryService inventoryService,
                ReviewService reviewService) {
            this.productService = productService;
            this.inventoryService = inventoryService;
            this.reviewService = reviewService;
        }

        /**
         * Aggregates product, inventory, and review data into a single detail.
         * If the product does not exist, throws an exception.
         */
        ProductDetail getProductDetail(String productId) {
            // Call the product service
            Product product = productService.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));
            System.out.println("  [AGG] Fetched product from ProductService: " + product.name());

            // Call the inventory service
            Inventory inventory = inventoryService.findByProductId(productId)
                    .orElse(new Inventory(productId, 0, false));
            System.out.println("  [AGG] Fetched inventory from InventoryService: "
                    + (inventory.inStock() ? "in stock (" + inventory.quantity() + ")" : "out of stock"));

            // Call the review service
            List<Review> reviews = reviewService.findByProductId(productId);
            System.out.println("  [AGG] Fetched " + reviews.size() + " review(s) from ReviewService");

            // Compose the unified response
            double avgRating = reviews.isEmpty()
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
                    avgRating,
                    reviews);
        }
    }

    /** The unified response returned to the client. */
    record ProductDetail(
            String id,
            String name,
            String description,
            String price,
            int stockQuantity,
            boolean inStock,
            int reviewCount,
            double averageRating,
            List<Review> reviews) {

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("  ProductDetail{\n");
            sb.append("    id=").append(id).append('\n');
            sb.append("    name=").append(name).append('\n');
            sb.append("    description=").append(description).append('\n');
            sb.append("    price=").append(price).append('\n');
            sb.append("    stockQuantity=").append(stockQuantity).append('\n');
            sb.append("    inStock=").append(inStock).append('\n');
            sb.append("    reviewCount=").append(reviewCount).append('\n');
            sb.append("    averageRating=").append(String.format("%.1f", averageRating)).append('\n');
            sb.append("    reviews=").append(reviews).append('\n');
            sb.append("  }");
            return sb.toString();
        }
    }
}