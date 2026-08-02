# Event Sourcing Pattern

## Overview

Instead of storing just the current state of an entity, Event Sourcing stores a sequence of **immutable domain events** that describe every state change. The current state is derived by **replaying** the event stream from the beginning. This gives a full audit trail, the ability to rebuild state at any point in time, and natural support for temporal queries.

This example models a **bank account** aggregate with a production-grade, thread-safe implementation that demonstrates:
- Append-only event store with **optimistic concurrency control**
- Sealed event hierarchy with exhaustive pattern matching
- Per-aggregate **pessimistic locking** (`ReentrantLock`) for the write path
- **Snapshots** for optimized rehydration of long streams
- **Projections** (read models) built from the event stream

## Structure

```
event-sourcing/
├── build.gradle.kts
├── LLD.md
├── README.md
└── src/
    ├── main/java/com/javastarterkit/patterns/eventsourcing/
    │   ├── Main.java
    │   ├── domain/
    │   │   ├── event/
    │   │   │   ├── DomainEvent.java
    │   │   │   ├── AccountOpened.java
    │   │   │   ├── MoneyDeposited.java
    │   │   │   ├── MoneyWithdrawn.java
    │   │   │   └── AccountClosed.java
    │   │   └── model/
    │   │       └── AccountAggregate.java
    │   ├── infrastructure/
    │   │   ├── EventStore.java
    │   │   ├── InMemoryEventStore.java
    │   │   ├── PerAggregateLock.java
    │   │   ├── SnapshotStore.java
    │   │   ├── Snapshot.java
    │   │   └── AccountState.java
    │   ├── application/
    │   │   ├── service/
    │   │   │   └── AccountService.java
    │   │   └── projection/
    │   │       └── BalanceProjection.java
    │   └── exception/
    │       ├── DomainException.java
    │       ├── OptimisticLockException.java
    │       └── AggregateNotFoundException.java
    └── test/java/com/javastarterkit/patterns/eventsourcing/
        ├── EventSourcingTest.java
        └── EventSourcingConcurrencyTest.java
```

## Key Components

### Domain Events
| Event | Description |
|-------|-------------|
| `DomainEvent` | Sealed base interface with `aggregateId()`, `timestamp()`, `version()` |
| `AccountOpened` | Account created with owner and initial balance |
| `MoneyDeposited` | Money added to account |
| `MoneyWithdrawn` | Money removed from account |
| `AccountClosed` | Account closed — no further operations permitted |

### Infrastructure
| Component | Responsibility |
|-----------|---------------|
| `EventStore` | Interface defining the append-only event store contract |
| `InMemoryEventStore` | Thread-safe implementation using `ConcurrentHashMap` + `compute()` with optimistic version checking |
| `PerAggregateLock` | Per-aggregate `ReentrantLock` registry for pessimistic write-side serialization |
| `SnapshotStore` | Thread-safe store for pre-computed aggregate snapshots |
| `Snapshot` / `AccountState` | Immutable snapshot data structures for optimized rehydration |

### Domain Model
| Component | Responsibility |
|-----------|---------------|
| `AccountAggregate` | Aggregate root; applies events to build state, enforces business invariants, emits new events on commands |

### Application
| Component | Responsibility |
|-----------|---------------|
| `AccountService` | Coordinates the load-mutate-save cycle under per-aggregate locks; persists events with optimistic version control |
| `BalanceProjection` | Builds a query-optimized read model (total deposited/withdrawn, current balance, transaction count) |

## Concurrency Strategy

| Construct | Where Used | Purpose |
|---|---|---|
| `ConcurrentHashMap<String, List<DomainEvent>>` | `InMemoryEventStore.streams` | Lock-free concurrent access; `compute()` provides atomic read-modify-write |
| `ReentrantLock` (per aggregate) | `PerAggregateLock.lockFor()` | Serializes the load-mutate-save cycle for the same aggregate |
| `compute()` / `computeIfAbsent()` | Event store / lock registry | Atomic operations without external locks |
| `OptimisticLockException` | `InMemoryEventStore.append()` | Detects version conflicts from concurrent writers |
| `Collections.unmodifiableList(new ArrayList<>())` | `InMemoryEventStore.load()` | Defensive copies for readers |
| Immutable `record` events | `domain/event/*.java` | Events are inherently immutable (append-only history) |

## Flow

1. A client invokes a command on `AccountService` (e.g., `withdraw("acc-001", 80)`).
2. `AccountService` acquires a per-aggregate `ReentrantLock` to serialize access.
3. The aggregate is loaded by replaying events (or from a snapshot + subsequent events).
4. The command validates business invariants and emits new domain events.
5. The new events are appended to the `EventStore` atomically with the expected version (optimistic concurrency).
6. The lock is released.
7. A `BalanceProjection` can independently rebuild a read model by consuming the event stream.

## Usage

```bash
# Build the pattern
./gradlew :design-patterns:system-design-pattern:architectural:event-sourcing:build

# Run the application
./gradlew :design-patterns:system-design-pattern:architectural:event-sourcing:run

# Run tests
./gradlew :design-patterns:system-design-pattern:architectural:event-sourcing:test
```

## Sample Output

```
=== Event Sourcing Pattern ===
Store state changes as a sequence of immutable events

Opened account: Account{id=acc-001, owner=Alice, balance=100, closed=false, version=1}
After transactions: Account{id=acc-001, owner=Alice, balance=550, closed=false, version=4}
After withdrawal of 80: Account{id=acc-001, owner=Alice, balance=470, closed=false, version=5}
Snapshot taken at version 5
Rehydrated from snapshot: Account{id=acc-001, owner=Alice, balance=470, closed=false, version=5}

--- Projection (read model from events) ---
Total deposited:   600
Total withdrawn:   130
Current balance:   470
Transaction count: 5

--- Full audit trail ---
  v1 | AccountOpened  | AccountOpened[aggregateId=acc-001, owner=Alice, initialBalance=100, ...]
  v2 | MoneyDeposited | MoneyDeposited[aggregateId=acc-001, amount=200, ...]
  v3 | MoneyWithdrawn | MoneyWithdrawn[aggregateId=acc-001, amount=50, ...]
  v4 | MoneyDeposited | MoneyDeposited[aggregateId=acc-001, amount=300, ...]
  v5 | MoneyWithdrawn | MoneyWithdrawn[aggregateId=acc-001, amount=80, ...]

After close: Account{id=acc-001, owner=Alice, balance=470, closed=true, version=6}
Expected failure after close: Cannot withdraw from a closed account: acc-001
```

## Benefits

- **Complete audit trail** — every state change is recorded as an immutable event.
- **Temporal queries** — state can be rebuilt at any point in time by replaying events up to a version.
- **Snapshots** — pre-computed state snapshots optimize loading for long event streams.
- **Projections** — multiple query-optimized read models can be built from the same event stream.
- **No lost history** — events are append-only and never modified or deleted.
- **Concurrency-safe** — per-aggregate locking + optimistic version control prevent lost updates.

## Trade-offs

- **Complexity** — requires event store, snapshot management, and projection infrastructure.
- **Eventual consistency** — projections may lag the event stream (in this synchronous example they are consistent immediately).
- **Storage growth** — event streams grow indefinitely; snapshots and archiving help mitigate this.
- **Schema evolution** — event schemas must remain backward-compatible as the system evolves.

## Category

Architectural

## Java Version

Java 25