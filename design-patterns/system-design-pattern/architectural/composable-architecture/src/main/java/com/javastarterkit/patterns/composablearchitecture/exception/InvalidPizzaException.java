package com.javastarterkit.patterns.composablearchitecture.exception;

/**
 * Thrown when a pizza configuration is invalid (e.g., no size selected, an
 * unknown topping, or a negative quantity).
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class InvalidPizzaException extends ComposableArchitectureException {

    /**
     * Creates an exception with a message.
     *
     * @param message the detail message
     */
    public InvalidPizzaException(String message) {
        super(message);
    }
}