package com.javastarterkit.patterns.layeredarchitecture;

import com.javastarterkit.patterns.layeredarchitecture.business.OrderService;
import com.javastarterkit.patterns.layeredarchitecture.persistence.InMemoryOrderRepository;
import com.javastarterkit.patterns.layeredarchitecture.persistence.OrderRepository;
import com.javastarterkit.patterns.layeredarchitecture.presentation.OrderConsole;
import com.javastarterkit.patterns.layeredarchitecture.presentation.OrderController;

/**
 * Layered Architecture Pattern — Main entry point.
 *
 * <p><b>Layered Architecture</b> (also known as <b>N-tier architecture</b>)
 * organizes the application into horizontal layers, each with a distinct
 * responsibility. The most common arrangement is three layers:
 * <ul>
 *   <li><b>Presentation Layer</b> — handles user interaction (UI, REST, CLI)</li>
 *   <li><b>Business/Application Layer</b> — implements business rules and use cases</li>
 *   <li><b>Persistence/Data Layer</b> — manages data storage and retrieval</li>
 * </ul>
 *
 * <p>The key rule: each layer depends only on the layer directly below it.
 * The presentation layer calls the business layer; the business layer calls
 * the persistence layer. This creates a strict dependency direction that
 * keeps the architecture predictable and testable.
 *
 * <p>This example models a simple <b>e-commerce order system</b>:
 * <ul>
 *   <li><b>Presentation Layer</b> — {@link OrderController} (simulated REST)
 *       and {@link OrderConsole} (CLI) handle user input</li>
 *   <li><b>Business Layer</b> — {@link OrderService} implements use cases
 *       (place order, cancel order, get order) and enforces business rules</li>
 *   <li><b>Persistence Layer</b> — {@link OrderRepository} (interface) and
 *       {@link InMemoryOrderRepository} (implementation) manage storage</li>
 *   <li><b>Domain Objects</b> — {@code Order}, {@code OrderItem}, {@code Money}
 *       are shared across layers</li>
 * </ul>
 *
 * <p>Each layer is isolated: the presentation layer knows nothing about
 * storage, and the persistence layer knows nothing about HTTP or the console.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public final class LayeredArchitecture {

    private LayeredArchitecture() {
        // Prevent instantiation
    }

    /**
     * Demonstrates layered architecture: place an order through the REST
     * controller, cancel it through the console, and show that each layer
     * depends only on the layer below it.
     */
    public static void demonstrate() {
        System.out.println("\n=== Layered Architecture Pattern ===");
        System.out.println("Organize code into horizontal layers with strict dependencies\n");

        // --- Build the layers (bottom-up) --------------------------------------
        OrderRepository repository = new InMemoryOrderRepository();   // Persistence layer
        OrderService service = new OrderService(repository);          // Business layer
        OrderController controller = new OrderController(service);    // Presentation layer
        OrderConsole console = new OrderConsole(service);             // Presentation layer

        // --- Presentation layer: REST controller -------------------------------
        System.out.println("--- Presentation layer: REST controller ---");
        String orderId = controller.post("/orders", "{\"customer\":\"Alice\"}");
        controller.post("/orders/" + orderId + "/items", "{\"product\":\"Laptop\",\"price\":\"999.99\",\"qty\":1}");
        controller.post("/orders/" + orderId + "/items", "{\"product\":\"Mouse\",\"price\":\"29.99\",\"qty\":2}");
        controller.get("/orders/" + orderId);

        // --- Presentation layer: Console ---------------------------------------
        System.out.println("\n--- Presentation layer: Console ---");
        String orderId2 = console.placeOrder("Bob");
        console.addItem(orderId2, "Keyboard", "79.99", 1);
        console.printOrder(orderId2);

        // --- Business layer: cancel an order -----------------------------------
        System.out.println("\n--- Business layer: cancel order ---");
        service.cancelOrder(orderId2);
        console.printOrder(orderId2);

        System.out.println("\nBenefits:");
        System.out.println("- Each layer has a single, well-defined responsibility");
        System.out.println("- Layers depend only on the layer directly below");
        System.out.println("- Presentation and persistence are swappable");
        System.out.println("- Easy to test each layer in isolation");
    }

    /**
     * Main method to run the demonstration.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        demonstrate();
    }
}