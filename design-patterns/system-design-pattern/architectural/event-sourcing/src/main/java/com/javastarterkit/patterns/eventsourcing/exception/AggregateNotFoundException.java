package com.javastarterkit.patterns.eventsourcing.exception;

/**
 * Thrown when attempting to load an aggregate that does not exist.
 *
 * <p>This occurs when loading an account by ID for which no event stream
 * exists — i.e., the account was never opened.
 */
public final class AggregateNotFoundException extends DomainException {

    public AggregateNotFoundException(String message) {
        super(message);
    }
}