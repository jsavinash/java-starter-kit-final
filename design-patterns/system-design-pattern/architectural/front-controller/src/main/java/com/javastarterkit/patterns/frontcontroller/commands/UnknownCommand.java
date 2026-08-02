package com.javastarterkit.patterns.frontcontroller.commands;

import com.javastarterkit.patterns.frontcontroller.Command;
import com.javastarterkit.patterns.frontcontroller.Request;
import com.javastarterkit.patterns.frontcontroller.Response;

/**
 * Default command for handling unknown/unregistered routes.
 *
 * <p>This command returns a 404 error response. It is stateless and thread-safe.
 */
public class UnknownCommand implements Command {

    /**
     * Executes the unknown command.
     *
     * @param request the incoming request
     * @return an error response with 404 status
     */
    @Override
    public Response execute(Request request) {
        return Response.error("404 Not Found - No handler registered for path: " + request.path());
    }
}