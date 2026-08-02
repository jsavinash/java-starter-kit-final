package com.javastarterkit.patterns.microservicesaggregator.exception;

/**
 * Thrown when a requested product cannot be found in the product service.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class ProductNotFoundException extends AggregatorException {

    /**
     * Constructs a new product-not-found exception with the specified message.
     *
     * @param message the detail message
     */
    public ProductNotFoundException(String message) {
        super(message);
    }
}