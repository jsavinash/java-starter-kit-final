package com.javastarterkit.patterns.modelviewcontroller.exception;

/**
 * Base runtime exception for all Model-View-Controller domain errors.
 *
 * <p>All domain-specific exceptions in the MVC pattern extend this class,
 * providing a consistent error hierarchy for the application.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class MvcException extends RuntimeException {

    /**
     * Constructs a new MVC exception with the specified detail message.
     *
     * @param message the detail message
     */
    public MvcException(String message) {
        super(message);
    }

    /**
     * Constructs a new MVC exception with the specified detail message
     * and cause.
     *
     * @param message the detail message
     * @param cause   the underlying cause
     */
    public MvcException(String message, Throwable cause) {
        super(message, cause);
    }
}