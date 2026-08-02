package com.javastarterkit.patterns.frontcontroller.commands;

import com.javastarterkit.patterns.frontcontroller.Command;
import com.javastarterkit.patterns.frontcontroller.Request;
import com.javastarterkit.patterns.frontcontroller.Response;

/**
 * Command that handles the dashboard page request.
 *
 * <p>This command represents a protected resource that requires authentication.
 * The FrontController ensures authentication before dispatching to this command.
 * It is stateless and thread-safe.
 */
public class DashboardCommand implements Command {

    /**
     * Executes the dashboard command.
     *
     * @param request the incoming request
     * @return a successful response with dashboard content
     */
    @Override
    public Response execute(Request request) {
        return Response.ok("Welcome to your protected dashboard!");
    }
}