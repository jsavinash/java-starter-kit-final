package com.javastarterkit.patterns.frontcontroller;

import com.javastarterkit.patterns.frontcontroller.commands.DashboardCommand;
import com.javastarterkit.patterns.frontcontroller.commands.HomeCommand;
import com.javastarterkit.patterns.frontcontroller.commands.LoginCommand;
import com.javastarterkit.patterns.frontcontroller.commands.UnknownCommand;
import java.util.Map;
import java.util.Objects;

/**
 * Centralized entry point for handling all incoming requests.
 *
 * <p>The Front Controller pattern provides a single entry point for handling all requests,
 * enabling consistent preprocessing (logging, authentication, authorization) before
 * dispatching to the appropriate command.
 *
 * <p>This implementation is thread-safe and can be safely shared across multiple threads.
 *
 * @see Command
 * @see CommandRegistry
 * @see AuthenticationService
 * @see RequestLogger
 */
public class FrontController {

    private final CommandRegistry commandRegistry;
    private final AuthenticationService authService;
    private final RequestLogger requestLogger;
    private final Command defaultCommand;

    /**
     * Creates a new FrontController with the given dependencies.
     *
     * @param commandRegistry the command registry for looking up commands
     * @param authService the authentication service for auth checks
     * @param requestLogger the logger for request/response logging
     * @throws IllegalArgumentException if any dependency is null
     */
    public FrontController(CommandRegistry commandRegistry,
                          AuthenticationService authService,
                          RequestLogger requestLogger) {
        this(commandRegistry, authService, requestLogger, new UnknownCommand());
    }

    /**
     * Creates a new FrontController with the given dependencies and default command.
     *
     * @param commandRegistry the command registry for looking up commands
     * @param authService the authentication service for auth checks
     * @param requestLogger the logger for request/response logging
     * @param defaultCommand the default command for unknown routes
     * @throws IllegalArgumentException if any dependency is null
     */
    public FrontController(CommandRegistry commandRegistry,
                          AuthenticationService authService,
                          RequestLogger requestLogger,
                          Command defaultCommand) {
        this.commandRegistry = Objects.requireNonNull(commandRegistry, "CommandRegistry cannot be null");
        this.authService = Objects.requireNonNull(authService, "AuthenticationService cannot be null");
        this.requestLogger = Objects.requireNonNull(requestLogger, "RequestLogger cannot be null");
        this.defaultCommand = Objects.requireNonNull(defaultCommand, "DefaultCommand cannot be null");
    }

    /**
     * Registers a command for a specific path.
     *
     * <p>This is a convenience method that delegates to the command registry.
     *
     * @param path the request path (e.g., "/home", "/login")
     * @param command the command to handle requests for this path
     */
    public void registerCommand(String path, Command command) {
        commandRegistry.register(path, command);
    }

    /**
     * Authenticates a user with the given username and password.
     *
     * <p>This is a convenience method that delegates to the authentication service.
     *
     * @param username the username
     * @param password the password
     * @return true if authentication succeeded
     */
    public boolean authenticate(String username, String password) {
        return authService.authenticate(username, password);
    }

    /**
     * Handles an incoming request by applying cross-cutting concerns and dispatching
     * to the appropriate command.
     *
     * <p>The request processing flow is:
     * <ol>
     *   <li>Log the incoming request</li>
     *   <li>Check if the path requires authentication</li>
     *   <li>If authentication is required and user is not authenticated, return redirect</li>
     *   <li>Look up the command for the path</li>
     *   <li>If no command found, return 404 error</li>
     *   <li>Execute the command</li>
     *   <li>Log the outgoing response</li>
     *   <li>Return the response</li>
     * </ol>
     *
     * <p>This method is thread-safe and can be called concurrently.
     *
     * @param request the incoming request
     * @return the response to be sent back
     * @throws IllegalArgumentException if request is null
     */
    public Response handleRequest(Request request) {
        Objects.requireNonNull(request, "Request cannot be null");

        // Log incoming request
        requestLogger.logRequest(request);

        // Check if authentication is required
        if (requiresAuthentication(request.path()) && !authService.isAuthenticated(getUsernameFromRequest(request))) {
            Response redirectResponse = Response.redirect("/login");
            requestLogger.logResponse(redirectResponse);
            return redirectResponse;
        }

        // Look up command
        Command command = commandRegistry.get(request.path());
        if (command == null) {
            Response errorResponse = defaultCommand.execute(request);
            requestLogger.logResponse(errorResponse);
            return errorResponse;
        }

        // Execute command
        Response response = command.execute(request);
        requestLogger.logResponse(response);
        return response;
    }

    /**
     * Determines if a path requires authentication.
     *
     * <p>This method can be overridden or extended to implement custom authorization rules.
     *
     * @param path the request path
     * @return true if the path requires authentication
     */
    protected boolean requiresAuthentication(String path) {
        return "/dashboard".equals(path) || "/admin".equals(path);
    }

    /**
     * Extracts the username from the request.
     *
     * <p>This is a simplified implementation. In a real application, this would
     * extract the username from a session or token.
     *
     * @param request the request
     * @return the username, or null if not found
     */
    private String getUsernameFromRequest(Request request) {
        // In a real application, this would extract from session/token
        // For demo purposes, we'll check a specific parameter
        return request.param("username");
    }

    /**
     * Main method demonstrating the Front Controller pattern.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        demonstrate();
    }

    /**
     * Demonstrates the Front Controller pattern with a complete example.
     */
    public static void demonstrate() {
        System.out.println("\n=== Front Controller Pattern ===");
        System.out.println("Centralized request handling with cross-cutting concerns\n");

        // Create dependencies
        CommandRegistry registry = new CommandRegistry();
        AuthenticationService authService = new AuthenticationService();
        RequestLogger logger = new RequestLogger();

        // Create front controller
        FrontController controller = new FrontController(registry, authService, logger);

        // Register commands
        controller.registerCommand("/home", new HomeCommand());
        controller.registerCommand("/login", new LoginCommand(authService));
        controller.registerCommand("/dashboard", new DashboardCommand());

        // --- Process requests ---------------------------------------------------
        System.out.println("--- Processing requests ---");

        controller.handleRequest(Request.of("/home"));
        System.out.println();

        controller.handleRequest(Request.of("/login", Map.of("username", "alice", "password", "secret")));
        System.out.println();

        // Try accessing dashboard without authentication
        controller.handleRequest(Request.of("/dashboard"));
        System.out.println();

        // Authenticate user
        System.out.println("[Demo] Authenticating user 'alice'...");
        controller.authenticate("alice", "secret");
        System.out.println();

        // Try accessing dashboard after authentication
        controller.handleRequest(Request.of("/dashboard"));
        System.out.println();

        // Unknown route
        controller.handleRequest(Request.of("/unknown"));
        System.out.println();

        System.out.println("Benefits:");
        System.out.println("- Centralized request handling and cross-cutting concerns");
        System.out.println("- Single point for authentication, authorization, logging");
        System.out.println("- Easy to add new commands without changing the controller");
        System.out.println("- Consistent request processing across the application");
    }
}