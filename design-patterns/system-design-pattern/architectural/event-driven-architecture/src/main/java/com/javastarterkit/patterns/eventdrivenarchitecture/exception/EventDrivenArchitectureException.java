package com.javastarterkit.patterns.eventdrivenarchitecture.exception;

/**
 * Base exception for the event-driven architecture module.
 */
public class EventDrivenArchitectureException extends RuntimeException {

    /**
     * @param message the detail message
     */
    public EventDrivenArchitectureException(String message) {
        super(message);
    }

    /**
     * @param message the detail message
     * @param cause   the root cause
     */
    public EventDrivenArchitectureException(String message, Throwable cause) {
        super(message, cause);
    }
}