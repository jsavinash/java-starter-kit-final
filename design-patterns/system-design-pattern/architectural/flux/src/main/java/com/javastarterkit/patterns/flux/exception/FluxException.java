package com.javastarterkit.patterns.flux.exception;

/**
 * Unchecked exception for Flux pattern errors.
 *
 * <p>Used to signal invalid state transitions, configuration errors,
 * or other runtime failures within the Flux implementation.
 */
public final class FluxException extends RuntimeException {

    /**
     * Creates a new FluxException with the given message.
     *
     * @param message the exception message; must not be null
     */
    public FluxException(final String message) {
        super(message);
    }

    /**
     * Creates a new FluxException with the given message and cause.
     *
     * @param message the exception message; must not be null
     * @param cause the underlying cause; may be null
     */
    public FluxException(final String message, final Throwable cause) {
        super(message, cause);
    }
}