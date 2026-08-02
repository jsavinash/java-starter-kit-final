# Low-Level Design: Model-View-Presenter (MVP) Pattern

**Category**: Architectural  
**Difficulty**: Advanced  
**Target**: Java 25 (Amazon Corretto) · Gradle 9.6.1 · JUnit 5

---

## 1. Requirements & Scope

### Functional Requirements

1. **User Authentication & Session Management** — Users authenticate with username/password, receive a session token, and maintain state across interactions. Sessions expire after a configurable timeout.
2. **Dashboard Data Presentation** — Display user-specific dashboard with task metrics (total, pending, in-progress, completed, overdue), user profile, and generated notifications.
3. **Task Management CRUD Operations** — Create, read, update, and delete tasks with immutable state transitions (`with*()` methods), preserving ownership and audit timestamps.
4. **Presenter-Mediated View Updates** — The passive View renders exactly what the Presenter supplies; no business logic in the View. Presenters attach/detach cleanly for lifecycle management.
5. **Input Validation & Error Handling** — Comprehensive validation at service boundaries (username format, password strength) with typed exceptions (`TaskNotFoundException`, `IllegalStateException`) and user-friendly error messages.

### Non-Functional Requirements

- **Thread-Safety** — All repositories (`ConcurrentHashMap`), presenter mutable state (`AtomicReference`), and session management must be safe for concurrent UI interactions.
- **Extensibility** — New Views and Presenters can be added without modifying existing components (Open/Closed Principle); sealed contracts via interfaces.
- **Testability** — Full separation of Model, View, and Presenter enables unit testing with recording stubs and concurrency stress tests without UI frameworks.
- **Immutability** — All domain objects are Java records; state changes produce new instances via `with*()` methods.

---

## 2. Gradle Project Build Configuration

[See `build.gradle.kts` in this directory]

```kotlin
plugins {
    java
    application
}

group = "com.javastarterkit.patterns"
version = "1.0.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
        vendor = JvmVendorSpec.AMAZON
    }
    withSourcesJar()
    withJavadocJar()
}

application {
    mainClass = "com.javastarterkit.patterns.modelviewpresenter.ModelViewPresenter"
}

repositories {
    mavenCentral()
}

dependencies {
    // Testing
    testImplementation(platform("org.junit:junit-bom:5.11.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.assertj:assertj-core:3.27.3")
    testImplementation("org.mockito:mockito-junit-jupiter:5.14.2")
    testImplementation("org.awaitility:awaitility:4.3.0")

    // Concurrency utilities
    implementation("com.google.guava:guava:33.4.0-jre")

    // Logging
    implementation("org.slf4j:slf4j-api:2.0.16")
    implementation("ch.qos.logback:logback-classic:1.5.16")
}
```

---

## 3. LLD Diagrams (Mermaid.js)

### 3.1 Class Diagram — Core Entities & Relationships

```mermaid
classDiagram
    %% ═════════════ MODEL LAYER ═════════════
    class User {
        <<record>>
        -String id
        -String username
        -String email
        -String password
        -Set~Role~ roles
        -Instant lastLogin
        +create(username, email, password, roles) User
        +withUpdatedLastLogin() User
        +hasRole(Role) boolean
        +isAdmin() boolean
        +isManager() boolean
        +verifyPassword(String) boolean
    }

    class Task {
        <<record>>
        -String id
        -String userId
        -String title
        -String description
        -TaskStatus status
        -Priority priority
        -Instant createdAt
        -Instant updatedAt
        -Instant completedAt
        -Instant dueDate
        +create(userId, title, description, priority, dueDate) Task
        +withTitle(String) Task
        +withDescription(String) Task
        +withStatus(TaskStatus) Task
        +withPriority(Priority) Task
        +withDueDate(Instant) Task
        +markCompleted() Task
        +isOverdue() boolean
        +isCompleted() boolean
    }

    class Session {
        <<record>>
        -String id
        -String userId
        -Instant createdAt
        -Instant lastAccessedAt
        +withTouched() Session
        +isExpired(Duration) boolean
    }

    class DashboardMetrics {
        <<record>>
        -long totalTasks
        -long pendingTasks
        -long inProgressTasks
        -long completedTasks
        -long overdueTasks
        +empty() DashboardMetrics
    }

    class Notification {
        <<record>>
        -String id
        -String message
        -NotificationType type
        -Instant timestamp
        -boolean read
        +markAsRead() Notification
        +info(id, message) Notification
        +success(id, message) Notification
        +warning(id, message) Notification
        +error(id, message) Notification
    }

    class AuthenticationResult {
        <<record>>
        -boolean success
        -String sessionId
        -User user
        -String errorMessage
        +success(sessionId, user) AuthenticationResult
        +failure(errorMessage) AuthenticationResult
        +getSessionId() Optional~String~
        +getUser() Optional~User~
        +getErrorMessage() Optional~String~
    }

    class TaskStatus {
        <<enumeration>>
        PENDING
        IN_PROGRESS
        COMPLETED
        CANCELLED
    }

    class Priority {
        <<enumeration>>
        LOW
        MEDIUM
        HIGH
        CRITICAL
    }

    class Role {
        <<enumeration>>
        USER
        ADMIN
        MANAGER
    }

    class NotificationType {
        <<enumeration>>
        INFO
        SUCCESS
        WARNING
        ERROR
    }

    %% ═════════════ REPOSITORY LAYER ═════════════
    class UserRepository {
        <<interface>>
        +findById(String) Optional~User~
        +findByUsername(String) Optional~User~
        +save(User) User
        +deleteById(String) void
        +existsByUsername(String) boolean
    }

    class TaskRepository {
        <<interface>>
        +findById(String) Optional~Task~
        +findByUserId(String) List~Task~
        +findByUserIdAndStatus(String, TaskStatus) List~Task~
        +save(Task) Task
        +deleteById(String) void
        +countByUserId(String) long
    }

    class InMemoryUserRepository {
        -Map~String, User~ usersById
        -Map~String, String~ usernameIndex
        +save(User) User
    }

    class InMemoryTaskRepository {
        -Map~String, Task~ tasksById
        -Map~String, List~String~~ userTaskIndex
        +save(Task) Task
    }

    %% ═════════════ VIEW LAYER ═════════════
    class TaskView {
        <<interface>>
        +showTasks(List~Task~) void
        +showTask(Task) void
        +showNotification(String, NotificationType) void
        +showLoading() void
        +hideLoading() void
        +clearForm() void
        +showTaskNotFoundError(String) void
        +showConfirmation(String) boolean
        +setFormEnabled(boolean) void
        +updateTaskCount(int, int, int) void
        +displayUser(User) void
        +displayMetrics(DashboardMetrics) void
        +displayNotifications(List~Notification~) void
        +displayTask(Task) void
        +showError(String) void
        +enableEditing(boolean) void
    }

    %% ═════════════ PRESENTER LAYER ═════════════
    class TaskPresenter {
        <<interface>>
        +onAttach(TaskView) void
        +onDetach() void
        +onDestroy() void
    }

    class TaskListPresenter {
        -UserRepository userRepository
        -TaskRepository taskRepository
        -AtomicReference~TaskView~ viewRef
        -AtomicReference~User~ currentUser
        +onAttach(TaskView) void
        +onDetach() void
        +onDestroy() void
        +onViewReady(String) void
        +onRefreshRequested() void
        +getCurrentUserId() String
        -computeMetrics(List~Task~) DashboardMetrics
        -generateNotifications(List~Task~, User) List~Notification~
    }

    class TaskDetailPresenter {
        -TaskRepository taskRepository
        -AtomicReference~TaskView~ viewRef
        -AtomicReference~String~ currentTaskId
        +onAttach(TaskView) void
        +onDetach() void
        +onDestroy() void
        +onViewReady(String) void
        +onSaveRequested(Task) void
        +onDeleteRequested() void
        +onMarkCompleted() void
        +onEditModeChanged(boolean) void
    }

    %% ═════════════ SERVICE LAYER ═════════════
    class AuthenticationService {
        -UserRepository userRepository
        -SessionManager sessionManager
        +authenticate(String, String) AuthenticationResult
        +logout(String) void
        +validateSession(String) boolean
        +getCurrentUser(String) User
    }

    class SessionManager {
        -Map~String, Session~ sessions
        -Duration sessionTimeout
        +createSession(User) Session
        +getSession(String) Optional~Session~
        +invalidateSession(String) void
        +isValid(String) boolean
        +cleanupExpiredSessions() void
    }

    %% ═════════════ RELATIONSHIPS ═════════════
    %% Repository implementations
    UserRepository <|.. InMemoryUserRepository
    TaskRepository <|.. InMemoryTaskRepository

    %% Presenter hierarchy
    TaskPresenter <|.. TaskListPresenter
    TaskPresenter <|.. TaskDetailPresenter

    %% Presenter → Repository & View
    TaskListPresenter --> TaskView
    TaskListPresenter --> UserRepository
    TaskListPresenter --> TaskRepository
    TaskDetailPresenter --> TaskView
    TaskDetailPresenter --> TaskRepository

    %% Service → Repository
    AuthenticationService --> UserRepository
    AuthenticationService --> SessionManager
    SessionManager --> Session

    %% Model associations
    Task --> TaskStatus
    Task --> Priority
    User --> Role
    Notification --> NotificationType
    AuthenticationResult --> User
    DashboardMetrics --> Task

    %% Composition — Model aggregates
    User "1" o-- "0..*" Task : owns
    User "1" o-- "0..*" Session : has
    Session "1" --> "1" User : belongs_to
```

### 3.2 Sequence Diagram — User Authentication → Dashboard → Task Detail End-to-End Flow

```mermaid
sequenceDiagram
    participant Client
    participant TaskView as TaskView (View)
    participant Presenter as TaskListPresenter / TaskDetailPresenter
    participant AuthService as AuthenticationService
    participant SessionMgr as SessionManager
    participant UserRepo as UserRepository
    participant TaskRepo as TaskRepository

    %% ═══════════ PHASE 1: AUTHENTICATION ═══════════
    Client->>AuthService: authenticate(username, password)
    AuthService->>AuthService: validate format (regex)
    alt invalid format
        AuthService-->>Client: AuthenticationResult.failure(msg)
    end

    AuthService->>UserRepo: findByUsername(username)
    UserRepo-->>AuthService: Optional<User>
    alt user not found OR wrong password
        AuthService-->>Client: AuthenticationResult.failure("Invalid username or password")
    end

    AuthService->>UserRepo: save(user.withUpdatedLastLogin())
    AuthService->>SessionMgr: createSession(user)
    SessionMgr->>SessionMgr: new Session(UUID, user.id, now, now)
    SessionMgr-->>AuthService: Session
    AuthService-->>Client: AuthenticationResult.success(sessionId, user)

    %% ═══════════ PHASE 2: DASHBOARD PRESENTATION ═══════════
    Client->>Presenter: onAttach(dashboardView)
    Client->>Presenter: onViewReady(userId)

    Presenter->>TaskView: showLoading()
    Presenter->>UserRepo: findById(userId)
    UserRepo-->>Presenter: Optional<User>
    Presenter->>TaskRepo: findByUserId(userId)
    TaskRepo-->>Presenter: List<Task>

    Presenter->>Presenter: computeMetrics(tasks)
    Presenter-->>Presenter: DashboardMetrics

    alt has overdue tasks
        Presenter->>Presenter: generateNotifications(tasks, user)
        Presenter-->>Presenter: List<Notification>
    end

    Presenter->>TaskView: displayUser(user)
    Presenter->>TaskView: displayMetrics(metrics)
    Presenter->>TaskView: showTasks(tasks)
    Presenter->>TaskView: displayNotifications(notifications)
    Presenter->>TaskView: hideLoading()
    Presenter-->>Client: onViewReady complete

    %% ═══════════ PHASE 3: TASK DETAIL OPERATIONS ═══════════
    Client->>Presenter: onAttach(detailView)
    Client->>Presenter: onViewReady(taskId)

    Presenter->>TaskView: showLoading()
    Presenter->>TaskRepo: findById(taskId)
    TaskRepo-->>Presenter: Optional<Task>
    Presenter->>TaskView: displayTask(task)
    Presenter->>TaskView: hideLoading()
    Presenter-->>Client: task rendered

    %% Mark as completed
    Client->>Presenter: onMarkCompleted()
    Presenter->>TaskRepo: findById(taskId)
    TaskRepo-->>Presenter: Task
    Presenter->>TaskRepo: save(task.withStatus(COMPLETED))
    TaskRepo-->>Presenter: completedTask
    Presenter->>TaskView: displayTask(completedTask)
    Presenter->>TaskView: showNotification("Task marked completed", SUCCESS)
```

### 3.3 Sequence Diagram — Concurrent Access Safety

```mermaid
sequenceDiagram
    participant Thread1 as Worker-1
    participant Thread2 as Worker-2
    participant ThreadN as Worker-N (up to 100)
    participant Repo as TaskRepository (ConcurrentHashMap)
    participant Presenter as TaskListPresenter (AtomicReference)

    par Concurrent Dashboard Reads
        Thread1->>Presenter: onRefreshRequested()
        Thread2->>Presenter: onRefreshRequested()
        ThreadN->>Presenter: onRefreshRequested()
    and Concurrent Task Writes
        Thread1->>Repo: save(task1)
        Thread2->>Repo: save(task2)
        ThreadN->>Repo: save(taskN)
    end

    Note over Repo: ConcurrentHashMap guarantees<br/>lock-free atomic put/remove
    Note over Presenter: AtomicReference guarantees<br/>volatile visibility of view/state

    Repo-->>Thread1: task saved
    Repo-->>Thread2: task saved
    Repo-->>ThreadN: task saved
    Presenter-->>Thread1: dashboard rendered
    Presenter-->>Thread2: dashboard rendered
    Presenter-->>ThreadN: dashboard rendered
```

---

## 4. System Implementation Details & Code

### 4.1 Package Structure

```
com.javastarterkit.patterns.modelviewpresenter
├── ModelViewPresenter.java              # Demo application & composition root
├── exception/
│   ├── MvpException.java                # Base runtime exception
│   └── TaskNotFoundException.java       # Task lookup failure
├── model/                               # Immutable domain records & enums
│   ├── AuthenticationResult.java
│   ├── DashboardMetrics.java
│   ├── Notification.java
│   ├── NotificationType.java
│   ├── Priority.java
│   ├── Role.java
│   ├── Session.java
│   ├── Task.java
│   ├── TaskStatus.java
│   └── User.java
├── presenter/
│   ├── TaskDetailPresenter.java         # Single-task CRUD orchestration
│   ├── TaskListPresenter.java           # Dashboard orchestration
│   └── TaskPresenter.java               # Lifecycle contract interface
├── repository/
│   ├── InMemoryTaskRepository.java      # ConcurrentHashMap task store
│   ├── InMemoryUserRepository.java      # ConcurrentHashMap + username index
│   ├── TaskRepository.java              # Task persistence contract
│   └── UserRepository.java              # User persistence contract
├── service/
│   ├── AuthenticationService.java       # Login/logout/session validation
│   └── SessionManager.java              # Session lifecycle & expiry
└── view/
    └── TaskView.java                    # Passive view contract
```

### 4.2 Thread-Safety Strategy

| Component | Strategy | Why |
|-----------|----------|-----|
| **Domain Models** | Immutable Java records | No shared mutable state; thread-safe by design |
| **Repositories** | `ConcurrentHashMap` for key-value stores; synchronized `save()` for user uniqueness | Lock-free concurrent reads; atomic writes |
| **Presenters** | `AtomicReference<TaskView>` and `AtomicReference<User>`/`AtomicReference<String>` | Volatile visibility of attached view and current selection; lock-free state swaps |
| **Session Manager** | `ConcurrentHashMap<String, Session>` + immutable `Session` record | Concurrent session creation/invalidation |
| **Notifications** | `List.copyOf()` defensive copies | Prevents external mutation of returned collections |

**Key Java 25 / modern constructs used:**
- Records for all value/DTO types
- `AtomicReference` for lock-free state management
- `ConcurrentHashMap.computeIfAbsent()` for atomic index updates
- `List.copyOf()` / `.toList()` for immutable collection returns
- Pattern-matching-friendly `Optional` for null-safety
- `CountDownLatch` + `ExecutorService` for concurrency stress tests

### 4.3 SOLID Principles Applied

- **S** — Each class has one responsibility: `TaskListPresenter` only orchestrates dashboard; `TaskDetailPresenter` only handles task details; `SessionManager` only manages session lifecycles.
- **O** — Open for extension: new presenters implement `TaskPresenter` interface without modifying existing code; new repositories implement repository interfaces.
- **L** — Liskov substitutable: `InMemoryTaskRepository` and any future impl are substitutable for `TaskRepository`.
- **I** — Interface segregation: `TaskView` is a focused view contract; `TaskPresenter` is a slim lifecycle contract; repository interfaces are minimal.
- **D** — Dependency inversion: presenters depend on `UserRepository`/`TaskRepository` abstractions, not concrete in-memory implementations.

### 4.4 Key Code Examples

#### Record Models with Immutable State Transitions

```java
public record Task(
        String id,
        String userId,
        String title,
        String description,
        TaskStatus status,
        Priority priority,
        Instant createdAt,
        Instant updatedAt,
        Instant completedAt,
        Instant dueDate
) {
    public Task withStatus(TaskStatus newStatus) {
        Instant newCompletedAt = (newStatus == TaskStatus.COMPLETED) ? Instant.now() : null;
        return new Task(id, userId, title, description, newStatus, priority,
                createdAt, Instant.now(), newCompletedAt, dueDate);
    }

    public boolean isOverdue() {
        return dueDate != null && dueDate.isBefore(Instant.now())
                && status != TaskStatus.COMPLETED;
    }
}
```

#### Thread-Safe Presenter with AtomicReference

```java
public final class TaskListPresenter implements TaskPresenter {
    private final AtomicReference<TaskView> viewRef = new AtomicReference<>();
    private final AtomicReference<User> currentUser = new AtomicReference<>();

    @Override
    public void onAttach(TaskView view) {
        viewRef.set(Objects.requireNonNull(view, "View must not be null"));
    }

    public void onViewReady(String userId) {
        TaskView view = getAttachedView();
        view.showLoading();
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new TaskNotFoundException("User not found: " + userId));
            List<Task> tasks = taskRepository.findByUserId(userId);
            currentUser.set(user);
            view.displayUser(user);
            view.displayMetrics(computeMetrics(tasks));
            view.showTasks(tasks);
        } finally {
            view.hideLoading();
        }
    }
}
```

#### Thread-Safe Repository with ConcurrentHashMap

```java
public final class InMemoryTaskRepository implements TaskRepository {
    private final Map<String, Task> tasksById = new ConcurrentHashMap<>();
    private final Map<String, List<String>> userTaskIndex = new ConcurrentHashMap<>();

    @Override
    public Task save(Task task) {
        tasksById.put(task.id(), task);
        userTaskIndex.computeIfAbsent(task.userId(), k -> new ArrayList<>())
                .add(task.id());
        return task;
    }
}
```

### 4.5 Test Coverage

The test suite (`ModelViewPresenterTest`) covers:
- **Authentication**: valid/invalid credentials, username format validation, logout, null-safety
- **Dashboard Presentation**: metrics computation (5 scenarios), user display, notification generation
- **Task Operations**: load, save (preserving ownership), delete, mark-completed, not-found errors
- **Concurrency**: 100 concurrent task saves, 50 concurrent read/write operations on same task
- **End-to-End**: full authenticate → dashboard → detail → logout flow; `ModelViewPresenter.demonstrate()` runs without throwing

### 4.6 Running the Demo

```bash
# Build & run all tests
./gradlew :design-patterns:system-design-pattern:architectural:model-view-presenter:build

# Run the demo application
./gradlew :design-patterns:system-design-pattern:architectural:model-view-presenter:run