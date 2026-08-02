package com.javastarterkit.patterns.layeredarchitecture.exception;

/**
 * Base exception for all errors in the Layered Architecture pattern.
 *
 * <p>All domain-level exceptions in the layered architecture hierarchy extend this class,
 * providing a single catch-point for application-level error handling.
 */
public class LayeredArchitectureException extends RuntimeException {

    public LayeredArchitectureException(String message) {
        super(message);
    }

    public LayeredArchitectureException(String message, Throwable cause) {
        super(message, cause);
    }
}
