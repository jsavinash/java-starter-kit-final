# Model-View-Presenter (MVP) Pattern

**Category**: Architectural  
**Difficulty**: Advanced  
**Java Version**: Java 25 (Amazon Corretto)  
**Build**: Gradle 9.6.1 (Kotlin DSL)

## Overview

The **Model-View-Presenter (MVP)** architectural pattern separates an application into three core components:

- **Model** — Immutable domain data (records) and business rules. No knowledge of the UI.
- **View** — A passive contract that renders whatever the Presenter supplies. No business logic.
- **Presenter** — The orchestrator. Listens to view events, interacts with the model/repositories, and drives view updates. The Presenter owns all presentation logic, making the View deeply testable.

### Key Benefits

| Benefit | Implementation |
|---------|----------------|
| **Testability** | Presenters can be unit-tested with recording stubs — no UI framework required |
| **Thread-Safety** | `ConcurrentHashMap` repositories + `AtomicReference` presenter state |
| **Extensibility** | New views/presenters added via `TaskPresenter` interface without modifying existing code |
| **Immutability** | All domain objects are Java records with `with*()` state transitions |
| **SOLID** | Single-responsibility presenters, interface-segregated contracts, dependency inversion |

## Structure

```
model-view-presenter/
├── build.gradle.kts
├── LLD.md                          # Full low-level design (requirements, diagrams, details)
├── README.md
└── src/
    ├── main/java/com/javastarterkit/patterns/modelviewpresenter/
    │   ├── ModelViewPresenter.java        # Demo app & ConsoleTaskView
    │   ├── exception/                     # MvpException, TaskNotFoundException
    │   ├── model/                         # Task, User, Session, DashboardMetrics, ...
    │   ├── presenter/                     # TaskPresenter, TaskListPresenter, TaskDetailPresenter
    │   ├── repository/                    # UserRepository, TaskRepository, In-memory impls
    │   ├── service/                       # AuthenticationService, SessionManager
    │   └── view/                          # TaskView (passive view contract)
    └── test/java/.../ModelViewPresenterTest.java   # 19 tests (auth, dashboard, CRUD, concurrency)
```

## Components

### Model Layer (Immutable Records)
- `Task` — title, description, status, priority, timestamps, due date; `with*()` transitions
- `User` — username, email, password, roles, lastLogin; `verifyPassword()`
- `Session` — session ID, user ID, timestamps; `isExpired(Duration)`
- `DashboardMetrics` — aggregated task counts
- `Notification` — type-tagged user alerts
- `AuthenticationResult` — success/failure with payload

### Repository Layer (Thread-Safe)
- `TaskRepository` / `InMemoryTaskRepository` — `ConcurrentHashMap` + user-task index
- `UserRepository` / `InMemoryUserRepository` — `ConcurrentHashMap` + username uniqueness index

### Service Layer
- `AuthenticationService` — login, logout, session validation
- `SessionManager` — session lifecycle with timeout expiry

### View Contract
`TaskView` — 15 passive display methods; implementations have zero business logic.

### Presenter Layer
- `TaskPresenter` — lifecycle: `onAttach()`, `onDetach()`, `onDestroy()`
- `TaskListPresenter` — dashboard orchestration: user, metrics, tasks, notifications
- `TaskDetailPresenter` — task CRUD: load, save, delete, mark-completed

## Thread-Safety Strategy

1. **Immutable Models** — All records, no mutable fields
2. **ConcurrentHashMaps** — Lock-free concurrent reads for repositories/sessions
3. **AtomicReferences** — Lock-free state swaps for presenter view/selection
4. **Defensive Copies** — `List.copyOf()` on all public collection returns
5. **Stress-tested** — 100 concurrent writes + 50 concurrent read/write tests pass

## Usage

```bash
# Build and run all tests (19 passing)
./gradlew :design-patterns:system-design-pattern:architectural:model-view-presenter:build

# Run the demo application
./gradlew :design-patterns:system-design-pattern:architectural:model-view-presenter:run

# Run just the tests
./gradlew :design-patterns:system-design-pattern:architectural:model-view-presenter:test

# Run a single test class
./gradlew :design-patterns:system-design-pattern:architectural:model-view-presenter:test \
  --tests "com.javastarterkit.patterns.modelviewpresenter.ModelViewPresenterTest"
```

## Demo Flow

The `ModelViewPresenter.demonstrate()` method shows:
1. **Authentication** — login, session creation, logout
2. **Dashboard** — user profile, metrics, task list, notifications
3. **Task Detail** — load, update, mark-completed, delete
4. **Concurrency** — 5 threads concurrently reading dashboard + writing tasks

## Benefits

- Presenter isolates business logic from UI framework (Swing, JavaFX, console)
- Views are swappable — swap `ConsoleTaskView` for any UI without touching presenters
- Domain models stay immutable and thread-safe by design
- Complete test suite covers happy path, edge cases, and concurrency

## See Also

- [Full LLD Document](LLD.md) — Requirements, Gradle config, Mermaid diagrams, implementation details