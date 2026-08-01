# Composable Architecture Pattern

## Overview

Composable Architecture structures an application around small, independent **components**, each owning its own **State**, **Action**s, and a **Reducer** that describes how state changes in response to actions. Components are composed hierarchically — a parent reducer pulls state out of global state, delegates actions to child reducers, and combines the results — so complex features are built by assembling simple, testable pieces.

The core building blocks (inspired by Point-Free's "The Composable Architecture") are:

- **State** — a record describing the data a feature owns
- **Action** — a sealed interface of all possible user/system events
- **Reducer** — a pure function `(State, Action) -> State`
- **Store** — holds the current state and dispatches actions through the reducer
- **Component** — bundles a state slice, action subset, and reducer so it can be composed

This example models a simple **counter** and a **text-input** feature, then composes them into a single **form** feature to demonstrate how independent components are combined without coupling.

## Structure

```
composable-architecture/
├── build.gradle.kts
├── README.md
└── src/
    ├── main/java/com/javastarterkit/patterns/composablearchitecture/
    │   └── ComposableArchitecture.java
    └── test/java/com/javastarterkit/patterns/composablearchitecture/
        └── ComposableArchitectureTest.java
```

## Implementation

The example is a single self-contained Java file with inner static classes/interfaces organized into core abstractions and feature components:

### Core Abstractions
| Component | Responsibility |
|-----------|---------------|
| `State` | Marker interface for state records |
| `Action` | Marker interface for action sealed interfaces |
| `Reducer<S, A>` | Pure function `(State, Action) -> State`; provides `combine` and `pullback` composition operators |
| `Store<S, A>` | Holds current state; dispatches actions through the reducer |
| `Component<S, A>` | Bundles initial-state factory + reducer for composition |

### Feature Components
| Feature | State | Actions | Reducer |
|---------|-------|---------|---------|
| Counter | `CounterState(count)` | `increment`, `decrement` | `CounterReducer` |
| Text | `TextState(value)` | `change(text)`, `clear` | `TextReducer` |
| Form (composed) | `FormState(counter, text)` | `Counter(action)`, `Text(action)` | Combined via `pullback` + `combine` |

### Composition Operators
- **`Reducer.combine(first, second)`** — chains two reducers over the same state/action space; the second sees the state produced by the first.
- **`Reducer.pullback(component, extract, inject, mapAction)`** — adapts a child reducer to a parent state/action space: extracts the child state slice, runs the child reducer, and injects the result back. This is the key operator that lets independent features be combined without coupling.

### Flow
1. Build independent feature `Component`s (Counter, Text).
2. Compose them into a parent `FormState`/`FormAction` reducer using `pullback` and `combine`.
3. Create a `Store` with the composed reducer and initial state.
4. Dispatch actions — the store runs the reducer, which routes each action to the appropriate child reducer.

## Usage

```bash
# Build the pattern
./gradlew :system-design-pattern:architectural:composable-architecture:build

# Run the tests
./gradlew :system-design-pattern:architectural:composable-architecture:test
```

## Sample Output

```
=== Composable Architecture Pattern ===
Build features from small, independent, composable components

Initial state: Form{Counter(count=0), Text(value='')}
After increment: Form{Counter(count=1), Text(value='')}
After +1, -1:    Form{Counter(count=1), Text(value='')}
After text:      Form{Counter(count=1), Text(value='Hello')}
After clear:     Form{Counter(count=1), Text(value='')}

Benefits:
- Each feature is isolated, testable, and reusable
- Features are composed without coupling via pullback/combine
- State changes are centralized and predictable (single reducer)
- Easy to reason about: (State, Action) -> State
```

## Benefits

- **Isolation** — each feature is independent, testable in isolation, and reusable.
- **Composition without coupling** — `pullback` and `combine` let features be assembled without knowing about each other.
- **Predictable state changes** — all mutations flow through a single reducer function `(State, Action) -> State`.
- **Testability** — reducers are pure functions, making them trivial to unit test.

## Trade-offs

- **Boilerplate** — each feature requires state, action, and reducer definitions.
- **Indirection** — the pullback/combine abstractions add a layer of complexity.
- **Not idiomatic Java** — the pattern originates from the Swift/functional programming world; Java's type system makes some composition operators verbose.

## Category

Architectural

## Java Version

Java 25