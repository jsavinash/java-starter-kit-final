# Microservices Aggregator Pattern

## Overview

The **Microservices Aggregator** pattern introduces an **aggregator service** that composes responses from multiple microservices into a single response for the client. Instead of the client making N separate calls to N services, it makes one call to the aggregator, which fans out to the underlying services, collects their responses, and combines them into a unified payload.

This example models a **product detail page** that aggregates data from three microservices: Product, Inventory, and Review services.

## Structure

```
microservices-aggregator/
├── build.gradle.kts
├── README.md
└── src/
    ├── main/java/com/javastarterkit/patterns/microservicesaggregator/
    │   └── MicroservicesAggregator.java
    └── test/java/com/javastarterkit/patterns/microservicesaggregator/
        └── MicroservicesAggregatorTest.java
```

## Implementation

The example is a single self-contained Java file organized into three parts:

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
| `ProductAggregator` | Calls all three microservices and composes their responses into a single `ProductDetail`; computes average rating from reviews; handles missing inventory gracefully |

### Flow
1. The client makes **one call** to the aggregator for a product ID.
2. The aggregator calls the **Product Service** (fails fast if the product doesn't exist).
3. The aggregator calls the **Inventory Service** (defaults to out-of-stock if missing).
4. The aggregator calls the **Review Service** (returns empty list if none).
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
  [AGG] Fetched product from ProductService: Laptop Pro
  [AGG] Fetched inventory from InventoryService: in stock (15)
  [AGG] Fetched 2 review(s) from ReviewService
  ProductDetail{
    id=p-1001
    name=Laptop Pro
    description=High-performance laptop
    price=1299.99
    stockQuantity=15
    inStock=true
    reviewCount=2
    averageRating=4.5
    reviews=[Review[author=Alice, rating=5, comment=Excellent laptop!], Review[author=Bob, rating=4, comment=Great performance, a bit heavy]]
  }

--- Client calls aggregator for product 'p-1002' ---
  [AGG] Fetched product from ProductService: Wireless Mouse
  [AGG] Fetched inventory from InventoryService: out of stock
  [AGG] Fetched 1 review(s) from ReviewService
  ProductDetail{
    ...
  }

--- Client calls aggregator for unknown product ---
  Error: Product not found: p-9999

Benefits:
- Client makes one call instead of N calls
- Aggregator encapsulates fan-out and composition logic
- Microservices remain independent and focused
- Aggregator can handle partial failures gracefully
```

## Benefits

- **Single client call** — the client makes one request instead of N, reducing network round-trips and simplifying client logic.
- **Separation of concerns** — each microservice remains focused on a single responsibility.
- **Composition logic centralized** — the aggregator encapsulates fan-out, data merging, and error handling.
- **Graceful degradation** — the aggregator can default missing data (e.g., inventory) instead of failing the entire request.
- **Independent evolution** — microservices can be scaled and deployed independently.

## Trade-offs

- **Increased latency** — the aggregator introduces an extra network hop for the client.
- **Coupling** — the aggregator is coupled to the contracts of all downstream services.
- **Single point of failure** — if the aggregator fails, the entire product detail page is unavailable.
- **N+1 problem** — the aggregator must call N services sequentially unless it uses parallel calls, increasing total latency.

## Category

Architectural

## Java Version

Java 25