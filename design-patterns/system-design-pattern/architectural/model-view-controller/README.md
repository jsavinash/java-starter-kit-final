# Model View Controller (MVC) Pattern

## Overview

**Model-View-Controller (MVC)** separates the application into three interconnected components:

1. **Model** — manages data and business rules; notifies views when it changes
2. **View** — renders the model data to the user; observes the model
3. **Controller** — receives user input, updates the model, and coordinates views

The Model has **no knowledge** of the View or Controller. The View has **no knowledge** of the Controller's logic — it only knows how to render the Model and forward user actions. The Controller orchestrates the flow between them.

This example models a simple **task management app** to demonstrate the full MVC flow.

## Structure

```
model-view-controller/
├── build.gradle.kts
├── README.md
└── src/
    ├── main/java/com/javastarterkit/patterns/modelviewcontroller/
    │   └── ModelViewController.java
    └── test/java/com/javastarterkit/patterns/modelviewcontroller/
        └── ModelViewControllerTest.java
```

## Implementation

The example is a single self-contained Java file with inner static classes/interfaces organized into three MVC components:

### Model
| Component | Responsibility |
|-----------|---------------|
| `Task` | A single task with description and completed state |
| `TaskList` | Manages tasks and business rules; notifies registered views on every change |

### View
| Component | Responsibility |
|-----------|---------------|
| `TaskView` | View contract: renders the model and is notified when it changes |
| `ConsoleTaskView` | Renders tasks as plain text with progress (e.g., `[x]`, `[ ]`) |
| `HtmlTaskView` | Renders tasks as HTML — demonstrates multiple views for the same model |

### Controller
| Component | Responsibility |
|-----------|---------------|
| `TaskController` | Receives user commands (`addTask`, `completeTask`, `listTasks`) and updates the model |

### Flow
1. The **View** renders the **Model's** current state.
2. The user performs an action, which the **Controller** receives.
3. The **Controller** validates and updates the **Model**.
4. The **Model** notifies all registered **Views** of the change.
5. The **Views** re-render with the new state.

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

- **Separation of concerns** — data (Model), presentation (View), and input handling (Controller) are cleanly separated.
- **Multiple views** — one Model can be rendered by many Views (console, HTML, mobile, etc.) with automatic synchronization via observers.
- **Testability** — each component can be tested in isolation.
- **Parallel development** — designers work on Views while developers work on Models and Controllers.
- **Reusability** — the same Model can be reused across different front-end technologies.

## Trade-offs

- **Complexity** — for simple UIs, MVC can introduce more classes than necessary.
- **Observer overhead** — models must manage observer lists and notifications.
- **Controller bloat** — without discipline, controllers can accumulate too much logic.
- **Indirection** — data flows through three components, which can make debugging slightly harder.

## Category

Architectural

## Java Version

Java 25