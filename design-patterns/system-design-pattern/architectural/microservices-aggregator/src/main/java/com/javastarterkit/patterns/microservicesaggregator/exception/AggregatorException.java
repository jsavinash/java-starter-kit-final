package com.javastarterkit.patterns.microservicesaggregator.exception;

/**
 * Base runtime exception for all microservices aggregator domain errors.
 *
 * <p>All domain-specific exceptions in the aggregator pattern extend this
 * class, providing a consistent error hierarchy for the client.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class AggregatorException extends RuntimeException {

    /**
     * Constructs a new aggregator exception with the specified detail message.
     *
     * @param message the detail message
     */
    public AggregatorException(String message) {
        super(message);
    }

    /**
     * Constructs a new aggregator exception with the specified detail message
     * and cause.
     *
     * @param message the detail message
     * @param cause   the underlying cause
     */
    public AggregatorException(String message, Throwable cause) {
        super(message, cause);
    }
}