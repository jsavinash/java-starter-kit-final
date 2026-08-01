# Command Query Responsibility Segregation (CQRS) Pattern

## Overview

CQRS separates the model used to **update** state (the *command* / *write* side) from the model used to **read** state (the *query* / *read* side). Instead of a single model serving both reads and writes, each side is optimized independently:

- The **command side** enforces business invariants and emits domain events.
- The **query side** maintains a denormalized, query-optimized read model.
- A **projection** subscribes to domain events and updates the read model, keeping the two sides synchronized.

This example models a simple **bank account** aggregate to demonstrate the full CQRS flow.

## Structure

```
command-query-responsibility-segregation/
├── build.gradle.kts
├── README.md
└── src/
    ├── main/java/com/javastarterkit/patterns/commandqueryresponsibilitysegregation/
    │   └── CommandQueryResponsibilitySegregation.java
    └── test/java/com/javastarterkit/patterns/commandqueryresponsibilitysegregation/
        └── CommandQueryResponsibilitySegregationTest.java
```

## Implementation

The example is a single self-contained Java file with inner static classes/interfaces organized into three layers:

### Command Side (Write Model)
| Component | Responsibility |
|-----------|---------------|
| `AccountAggregate` | Enforces business rules (no negative balances, positive amounts) and emits domain events |
| `AccountWriteStore` | Persists the event stream per aggregate (in-memory) |
| `AccountRepository` | Loads aggregates by replaying events; saves new events and publishes them |
| `Command` / `CommandHandler` | Marker interface and handler contract for `OpenAccount`, `DepositMoney`, `WithdrawMoney` |
| `CommandBus` | Routes commands to their registered handlers |

### Query Side (Read Model)
| Component | Responsibility |
|-----------|---------------|
| `AccountView` | Denormalized read DTO optimized for fast lookups |
| `AccountReadModel` | In-memory key-value store for O(1) account lookups |
| `Query` / `QueryHandler` | Marker interface and handler contract for `FindAccountById`, `ListAllAccounts`, `CountAccounts` |
| `QueryBus` | Routes queries to their registered handlers |

### Synchronization
| Component | Responsibility |
|-----------|---------------|
| `DomainEvent` | Base type for `AccountOpened`, `MoneyDeposited`, `MoneyWithdrawn` |
| `EventBus` | In-process pub/sub connecting the command side to the read side |
| `AccountProjection` | Subscribes to events and updates the `AccountReadModel` |

### Flow
1. A command is dispatched via the `CommandBus`.
2. The handler loads the aggregate, performs the mutation, and persists new events.
3. The `AccountRepository` publishes each new event to the `EventBus`.
4. The `AccountProjection` receives the event and updates the `AccountReadModel`.
5. A query dispatched via the `QueryBus` reads from the now-updated read model.

## Usage

```bash
# Build the pattern
./gradlew :system-design-pattern:architectural:command-query-responsibility-segregation:build

# Run the tests
./gradlew :system-design-pattern:architectural:command-query-responsibility-segregation:test

# Run the pattern example
./gradlew :system-design-pattern:architectural:command-query-responsibility-segregation:run
```

## Sample Output

```
=== Command Query Responsibility Segregation (CQRS) Pattern ===
Separates write (command) and read (query) models for independent scaling and optimization

--- COMMAND SIDE (writes) ---
  [CMD] OpenAccount owner=Alice initialBalance=100 -> id=40a7a2ae
  [CMD] OpenAccount owner=Bob initialBalance=50 -> id=a1ff8964
  [CMD] DepositMoney id=40a7a2ae amount=200 -> balance=300
  [CMD] WithdrawMoney id=a1ff8964 amount=20 -> balance=30
  [CMD] DepositMoney id=a1ff8964 amount=70 -> balance=100

--- QUERY SIDE (reads) ---
Find Alice by id: Optional[AccountView{id=40a7a2ae, owner=Alice, balance=300}]
Find Bob by id:   Optional[AccountView{id=a1ff8964, owner=Bob, balance=100}]
All accounts:     [AccountView{id=40a7a2ae, owner=Alice, balance=300}, AccountView{id=a1ff8964, owner=Bob, balance=100}]
Account count:    2

Benefits:
- Write model is optimized for business rules & validation
- Read model is optimized for queries (denormalized, fast lookups)
- Read and write sides can scale independently
- Read model can be rebuilt from the event stream at any time
```

## Benefits

- **Independent optimization** — the write model is optimized for business rules; the read model is optimized for queries.
- **Independent scaling** — read and write sides can scale separately to match their load profiles.
- **Rebuildable read models** — the read model can always be rebuilt by replaying the event stream.
- **Clear separation of concerns** — commands and queries have distinct, focused interfaces.

## Trade-offs

- **Increased complexity** — two models and synchronization infrastructure must be maintained.
- **Eventual consistency** — the read model may lag the write model (in this synchronous example they are consistent immediately).
- **Learning curve** — teams must understand event-driven synchronization and projections.

## Category

Architectural

## Java Version

Java 25