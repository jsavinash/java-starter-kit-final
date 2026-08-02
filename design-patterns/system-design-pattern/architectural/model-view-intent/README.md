# Model-View-Intent (MVI) Pattern

## Overview

The **Model-View-Intent (MVI)** pattern is a unidirectional data-flow architecture built on three core concepts:

- **Intent** — a user's action expressed as an immutable message. Intents are the **only** way to change state.
- **Model (State)** — an immutable snapshot of the UI state. The View always renders from this single source of truth.
- **View** — renders the current state and dispatches intents. The View never mutates state directly.

Between the View and the Model sits a **Reducer** — a pure function that takes the current state and an intent, and produces a **new** state. This guarantees predictable, traceable state changes.

## Structure

```
model-view-intent/
├── build.gradle.kts
├── README.md
├── LLD.md
└── src/
    ├── main/java/com/javastarterkit/patterns/modelviewintent/
    │   ├── ModelViewIntentApp.java      # Main entry point
    │   ├── core/                        # Core MVI infrastructure
    │   │   ├── MviStore.java            # Thread-safe store (AtomicReference)
    │   │   ├── Reducer.java             # Pure reducer interface
    │   │   └── ViewObserver.java        # Observer interface
    │   ├── intent/                      # Immutable user actions
    │   │   ├── CounterIntent.java       # Sealed interface
    │   │   ├── Increment.java
    │   │   ├── Decrement.java
    │   │   ├── Reset.java
    │   │   ├── TaskIntent.java          # Sealed interface
    │   │   ├── AddTask.java
    │   │   └── CompleteTask.java
    │   ├── state/                       # Immutable state snapshots
    │   │   ├── CounterState.java
    │   │   ├── TaskState.java
    │   │   └── TaskItem.java
    │   ├── reducer/                     # Pure reducer functions
    │   │   ├── CounterReducer.java
    │   │   └── TaskReducer.java
    │   ├── view/                        # View implementations
    │   │   ├── CounterView.java
    │   │   └── TaskListView.java
    │   └── exception/                   # Exception hierarchy
    │       ├── MviException.java
    │       └── InvalidIntentException.java
    └── test/java/com/javastarterkit/patterns/modelviewintent/
        └── ModelViewIntentAppTest.java
```

## Implementation

### Components
| Component | Responsibility |
|-----------|---------------|
| `MviStore` | Thread-safe store holding state, reducing intents, notifying observers |
| `Reducer` | Pure function interface: (state, intent) → new state |
| `ViewObserver` | Observer notified on state changes |
| `CounterIntent` | Sealed interface for counter actions (Increment, Decrement, Reset) |
| `TaskIntent` | Sealed interface for task actions (AddTask, CompleteTask) |
| `CounterState` | Immutable counter state record |
| `TaskState` | Immutable task list state record |
| `CounterReducer` | Pure reducer for counter intents |
| `TaskReducer` | Pure reducer for task intents |
| `CounterView` | Renders counter state |
| `TaskListView` | Renders task list state |

### Data Flow
```
View → Intent → Reducer → State → View
```

## Usage

```bash
# Build the pattern
./gradlew :system-design-pattern:architectural:model-view-intent:build

# Run the tests
./gradlew :system-design-pattern:architectural:model-view-intent:test
```

## Benefits

- **Unidirectional data flow** — state changes are predictable and traceable.
- **Immutable state** — no accidental mutation.
- **Pure reducers** — easy to test, no side effects.
- **Single source of truth** — the View always renders from the store's state.
- **Thread-safe** — `AtomicReference` and `CopyOnWriteArrayList` ensure safe concurrent access.

## Category

Architectural

## Java Version

Java 25