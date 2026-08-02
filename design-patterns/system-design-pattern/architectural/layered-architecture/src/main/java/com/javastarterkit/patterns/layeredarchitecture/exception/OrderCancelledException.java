package com.javastarterkit.patterns.layeredarchitecture.exception;

/**
 * Thrown when a business operation is attempted on an order that has already been cancelled.
 */
public class OrderCancelledException extends LayeredArchitectureException {

    public OrderCancelledException(String message) {
        super(message);
    }
}
