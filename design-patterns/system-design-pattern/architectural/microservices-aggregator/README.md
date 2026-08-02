# Microservices Aggregator Pattern

## Overview

The **Microservices Aggregator** pattern introduces an **aggregator service** that composes responses from multiple microservices into a single response for the client. Instead of the client making N separate calls to N services, it makes one call to the aggregator, which fans out to the underlying services in parallel, collects their responses, and combines them into a unified payload.

This example models a **product detail page** that aggregates data from three microservices: Product, Inventory, and Review services.

## Structure

```
microservices-aggregator/
├── build.gradle.kts
├── README.md
├── LLD.md
└── src/
    ├── main/java/com/javastarterkit/patterns/microservicesaggregator/
    │   ├── MicroservicesAggregatorApp.java      # Main entry point
    │   ├── aggregator/
    │   │   └── ProductAggregator.java           # Core aggregator (parallel fan-out)
    │   ├── models/                              # Immutable domain records
    │   │   ├── Product.java
    │   │   ├── Inventory.java
    │   │   ├── Review.java
    │   │   └── ProductDetail.java
    │   ├── services/                            # Microservice contracts (DIP)
    │   │   ├── ProductService.java
    │   │   ├── InventoryService.java
    │   │   ├── ReviewService.java
    │   │   └── impl/
    │   │       ├── InMemoryProductService.java
    │   │       ├── InMemoryInventoryService.java
    │   │       └── InMemoryReviewService.java
    │   └── exception/                           # Exception hierarchy
    │       ├── AggregatorException.java
    │       ├── ProductNotFoundException.java
    │       └── ServiceUnavailableException.java
    └── test/java/com/javastarterkit/patterns/microservicesaggregator/
        └── MicroservicesAggregatorAppTest.java
```

## Implementation

### Microservices
| Service | Responsibility |
|---------|---------------|
| `ProductService` | Provides product catalog data: id, name, description, price |
| `InventoryService` | Provides stock availability: quantity, in-stock flag |
| `ReviewService` | Provides customer ratings and reviews |

### Data Objects
| Component | Responsibility |
|-----------|---------------|
| `Product` | Product data returned by the product service |
| `Inventory` | Stock data returned by the inventory service |
| `Review` | Review data returned by the review service |
| `ProductDetail` | The unified response composed by the aggregator and returned to the client |

### Aggregator
| Component | Responsibility |
|-----------|---------------|
| `ProductAggregator` | Calls all three microservices in parallel using `CompletableFuture`; composes their responses into a single `ProductDetail`; computes average rating; handles missing inventory gracefully; enforces timeout protection |

### Flow
1. The client makes **one call** to the aggregator for a product ID.
2. The aggregator calls the **Product Service** (fails fast if the product doesn't exist).
3. The aggregator fans out to the **Inventory Service** and **Review Service** in parallel.
4. The aggregator waits for both with a configurable timeout.
5. The aggregator composes the unified `ProductDetail` response.

## Usage

```bash
# Build the pattern
./gradlew :system-design-pattern:architectural:microservices-aggregator:build

# Run the tests
./gradlew :system-design-pattern:architectural:microservices-aggregator:test
```

## Sample Output

```
=== Microservices Aggregator Pattern ===
Compose responses from multiple microservices into one

--- Client calls aggregator for product 'p-1001' ---
ProductDetail{id='p-1001', name='Laptop Pro', description='High-performance laptop', price='1299.99', stockQuantity=15, inStock=true, reviewCount=2, averageRating=4.5, reviews=[Review[author=Alice, rating=5, comment=Excellent laptop!], Review[author=Bob, rating=4, comment=Great performance, a bit heavy]]}

--- Client calls aggregator for product 'p-1002' ---
ProductDetail{id='p-1002', name='Wireless Mouse', description='Ergonomic wireless mouse', price='29.99', stockQuantity=0, inStock=false, reviewCount=1, averageRating=3.0, reviews=[Review[author=Carol, rating=3, comment=Works fine, battery life is short]]}

--- Client calls aggregator for product 'p-1003' ---
ProductDetail{id='p-1003', name='Mechanical Keyboard', description='RGB mechanical keyboard', price='89.99', stockQuantity=42, inStock=true, reviewCount=0, averageRating=0.0, reviews=[]}

--- Client calls aggregator for unknown product ---
  Error: Product not found: p-9999

Benefits:
- Client makes one call instead of N calls
- Aggregator fans out to services in parallel (CompletableFuture)
- Microservices remain independent and focused
- Aggregator handles partial failures gracefully
```

## Benefits

- **Single client call** — the client makes one request instead of N, reducing network round-trips and simplifying client logic.
- **Parallel fan-out** — the aggregator invokes downstream services concurrently using `CompletableFuture`, minimizing total latency.
- **Separation of concerns** — each microservice remains focused on a single responsibility.
- **Composition logic centralized** — the aggregator encapsulates fan-out, data merging, and error handling.
- **Graceful degradation** — the aggregator can default missing data (e.g., inventory) instead of failing the entire request.
- **Timeout protection** — downstream calls are bounded by a configurable timeout, preventing indefinite hangs.
- **Independent evolution** — microservices can be scaled and deployed independently.

## Trade-offs

- **Increased latency** — the aggregator introduces an extra network hop for the client.
- **Coupling** — the aggregator is coupled to the contracts of all downstream services.
- **Single point of failure** — if the aggregator fails, the entire product detail page is unavailable.
- **N+1 problem** — the aggregator must call N services; parallel fan-out mitigates but does not eliminate this.

## Category

Architectural

## Java Version

Java 25