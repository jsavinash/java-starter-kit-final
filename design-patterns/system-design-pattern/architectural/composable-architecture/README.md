# Composable Architecture Pattern

## Overview

Composable Architecture structures an application around small, independent **components**, each owning its own **State**, **Action**s, and a **Reducer** that describes how state changes in response to actions. Components are composed hierarchically — a parent reducer pulls state out of global state, delegates actions to child reducers, and combines the results — so complex features are built by assembling simple, testable pieces.

The core building blocks (inspired by Point-Free's "The Composable Architecture") are:

- **State** — an immutable record describing the data a feature owns
- **Action** — a sealed interface of all possible user/system events
- **Reducer** — a pure function `(State, Action) -> State`
- **Store** — a thread-safe holder of the current state that dispatches actions through the reducer
- **Component** — bundles a state slice, action subset, and reducer so it can be composed

This example models a **pizza-configuration** feature and a **delivery-details** feature, then composes them into a single **order** feature to demonstrate how independent components are combined without coupling.

## Structure

```
composable-architecture/
├── build.gradle.kts
├── README.md
├── LLD.md
└── src/
    ├── main/java/com/javastarterkit/patterns/composablearchitecture/
    │   ├── Main.java                                  # Application entry point
    │   ├── core/
    │   │   ├── State.java                             # Marker interface
    │   │   ├── Action.java                            # Marker interface
    │   │   ├── Reducer.java                           # Pure function + combine/pullback
    │   │   ├── component/Component.java               # Feature bundle (state+reducer)
    │   │   └── store/Store.java                       # Thread-safe runtime engine
    │   ├── exception/
    │   │   ├── ComposableArchitectureException.java   # Base runtime exception
    │   │   ├── InvalidPizzaException.java             # Pizza validation error
    │   │   └── InvalidDeliveryException.java          # Delivery validation error
    │   ├── ui/
    │   │   ├── models/
    │   │   │   ├── PizzaSize.java                     # Enum of pizza sizes
    │   │   │   ├── Topping.java                       # Enum of toppings
    │   │   │   ├── PizzaState.java                    # Immutable pizza state
    │   │   │   ├── DeliveryState.java                 # Immutable delivery state
    │   │   │   └── OrderState.java                    # Composed root state
    │   │   ├── actions/
    │   │   │   ├── PizzaAction.java                   # Sealed pizza actions
    │   │   │   ├── DeliveryAction.java                # Sealed delivery actions
    │   │   │   └── OrderAction.java                   # Sealed parent action wrapper
    │   │   └── reducers/
    │   │       ├── PizzaReducer.java                  # Pizza feature reducer (enum)
    │   │       ├── DeliveryReducer.java               # Delivery feature reducer (enum)
    │   │       └── OrderReducer.java                  # Composed parent reducer
    │   └── composition/
    │       └── OrderComposer.java                     # Store factory / wiring
    └── test/java/com/javastarterkit/patterns/composablearchitecture/
        ├── PizzaReducerTest.java
        ├── DeliveryReducerTest.java
        ├── OrderCompositionTest.java
        └── StoreConcurrencyTest.java
```

## Implementation

### Core Abstractions

| Component | Responsibility |
|-----------|---------------|
| `State` | Marker interface for immutable state records |
| `Action` | Marker interface for sealed action hierarchies |
| `Reducer<S, A>` | Pure function `(State, Action) -> State`; provides `combine` and `pullback` composition operators |
| `Store<S, A>` | Thread-safe holder of current state; dispatches actions through the reducer and notifies subscribers |
| `Component<S, A>` | Bundles initial-state factory + state type + reducer for composition |

### Feature Components

| Feature | State | Actions | Reducer |
|---------|-------|---------|---------|
| Pizza | `PizzaState(size, toppings, quantity)` | `selectSize`, `toggleTopping`, `setQuantity` | `PizzaReducer` (enum) |
| Delivery | `DeliveryState(name, address, city, phone)` | `setName`, `setAddress`, `setCity`, `setPhone` | `DeliveryReducer` (enum) |
| Order (composed) | `OrderState(pizza, delivery)` | `Pizza(action)`, `Delivery(action)` | `OrderReducer.composed()` via `pullback` + `combine` |

### Composition Operators

- **`Reducer.combine(first, second)`** — chains two reducers over the same state/action space; the second sees the state produced by the first.
- **`Reducer.pullback(component, extract, inject, mapAction)`** — adapts a child reducer to a parent state/action space: extracts the child state slice, runs the child reducer, and injects the result back. This is the key operator that lets independent features be combined without coupling.

### Concurrency / Thread-Safety

The `Store` is built for multi-producer, multi-consumer usage:

- **Immutable state** — every state is a value object; no in-place mutation, so any thread can read a snapshot without locks.
- **`ReentrantLock`** — serializes the read-modify-write cycle of `dispatch`; reentrancy prevents deadlock on nested dispatch.
- **`CopyOnWriteArrayList`** — subscriber lists are copied on modification, so notification delivery never blocks.
- **`AtomicLong`** — monotonic revision counter for change-detection and caching.
- **Enum singletons** — reducers are stateless enums (`INSTANCE`), safe to share across threads and stores.

### Flow

1. Build independent feature `Component`s (Pizza, Delivery).
2. Compose them into a parent `OrderState`/`OrderAction` reducer using `pullback` and `combine`.
3. Create a `Store` with the composed reducer and initial state.
4. Dispatch actions — the store runs the reducer, which routes each action to the appropriate child reducer.

## Usage

```bash
# Build the pattern
./gradlew :system-design-pattern:architectural:composable-architecture:build

# Run the tests
./gradlew :system-design-pattern:architectural:composable-architecture:test

# Run the Main demonstration
./gradlew :system-design-pattern:architectural:composable-architecture:run
```

## Sample Output

```
=== Composable Architecture Pattern — Pizza Order Demo ===
Initial state: OrderState[pizza=PizzaState[size=MEDIUM, toppings=[CHEESE], quantity=1], delivery=DeliveryState[name=, address=, city=, phone=]]
After selectSize(LARGE):  OrderState[pizza=PizzaState[size=LARGE, toppings=[CHEESE], quantity=1], ...]
After toggleTopping(PEPPERONI): OrderState[pizza=PizzaState[size=LARGE, toppings=[CHEESE, PEPPERONI], quantity=1], ...]
After setQuantity(2):    OrderState[pizza=PizzaState[size=LARGE, toppings=[CHEESE, PEPPERONI], quantity=2], ...]
After delivery details:  OrderState[pizza=..., delivery=DeliveryState[name=Alice, address=123 Main St, city=Springfield, phone=555-0100]]
Final order ready: true
Total price: $29.0
```

## Benefits

- **Isolation** — each feature is independent, testable in isolation, and reusable.
- **Composition without coupling** — `pullback` and `combine` let features be assembled without knowing about each other.
- **Predictable state changes** — all mutations flow through a single reducer function `(State, Action) -> State`.
- **Thread-safety** — immutable states + a reentrant lock make the store safe for concurrent producers/consumers.
- **Testability** — reducers are pure functions, making them trivial to unit test.

## Trade-offs

- **Boilerplate** — each feature requires state, action, and reducer definitions.
- **Indirection** — the pullback/combine abstractions add a layer of complexity.
- **Not idiomatic Java** — the pattern originates from the Swift/functional programming world; Java's type system makes some composition operators verbose.

## Category

Architectural

## Java Version

Java 25

## See Also

- [LLD.md](LLD.md) — Full low-level design with requirements, build config, Mermaid diagrams, and implementation details.