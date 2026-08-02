# Layered Architecture Pattern

## Overview

**Layered Architecture** (also known as **N-tier architecture**) organizes the application into horizontal layers, each with a distinct responsibility. The most common arrangement is three layers:

1. **Presentation Layer** — handles user interaction (UI, REST, CLI)
2. **Business/Application Layer** — implements business rules and use cases
3. **Persistence/Data Layer** — manages data storage and retrieval

The key rule: **each layer depends only on the layer directly below it**. The presentation layer calls the business layer; the business layer calls the persistence layer. This creates a strict dependency direction that keeps the architecture predictable and testable.

This example models a simple **e-commerce order system** to demonstrate the full layered flow.

## Structure

```
layered-architecture/
├── build.gradle.kts
├── README.md
├── LLD.md
└── src/
    ├── main/java/com/javastarterkit/patterns/layeredarchitecture/
    │   ├── LayeredArchitecture.java          # Main entry point (wires layers)
    │   ├── models/                           # Domain layer
    │   │   ├── Money.java                    # Immutable value object (record)
    │   │   ├── OrderItem.java                # Immutable value object (record)
    │   │   └── Order.java                    # Entity with synchronized state
    │   ├── persistence/                      # Persistence layer
    │   │   ├── OrderRepository.java          # Interface (DIP)
    │   │   └── InMemoryOrderRepository.java  # ConcurrentHashMap implementation
    │   ├── business/                         # Business layer
    │   │   └── OrderService.java             # Stateless use-case orchestrator
    │   ├── presentation/                     # Presentation layer
    │   │   ├── OrderController.java          # Simulated REST adapter
    │   │   └── OrderConsole.java             # CLI adapter
    │   └── exception/                        # Exception hierarchy
    │       ├── LayeredArchitectureException.java
    │       ├── OrderNotFoundException.java
    │       └── OrderCancelledException.java
    └── test/java/com/javastarterkit/patterns/layeredarchitecture/
        └── LayeredArchitectureTest.java
```

## Implementation

### Domain Layer (Models)
| Component | Responsibility |
|-----------|---------------|
| `Money` | Immutable value object for non-negative amounts (uses `BigDecimal`) |
| `OrderItem` | A single line item (product, price, quantity) with a computed total |
| `Order` | Order entity with customer, items, total, and cancelled state; uses `synchronized` for thread-safe state transitions |

### Presentation Layer
| Component | Responsibility |
|-----------|---------------|
| `OrderController` | Simulated REST controller that translates HTTP-style requests into calls on `OrderService` |
| `OrderConsole` | Console/CLI adapter that translates user commands into calls on `OrderService` |

### Business Layer
| Component | Responsibility |
|-----------|---------------|
| `OrderService` | Implements use cases (`placeOrder`, `addItem`, `cancelOrder`, `getOrder`) and enforces business rules; depends only on `OrderRepository` |

### Persistence Layer
| Component | Responsibility |
|-----------|---------------|
| `OrderRepository` | Interface contract for how the business layer stores orders |
| `InMemoryOrderRepository` | Thread-safe in-memory implementation using `ConcurrentHashMap` |

### Flow
1. A **presentation adapter** (REST or Console) receives user input.
2. The presentation layer calls the **business layer** (`OrderService`).
3. `OrderService` enforces business rules and delegates persistence to the **persistence layer** (`OrderRepository`).
4. The persistence layer stores/retrieves `Order` objects.

## Thread-Safety Strategy

1. **Immutable Value Objects**: `Money` and `OrderItem` are immutable Java records — inherently thread-safe.
2. **Thread-Safe Repository**: `InMemoryOrderRepository` uses `ConcurrentHashMap` for lock-free concurrent reads and atomic writes.
3. **Synchronized Entity Methods**: `Order` uses `synchronized` blocks on mutable operations (`addItem`, `cancel`, `items()`, `total()`, `isCancelled()`) to ensure atomic state transitions.
4. **Stateless Service**: `OrderService` holds no mutable state — it delegates to the thread-safe repository. Can be safely shared across threads.
5. **Defensive Copies**: `Order.items()` returns `List.copyOf()` to prevent external mutation of internal state.

## Usage

```bash
# Build the pattern
./gradlew :design-patterns:system-design-pattern:architectural:layered-architecture:build

# Run the tests
./gradlew :design-patterns:system-design-pattern:architectural:layered-architecture:test
```

## Sample Output

```
=== Layered Architecture Pattern ===
Organize code into horizontal layers with strict dependencies

--- Presentation layer: REST controller ---
  [REST] POST /orders -> 201 Created: <uuid>
  [REST] POST /orders/<uuid>/items -> 200 OK: Order{id=<uuid>, customer=Alice, items=1, total=999.99, cancelled=false}
  [REST] POST /orders/<uuid>/items -> 200 OK: Order{id=<uuid>, customer=Alice, items=2, total=1059.97, cancelled=false}
  [REST] GET /orders/<uuid> -> 200 OK: Order{id=<uuid>, customer=Alice, items=2, total=1059.97, cancelled=false}

--- Presentation layer: Console ---
  [CLI] Placed order <uuid> for Bob
  [CLI] Added Keyboard x1 -> Order{id=<uuid>, customer=Bob, items=1, total=79.99, cancelled=false}
  [CLI] Order <uuid> for Bob | total=79.99 | cancelled=false

--- Business layer: cancel order ---
  [CLI] Order <uuid> for Bob | total=79.99 | cancelled=true

Benefits:
- Each layer has a single, well-defined responsibility
- Layers depend only on the layer directly below
- Presentation and persistence are swappable
- Easy to test each layer in isolation
```

## Benefits

- **Separation of concerns** — each layer has a single, well-defined responsibility.
- **Strict dependency direction** — layers depend only on the layer directly below, making the architecture predictable.
- **Swappable layers** — the presentation layer (REST vs Console) and persistence layer (InMemory vs JDBC) can be replaced without changing business logic.
- **Testability** — each layer can be tested in isolation by mocking the layer below.
- **Thread-safety** — immutable value objects, `ConcurrentHashMap` repository, and synchronized entity methods ensure safe concurrent access.
- **Familiarity** — the most widely understood and adopted architectural pattern.

## Trade-offs

- **Lazy architecture** — without discipline, layers can be bypassed (e.g., presentation calling persistence directly).
- **Sinkhole anti-pattern** — requests may pass through layers that add no value.
- **Monolithic tendency** — layers are typically deployed as a single unit.
- **Rigidity** — adding a new feature may require changes across all layers.

## Category

Architectural

## Java Version

Java 25