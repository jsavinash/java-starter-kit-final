package com.javastarterkit.patterns.eventsourcing.exception;

/**
 * Thrown when an append operation detects a version conflict.
 *
 * <p>In Event Sourcing, the expected version of an aggregate is provided at
 * append time. If the event store's latest version no longer matches the
 * expected version, a concurrent writer has already appended new events.
 * The caller must reload the aggregate and retry the command.
 */
public final class OptimisticLockException extends DomainException {

    public OptimisticLockException(String message) {
        super(message);
    }
}