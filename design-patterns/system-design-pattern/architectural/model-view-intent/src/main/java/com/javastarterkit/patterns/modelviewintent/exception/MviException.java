package com.javastarterkit.patterns.modelviewintent.exception;

/**
 * Base runtime exception for all Model-View-Intent domain errors.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class MviException extends RuntimeException {

    public MviException(String message) {
        super(message);
    }

    public MviException(String message, Throwable cause) {
        super(message, cause);
    }
}