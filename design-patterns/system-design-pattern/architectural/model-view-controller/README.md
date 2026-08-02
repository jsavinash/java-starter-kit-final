# Model-View-Controller (MVC) Pattern

## Overview

The **Model-View-Controller (MVC)** pattern separates the application into three interconnected components:

- **Model** — manages data and business rules; notifies views when it changes
- **View** — renders the model data to the user; observes the model and re-renders on change
- **Controller** — receives user input, updates the model, and coordinates views

This example models a **task management app** with a thread-safe `TaskList` model, two views (Console and HTML), and a `TaskController` that handles user commands.

## Structure

```
model-view-controller/
├── build.gradle.kts
├── README.md
├── LLD.md
└── src/
    ├── main/java/com/javastarterkit/patterns/modelviewcontroller/
    │   ├── ModelViewControllerApp.java      # Main entry point (wires MVC)
    │   ├── model/                           # Model layer
    │   │   ├── Task.java                    # Task entity (synchronized state)
    │   │   └── TaskList.java                # Task list model (observer pattern)
    │   ├── view/                            # View layer
    │   │   ├── TaskView.java                # View contract (interface)
    │   │   ├── ConsoleTaskView.java         # Console rendering
    │   │   └── HtmlTaskView.java            # HTML rendering
    │   ├── controller/                      # Controller layer
    │   │   └── TaskController.java          # User input handler
    │   └── exception/                       # Exception hierarchy
    │       ├── MvcException.java            # Base runtime exception
    │       └── TaskNotFoundException.java   # Task not found
    └── test/java/com/javastarterkit/patterns/modelviewcontroller/
        └── ModelViewControllerAppTest.java
```

## Implementation

### Components
| Component | Responsibility |
|-----------|---------------|
| `Task` | Task entity with immutable ID/description and synchronized completion state |
| `TaskList` | Thread-safe model with observer notification using `CopyOnWriteArrayList` |
| `TaskView` | View contract: renders the model and observes changes |
| `ConsoleTaskView` | Renders tasks as plain text |
| `HtmlTaskView` | Renders tasks as HTML (multiple views for one model) |
| `TaskController` | Receives user input, validates, and updates the model |

### Flow
1. The user issues a command (e.g., `addTask`) through the controller.
2. The controller delegates to the model.
3. The model updates its state and notifies all registered views.
4. Each view renders the updated model in its own format.

## Usage

```bash
# Build the pattern
./gradlew :system-design-pattern:architectural:model-view-controller:build

# Run the tests
./gradlew :system-design-pattern:architectural:model-view-controller:test
```

## Sample Output

```
=== Model-View-Controller (MVC) Pattern ===
Separate Model (data), View (display), Controller (input)

--- User: add task 'Buy groceries' ---
  Console view (0/1 completed):
    0. [ ] Buy groceries

--- User: add task 'Write report' ---
  Console view (0/2 completed):
    0. [ ] Buy groceries
    1. [ ] Write report

--- User: complete task 'Buy groceries' ---
  Console view (1/2 completed):
    0. [x] Buy groceries
    1. [ ] Write report

--- HTML view (renders the same model differently) ---
  <html>
    <body>
      <h1>Tasks (1/2)</h1>
      <ul>
        <li style="text-decoration:line-through">Buy groceries</li>
        <li>Write report</li>
      </ul>
    </body>
  </html>

Benefits:
- Model is independent of how it is displayed or updated
- Multiple views can observe the same model
- Controller coordinates user input and model updates
- Business logic lives in the model, not the UI
```

## Benefits

- **Separation of concerns** — each component has a single, well-defined responsibility.
- **Multiple views** — multiple views can observe the same model and render it differently.
- **Model independence** — the model has no knowledge of views or controllers.
- **Thread-safety** — `CopyOnWriteArrayList` and synchronized task state ensure safe concurrent access.
- **Extensibility** — new views can be added without modifying the model or controller.

## Trade-offs

- **Complexity** — MVC introduces more classes than a simple monolithic approach.
- **Indirection** — the controller adds a layer of indirection between user input and model updates.
- **Synchronization overhead** — `CopyOnWriteArrayList` has higher write cost for thread-safety.

## Category

Architectural

## Java Version

Java 25