package com.javastarterkit.patterns.microservicesaggregator.exception;

/**
 * Thrown when a downstream microservice is unavailable or fails to respond.
 *
 * <p>This exception signals a transient infrastructure failure, allowing the
 * aggregator to apply fallback strategies or return partial results.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class ServiceUnavailableException extends AggregatorException {

    /**
     * Constructs a new service-unavailable exception with the specified message.
     *
     * @param message the detail message
     */
    public ServiceUnavailableException(String message) {
        super(message);
    }

    /**
     * Constructs a new service-unavailable exception with the specified message
     * and cause.
     *
     * @param message the detail message
     * @param cause   the underlying cause
     */
    public ServiceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}