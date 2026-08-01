package com.javastarterkit.patterns.commandqueryresponsibilitysegregation.exception;

/**
 * Thrown when the {@link com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.command.CommandBus
 * CommandBus} or
 * {@link com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.query.QueryBus
 * QueryBus} receives a command or query for which no handler has been registered.
 */
public class HandlerNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public HandlerNotFoundException(String message) {
        super(message);
    }

    public HandlerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
