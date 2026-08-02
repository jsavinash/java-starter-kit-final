package com.javastarterkit.patterns.eventdrivenarchitecture.exception;

/**
 * Thrown when an order is invalid (blank ID, blank email, non-positive quantity).
 */
public class InvalidOrderException extends EventDrivenArchitectureException {

    /**
     * @param message the detail message
     */
    public InvalidOrderException(String message) {
        super(message);
    }
}