# Event Sourcing Pattern

## Overview

Instead of storing just the current state of an entity, Event Sourcing stores a sequence of **immutable domain events** that describe every state change. The current state is derived by **replaying** the event stream from the beginning. This gives a full audit trail, the ability to rebuild state at any point in time, and natural support for temporal queries.

This example models a simple **bank account** aggregate to demonstrate the full event sourcing flow.

## Structure

```
event-sourcing/
├── build.gradle.kts
├── README.md
└── src/
    ├── main/java/com/javastarterkit/patterns/eventsourcing/
    │   └── EventSourcing.java
    └── test/java/com/javastarterkit/patterns/eventsourcing/
        └── EventSourcingTest.java
```

## Implementation

The example is a single self-contained Java file with inner static classes/interfaces organized into four layers:

### Domain Events
| Event | Description |
|-------|-------------|
| `DomainEvent` | Sealed base interface with `aggregateId()`, `timestamp()`, `version()` |
| `AccountOpened` | Account created with owner and initial balance |
| `MoneyDeposited` | Money added to account |
| `MoneyWithdrawn` | Money removed from account |

### Event Store
| Component | Responsibility |
|-----------|---------------|
| `EventStore` | Append-only in-memory store; loads event streams by aggregate id, supports loading from a specific version |

### Aggregate
| Component | Responsibility |
|-----------|---------------|
| `AccountAggregate` | Applies events to build state; enforces business invariants (no negative balances); commands (`deposit`, `withdraw`) produce new events |
| `AccountAggregate.replay()` | Rebuilds state by replaying the full event stream |
| `AccountAggregate.fromSnapshot()` | Rebuilds state from a snapshot + subsequent events |

### Snapshot & Projection
| Component | Responsibility |
|-----------|---------------|
| `Snapshot` | Immutable pre-computed state at a specific version (optimizes loading long streams) |
| `AccountState` | Serializable state captured in a snapshot |
| `BalanceProjection` | Builds a query-optimized read model (total deposited, total withdrawn, current balance, transaction count) from events |

### Flow
1. Domain events are appended to the `EventStore` (append-only, immutable).
2. State is rebuilt by replaying the event stream through `AccountAggregate.replay()`.
3. Commands (`deposit`, `withdraw`) validate business invariants and produce new events.
4. A `Snapshot` can be taken to avoid replaying the entire stream on every load.
5. A `BalanceProjection` consumes events to build a query-optimized read model.

## Usage

```bash
# Build the pattern
./gradlew :system-design-pattern:architectural:event-sourcing:build

# Run the tests
./gradlew :system-design-pattern:architectural:event-sourcing:test
```

## Sample Output

```
=== Event Sourcing Pattern ===
Store state changes as a sequence of immutable events

Events appended to stream 'acc-001': 4

--- Replaying event stream to rebuild state ---
Rebuilt state: Account{id=acc-001, owner=Alice, balance=550, version=4}

--- Applying a new command ---
After withdrawal of 80: Account{id=acc-001, owner=Alice, balance=470, version=5}

--- Snapshot optimization ---
Snapshot taken at version 5: AccountState[id=acc-001, owner=Alice, balance=470]
State from snapshot + 0 replayed event(s): Account{id=acc-001, owner=Alice, balance=470, version=5}

--- Projection (read model from events) ---
Total deposited:  600
Total withdrawn:  130
Current balance:  470
Transaction count: 5

--- Full audit trail ---
  <timestamp> | AccountOpened | AccountOpened[aggregateId=acc-001, owner=Alice, initialBalance=100, ...]
  <timestamp> | MoneyDeposited | MoneyDeposited[aggregateId=acc-001, amount=200, ...]
  <timestamp> | MoneyWithdrawn | MoneyWithdrawn[aggregateId=acc-001, amount=50, ...]
  <timestamp> | MoneyDeposited | MoneyDeposited[aggregateId=acc-001, amount=300, ...]
  <timestamp> | MoneyWithdrawn | MoneyWithdrawn[aggregateId=acc-001, amount=80, ...]

Benefits:
- Complete audit trail of all state changes
- State can be rebuilt at any point in time by replaying events
- Snapshots optimize loading for long event streams
- Projections build query-optimized read models from events
- Events are immutable and append-only (no lost history)
```

## Benefits

- **Complete audit trail** — every state change is recorded as an immutable event.
- **Temporal queries** — state can be rebuilt at any point in time by replaying events up to a version.
- **Snapshots** — pre-computed state snapshots optimize loading for long event streams.
- **Projections** — multiple query-optimized read models can be built from the same event stream.
- **No lost history** — events are append-only and never modified or deleted.

## Trade-offs

- **Complexity** — requires event store, snapshot management, and projection infrastructure.
- **Eventual consistency** — projections may lag the event stream (in this synchronous example they are consistent immediately).
- **Storage growth** — event streams grow indefinitely; snapshots and archiving help mitigate this.
- **Schema evolution** — event schemas must remain backward-compatible as the system evolves.

## Category

Architectural

## Java Version

Java 25