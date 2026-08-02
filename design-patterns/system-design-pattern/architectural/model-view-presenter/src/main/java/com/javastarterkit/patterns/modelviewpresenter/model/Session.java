package com.javastarterkit.patterns.modelviewpresenter.model;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

/**
 * Immutable record representing an authenticated user session.
 * Each session has a unique ID, associated user ID, creation timestamp,
 * and last-accessed timestamp for expiry checking.
 */
public record Session(
        String id,
        String userId,
        Instant createdAt,
        Instant lastAccessedAt
) {
    public Session {
        Objects.requireNonNull(id, "Session ID must not be null");
        Objects.requireNonNull(userId, "User ID must not be null");
        Objects.requireNonNull(createdAt, "Created timestamp must not be null");
        Objects.requireNonNull(lastAccessedAt, "Last accessed timestamp must not be null");
    }

    /**
     * Returns a new Session with an updated last-accessed timestamp.
     * Used to extend session lifetime on each authenticated request.
     */
    public Session withTouched() {
        return new Session(id, userId, createdAt, Instant.now());
    }

    /**
     * Returns true if the session has been inactive longer than the given timeout duration.
     */
    public boolean isExpired(Duration timeout) {
        Objects.requireNonNull(timeout, "Timeout duration must not be null");
        return Duration.between(lastAccessedAt, Instant.now()).compareTo(timeout) > 0;
    }
}