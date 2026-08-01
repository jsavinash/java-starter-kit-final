package com.javastarterkit.patterns.composablearchitecture.exception;

/**
 * Base unchecked exception for the composable architecture.
 *
 * <p>All domain and runtime errors in this package extend this type so callers
 * can catch a single supertype, or rely on the specific subtypes
 * ({@link InvalidPizzaException}, {@link InvalidDeliveryException}) for
 * granular handling. Extends {@link RuntimeException} so that failures surface
 * without forcing checked-exception boilerplate on feature code.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class ComposableArchitectureException extends RuntimeException {

    /**
     * Creates an exception with a message.
     *
     * @param message the detail message
     */
    public ComposableArchitectureException(String message) {
        super(message);
    }

    /**
     * Creates an exception with a message and cause.
     *
     * @param message the detail message
     * @param cause   the underlying cause
     */
    public ComposableArchitectureException(String message, Throwable cause) {
        super(message, cause);
    }
}