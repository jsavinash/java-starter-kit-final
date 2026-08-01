package com.javastarterkit.patterns.frontcontroller;

import java.util.HashMap;
import java.util.Map;

/**
 * Front Controller Pattern Example
 *
 * <p>A centralized entry point ({@link FrontController}) handles all incoming
 * requests, performs common processing (logging, authentication, authorization),
 * and dispatches to the appropriate {@link Command}. This eliminates duplicate
 * code across controllers and provides a single place to enforce cross-cutting
 * concerns.
 *
 * <p>This example models a simple web application with three commands:
 * <ul>
 *   <li><b>HomeCommand</b> — renders the home page</li>
 *   <li><b>LoginCommand</b> — authenticates a user</li>
 *   <li><b>DashboardCommand</b> — renders a protected dashboard</li>
 * </ul>
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class FrontController {

    /**
     * Demonstrates the Front Controller flow: requests pass through a central
     * controller that handles cross-cutting concerns before dispatching.
     */
    public static void demonstrate() {
        System.out.println("\n=== Front Controller Pattern ===");
        System.out.println("Centralized request handling with common preprocessing\n");

        FrontController controller = new FrontController();

        // --- Register commands --------------------------------------------------
        controller.registerCommand("/home", new HomeCommand());
        controller.registerCommand("/login", new LoginCommand());
        controller.registerCommand("/dashboard", new DashboardCommand());

        // --- Process requests ---------------------------------------------------
        System.out.println("--- Processing requests ---");

        controller.handleRequest(new Request("/home", null));
        System.out.println();

        controller.handleRequest(new Request("/login", Map.of("username", "alice", "password", "secret")));
        System.out.println();

        controller.handleRequest(new Request("/dashboard", Map.of()));
        System.out.println();

        // Authenticate and retry dashboard
        controller.authenticate("alice");
        controller.handleRequest(new Request("/dashboard", Map.of()));
        System.out.println();

        // Unknown route
        controller.handleRequest(new Request("/unknown", Map.of()));

        System.out.println("\nBenefits:");
        System.out.println("- Centralized request handling and cross-cutting concerns");
        System.out.println("- Single point for authentication, authorization, logging");
        System.out.println("- Easy to add new commands without changing the controller");
        System.out.println("- Consistent request processing across the application");
    }

    public static void main(String[] args) {
        demonstrate();
    }

    // =========================================================================
    // Core abstractions: Request, Response, Command, FrontController
    // =========================================================================

    /** Represents an incoming HTTP-like request. */
    record Request(String path, Map<String, String> params) {
        String param(String key) {
            return params != null ? params.get(key) : null;
        }
    }

    /** Represents an outgoing HTTP-like response. */
    record Response(String status, String body) {
        static Response ok(String body) {
            return new Response("200 OK", body);
        }

        static Response redirect(String location) {
            return new Response("302 Found", "Redirecting to " + location);
        }

        static Response error(String message) {
            return new Response("500 Internal Server Error", message);
        }
    }

    /** Command interface: each command handles a specific request type. */
    interface Command {
        Response execute(Request request);
    }

    // =========================================================================
    // Front Controller implementation
    // =========================================================================

    private final Map<String, Command> commands = new HashMap<>();
    private boolean authenticated;

    void registerCommand(String path, Command command) {
        commands.put(path, command);
    }

    void authenticate(String username) {
        System.out.println("[Auth] User '" + username + "' authenticated successfully.");
        this.authenticated = true;
    }

    void handleRequest(Request request) {
        System.out.println("[FrontController] Received request: " + request.path());

        // Common preprocessing: logging
        System.out.println("[FrontController] Logging request: " + request.path());

        // Common preprocessing: authentication check
        if (requiresAuthentication(request.path()) && !authenticated) {
            System.out.println("[FrontController] Authentication required. Redirecting to /login.");
            System.out.println("Response: " + Response.redirect("/login"));
            return;
        }

        // Dispatch to command
        Command command = commands.get(request.path());
        if (command == null) {
            System.out.println("[FrontController] No command found for path: " + request.path());
            System.out.println("Response: " + Response.error("404 Not Found"));
            return;
        }

        Response response = command.execute(request);
        System.out.println("Response: " + response);
    }

    private boolean requiresAuthentication(String path) {
        return "/dashboard".equals(path);
    }

    // =========================================================================
    // Concrete Commands
    // =========================================================================

    /** Home page command: publicly accessible. */
    static final class HomeCommand implements Command {
        @Override
        public Response execute(Request request) {
            return Response.ok("Welcome to the home page!");
        }
    }

    /** Login command: authenticates a user. */
    static final class LoginCommand implements Command {
        @Override
        public Response execute(Request request) {
            String username = request.param("username");
            String password = request.param("password");
            if (username != null && password != null) {
                return Response.ok("Login successful for user: " + username);
            }
            return Response.error("Invalid credentials");
        }
    }

    /** Dashboard command: protected page. */
    static final class DashboardCommand implements Command {
        @Override
        public Response execute(Request request) {
            return Response.ok("Welcome to your protected dashboard!");
        }
    }
}