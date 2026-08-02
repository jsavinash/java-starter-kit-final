package com.javastarterkit.patterns.eventsourcing.exception;

/**
 * Base runtime exception for all domain-level errors in the Event Sourcing system.
 *
 * <p>Thrown when a business invariant is violated (e.g., insufficient funds,
 * depositing into a closed account, or a negative initial balance).
 */
public class DomainException extends RuntimeException {

    public DomainException(String message) {
        super(message);
    }

    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }
}