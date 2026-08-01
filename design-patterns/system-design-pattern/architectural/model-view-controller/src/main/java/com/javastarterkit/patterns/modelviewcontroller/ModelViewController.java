package com.javastarterkit.patterns.modelviewcontroller;

import java.util.ArrayList;
import java.util.List;

/**
 * Model-View-Controller (MVC) Pattern Example
 *
 * <p><b>Model-View-Controller</b> separates the application into three
 * interconnected components:
 * <ul>
 *   <li><b>Model</b> — {@link TaskList} manages data and business rules;
 *       notifies views when it changes</li>
 *   <li><b>View</b> — {@link TaskView} renders the model data to the user;
 *       observes the model and re-renders on change</li>
 *   <li><b>Controller</b> — {@link TaskController} receives user input,
 *       updates the model, and coordinates views</li>
 * </ul>
 *
 * <p>The Model has <b>no knowledge</b> of the View or Controller. The View
 * has <b>no knowledge</b> of the Controller's logic — it only knows how to
 * render the Model and forward user actions. The Controller orchestrates
 * the flow between them.
 *
 * <p>This self-contained example models a simple <b>task management app</b>:
 * <ul>
 *   <li><b>Model</b> — {@link Task} and {@link TaskList} with add/complete
 *       operations and change notification</li>
 *   <li><b>View</b> — {@link ConsoleTaskView} renders tasks and captures
 *       user commands</li>
 *   <li><b>View</b> — {@link HtmlTaskView} renders tasks as HTML (shows
 *       multiple views for the same model)</li>
 *   <li><b>Controller</b> — {@link TaskController} handles commands like
 *       "add", "complete", "list"</li>
 * </ul>
 *
 * <p>Key benefit: multiple views (console, HTML) can observe the same model,
 * and business logic lives entirely in the model — independent of how it is
 * displayed or how user input is captured.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class ModelViewController {

    /**
     * Demonstrates MVC: create a model, attach two views, and drive the
     * application through the controller. Both views update automatically
     * when the model changes.
     */
    public static void demonstrate() {
        System.out.println("\n=== Model-View-Controller (MVC) Pattern ===");
        System.out.println("Separate Model (data), View (display), Controller (input)\n");

        // --- Build the model, views, and controller ----------------------------
        TaskList model = new TaskList();
        TaskView consoleView = new ConsoleTaskView();
        TaskView htmlView = new HtmlTaskView();
        TaskController controller = new TaskController(model);

        // --- Attach views to the model ----------------------------------------
        model.addObserver(consoleView);
        model.addObserver(htmlView);

        // --- User actions handled by the controller ----------------------------
        System.out.println("--- User: add task 'Buy groceries' ---");
        controller.addTask("Buy groceries");
        consoleView.render(model);

        System.out.println("\n--- User: add task 'Write report' ---");
        controller.addTask("Write report");
        consoleView.render(model);

        System.out.println("\n--- User: complete task 'Buy groceries' ---");
        controller.completeTask(0);
        consoleView.render(model);

        System.out.println("\n--- HTML view (renders the same model differently) ---");
        htmlView.render(model);

        System.out.println("\nBenefits:");
        System.out.println("- Model is independent of how it is displayed or updated");
        System.out.println("- Multiple views can observe the same model");
        System.out.println("- Controller coordinates user input and model updates");
        System.out.println("- Business logic lives in the model, not the UI");
    }

    public static void main(String[] args) {
        demonstrate();
    }

    // =========================================================================
    // MODEL — data + business rules, no knowledge of views or controllers
    // =========================================================================

    /** A single task in the task list. */
    static final class Task {
        private final String description;
        private boolean completed;

        Task(String description) {
            this.description = description;
        }

        String description() {
            return description;
        }

        boolean isCompleted() {
            return completed;
        }

        void complete() {
            completed = true;
        }

        @Override
        public String toString() {
            return (completed ? "[x] " : "[ ] ") + description;
        }
    }

    /**
     * Model: a list of tasks with business rules and observer notification.
     * Views register as observers and are notified on every change.
     */
    static final class TaskList {
        private final List<Task> tasks = new ArrayList<>();
        private final List<TaskView> observers = new ArrayList<>();

        void addObserver(TaskView view) {
            observers.add(view);
        }

        void addTask(String description) {
            if (description == null || description.isBlank()) {
                throw new IllegalArgumentException("Task description cannot be blank");
            }
            tasks.add(new Task(description));
            notifyObservers();
        }

        void completeTask(int index) {
            if (index < 0 || index >= tasks.size()) {
                throw new IndexOutOfBoundsException("Invalid task index: " + index);
            }
            tasks.get(index).complete();
            notifyObservers();
        }

        List<Task> tasks() {
            return List.copyOf(tasks);
        }

        int size() {
            return tasks.size();
        }

        int completedCount() {
            return (int) tasks.stream().filter(Task::isCompleted).count();
        }

        private void notifyObservers() {
            for (TaskView view : observers) {
                view.onModelChanged(this);
            }
        }
    }

    // =========================================================================
    // VIEW — renders the model; observes changes
    // =========================================================================

    /**
     * View contract: renders the model and is notified when it changes.
     * Views know nothing about the controller or business logic.
     */
    interface TaskView {
        void render(TaskList model);

        default void onModelChanged(TaskList model) {
            // Optional hook: re-render when the model changes.
        }
    }

    /** Console view: renders tasks as plain text. */
    static final class ConsoleTaskView implements TaskView {
        @Override
        public void render(TaskList model) {
            System.out.println("  Console view (" + model.completedCount() + "/" + model.size() + " completed):");
            if (model.size() == 0) {
                System.out.println("    (no tasks)");
                return;
            }
            for (int i = 0; i < model.size(); i++) {
                System.out.println("    " + i + ". " + model.tasks().get(i));
            }
        }

        @Override
        public void onModelChanged(TaskList model) {
            // In a real UI, this would trigger a re-render automatically.
            // In this console example, we rely on explicit render() calls.
        }
    }

    /** HTML view: renders tasks as HTML — shows multiple views for one model. */
    static final class HtmlTaskView implements TaskView {
        @Override
        public void render(TaskList model) {
            StringBuilder html = new StringBuilder();
            html.append("  <html>\n");
            html.append("    <body>\n");
            html.append("      <h1>Tasks (").append(model.completedCount())
                    .append("/").append(model.size()).append(")</h1>\n");
            html.append("      <ul>\n");
            for (Task task : model.tasks()) {
                String css = task.isCompleted() ? " style=\"text-decoration:line-through\"" : "";
                html.append("        <li").append(css).append(">")
                        .append(task.description()).append("</li>\n");
            }
            html.append("      </ul>\n");
            html.append("    </body>\n");
            html.append("  </html>");
            System.out.println(html);
        }

        @Override
        public void onModelChanged(TaskList model) {
            // Similarly, in a real UI this would auto-refresh.
        }
    }

    // =========================================================================
    // CONTROLLER — handles user input, updates the model, coordinates views
    // =========================================================================

    /**
     * Controller: receives user commands, validates them, and updates the
     * model. It knows about the model and the views (to trigger renders),
     * but the model does not know about the controller.
     */
    static final class TaskController {
        private final TaskList model;

        TaskController(TaskList model) {
            this.model = model;
        }

        /** User command: add a new task. */
        void addTask(String description) {
            model.addTask(description);
        }

        /** User command: mark a task as complete. */
        void completeTask(int index) {
            model.completeTask(index);
        }

        /** User command: get tasks for display. */
        List<Task> listTasks() {
            return model.tasks();
        }
    }
}