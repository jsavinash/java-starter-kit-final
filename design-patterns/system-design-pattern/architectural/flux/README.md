# Flux Pattern

## Overview

Flux enforces a **unidirectional data flow**: user interactions dispatch **Actions** through a central **Dispatcher**; **Stores** receive those actions, update their state, and notify subscribers. Views render from store state and never mutate it directly.

This example models a simple **todo list** application with two stores:
- **TodoStore** — manages the list of todos
- **FilterStore** — manages the current visibility filter

## Structure

```
flux/
├── build.gradle.kts
├── README.md
└── src/
    ├── main/java/com/javastarterkit/patterns/flux/
    │   └── Flux.java
    └── test/java/com/javastarterkit/patterns/flux/
        └── FluxTest.java
```

## Implementation

The example is a single self-contained Java file with inner static classes/interfaces organized into core abstractions and concrete stores:

### Core Abstractions
| Component | Responsibility |
|-----------|---------------|
| `Action` | Sealed interface for all possible events (e.g. `AddTodo`, `ToggleTodo`, `RemoveTodo`, `SetFilter`, `ClearCompleted`) |
| `Dispatcher` | Central router that broadcasts actions to all registered stores |
| `Store<S>` | Abstract base: holds state, manages subscribers, receives actions via `onAction()` |

### Stores
| Store | State | Actions handled |
|-------|-------|-----------------|
| `TodoStore` | `TodoState(List<Todo>)` | `AddTodo`, `ToggleTodo`, `RemoveTodo`, `ClearCompleted` |
| `FilterStore` | `FilterState(Filter)` | `SetFilter` |

### Flow
1. User interaction dispatches an `Action` through the `Dispatcher`.
2. The `Dispatcher` broadcasts the action to all registered stores.
3. Each store handles the action, updates its state immutably, and notifies subscribers.
4. Views (subscribers) re-render from the new state.

## Usage

```bash
# Build the pattern
./gradlew :system-design-pattern:architectural:flux:build

# Run the tests
./gradlew :system-design-pattern:architectural:flux:test
```

## Sample Output

```
=== Flux Pattern ===
Unidirectional data flow: Actions -> Dispatcher -> Store -> View

--- Dispatching actions ---
  [Dispatch] AddTodo
  [TodoView] TodoState{todos=[Todo{id=1, text='Buy milk', completed=false}]}
  [Dispatch] AddTodo
  [TodoView] TodoState{todos=[Todo{id=1, text='Buy milk', completed=false}, Todo{id=2, text='Write code', completed=false}]}
  ...

--- Final state ---
Todos: [Todo{id=1, text='Buy milk', completed=true}, Todo{id=3, text='Read book', completed=false}]
Filter: Filter{filter=COMPLETED}

Benefits:
- Predictable state changes through a single dispatcher
- Decoupled stores communicate only via actions
- Easy to debug: every state change is an explicit action
- Scales well: add stores/actions without tight coupling
```

## Benefits

- **Predictable state changes** — all mutations go through a single dispatcher, making the flow easy to trace.
- **Decoupled stores** — stores only communicate via actions; they do not call each other directly.
- **Easy debugging** — every state change is an explicit action that can be logged/replayed.
- **Scalable** — new stores and actions can be added without modifying existing ones.

## Trade-offs

- **Boilerplate** — each action and store requires explicit wiring.
- **Indirection** — the dispatcher/store/subscriber layers add complexity compared to direct mutation.
- **No built-in async** — the basic pattern is synchronous; async side effects require additional middleware.

## Category

Architectural

## Java Version

Java 25