package com.javastarterkit.patterns.commandqueryresponsibilitysegregation.exception;

/**
 * Thrown when an aggregate root cannot be found in the event store.
 *
 * <p>This typically happens when a command targets an aggregate ID that
 * has never been opened (no {@code AccountOpened} event exists in the stream).
 */
public class AggregateNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AggregateNotFoundException(String message) {
        super(message);
    }

    public AggregateNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
