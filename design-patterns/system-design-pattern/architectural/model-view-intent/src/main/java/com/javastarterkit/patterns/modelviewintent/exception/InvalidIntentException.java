package com.javastarterkit.patterns.modelviewintent.exception;

/**
 * Thrown when an intent cannot be processed by the reducer.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class InvalidIntentException extends MviException {

    public InvalidIntentException(String message) {
        super(message);
    }
}