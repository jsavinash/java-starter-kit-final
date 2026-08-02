package com.javastarterkit.patterns.modelviewcontroller;

import com.javastarterkit.patterns.modelviewcontroller.controller.TaskController;
import com.javastarterkit.patterns.modelviewcontroller.model.TaskList;
import com.javastarterkit.patterns.modelviewcontroller.view.ConsoleTaskView;
import com.javastarterkit.patterns.modelviewcontroller.view.HtmlTaskView;
import com.javastarterkit.patterns.modelviewcontroller.view.TaskView;

/**
 * Main entry point demonstrating the Model-View-Controller (MVC) pattern.
 *
 * <p>Wires the model, views, and controller together and demonstrates the
 * end-to-end flow: the user issues commands through the controller, which
 * updates the model, and the views render the model state.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public final class ModelViewControllerApp {

    private ModelViewControllerApp() {
        // Prevent instantiation
    }

    /**
     * Demonstrates the MVC pattern end-to-end.
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

    /**
     * Main entry point.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        demonstrate();
    }
}