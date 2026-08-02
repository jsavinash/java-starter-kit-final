package com.javastarterkit.patterns.frontcontroller;

/**
 * Functional interface for command pattern implementations.
 *
 * <p>Each command handles a specific type of request and returns a response.
 * Commands are stateless and thread-safe, making them suitable for concurrent execution.
 *
 * @see FrontController
 */
@FunctionalInterface
public interface Command {

    /**
     * Executes the command with the given request.
     *
     * @param request the incoming request
     * @return the response to be sent back
     */
    Response execute(Request request);
}