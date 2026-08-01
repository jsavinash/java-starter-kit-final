package com.javastarterkit.patterns.composablearchitecture.exception;

/**
 * Thrown when a delivery configuration is invalid (e.g., a blank address or an
 * empty city).
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class InvalidDeliveryException extends ComposableArchitectureException {

    /**
     * Creates an exception with a message.
     *
     * @param message the detail message
     */
    public InvalidDeliveryException(String message) {
        super(message);
    }
}