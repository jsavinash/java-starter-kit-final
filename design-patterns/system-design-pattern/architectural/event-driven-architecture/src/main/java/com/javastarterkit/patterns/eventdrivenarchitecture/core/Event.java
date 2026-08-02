package com.javastarterkit.patterns.eventdrivenarchitecture.core;

import java.time.Instant;
import java.util.UUID;

/**
 * Base interface for all domain events in the event-driven architecture.
 * Every event carries a unique identifier, a timestamp, and a source.
 *
 * <p>Events are immutable value objects that describe something that
 * has already happened in the system.</p>
 */
public interface Event {

    /**
     * @return unique event identifier
     */
    UUID eventId();

    /**
     * @return timestamp when the event occurred
     */
    Instant occurredAt();

    /**
     * @return source component that produced the event
     */
    String source();
}