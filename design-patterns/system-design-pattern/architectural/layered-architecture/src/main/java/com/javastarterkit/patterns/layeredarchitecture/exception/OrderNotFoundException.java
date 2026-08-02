package com.javastarterkit.patterns.layeredarchitecture.exception;

/**
 * Thrown when an order cannot be found by its identifier in the repository.
 */
public class OrderNotFoundException extends LayeredArchitectureException {

    public OrderNotFoundException(String message) {
        super(message);
    }
}
