package com.javastarterkit.patterns.frontcontroller.commands;

import com.javastarterkit.patterns.frontcontroller.Command;
import com.javastarterkit.patterns.frontcontroller.Request;
import com.javastarterkit.patterns.frontcontroller.Response;

/**
 * Command that handles the home page request.
 *
 * <p>This command is publicly accessible and returns a welcome message.
 * It is stateless and thread-safe.
 */
public class HomeCommand implements Command {

    /**
     * Executes the home page command.
     *
     * @param request the incoming request
     * @return a successful response with a welcome message
     */
    @Override
    public Response execute(Request request) {
        return Response.ok("Welcome to the home page!");
    }
}