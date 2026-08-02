package com.javastarterkit.patterns.frontcontroller.commands;

import com.javastarterkit.patterns.frontcontroller.AuthenticationService;
import com.javastarterkit.patterns.frontcontroller.Command;
import com.javastarterkit.patterns.frontcontroller.Request;
import com.javastarterkit.patterns.frontcontroller.Response;
import java.util.Objects;

/**
 * Command that handles user login requests.
 *
 * <p>This command validates credentials and delegates authentication to the
 * AuthenticationService. It is stateless and thread-safe.
 */
public class LoginCommand implements Command {

    private final AuthenticationService authService;

    /**
     * Creates a new LoginCommand with the given authentication service.
     *
     * @param authService the authentication service to use
     * @throws IllegalArgumentException if authService is null
     */
    public LoginCommand(AuthenticationService authService) {
        this.authService = Objects.requireNonNull(authService, "AuthenticationService cannot be null");
    }

    /**
     * Executes the login command.
     *
     * <p>Expects username and password parameters in the request.
     *
     * @param request the incoming request with username and password params
     * @return a success or error response
     */
    @Override
    public Response execute(Request request) {
        String username = request.param("username");
        String password = request.param("password");

        if (username == null || password == null) {
            return Response.error("Invalid credentials: username and password required");
        }

        boolean authenticated = authService.authenticate(username, password);
        if (authenticated) {
            return Response.ok("Login successful for user: " + username);
        } else {
            return Response.error("Invalid credentials");
        }
    }
}