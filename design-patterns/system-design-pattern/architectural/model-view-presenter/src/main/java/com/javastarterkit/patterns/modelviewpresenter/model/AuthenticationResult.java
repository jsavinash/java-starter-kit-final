package com.javastarterkit.patterns.modelviewpresenter.model;

import java.util.Objects;
import java.util.Optional;

/**
 * Immutable record representing the result of an authentication attempt.
 * Encapsulates success/failure state, session information, and user data.
 */
public record AuthenticationResult(
        boolean success,
        String sessionId,
        User user,
        String errorMessage
) {
    /**
     * Factory method for successful authentication.
     */
    public static AuthenticationResult success(String sessionId, User user) {
        Objects.requireNonNull(sessionId, "Session ID must not be null");
        Objects.requireNonNull(user, "User must not be null");
        return new AuthenticationResult(true, sessionId, user, null);
    }

    /**
     * Factory method for failed authentication.
     */
    public static AuthenticationResult failure(String errorMessage) {
        Objects.requireNonNull(errorMessage, "Error message must not be null");
        return new AuthenticationResult(false, null, null, errorMessage);
    }

    public Optional<User> getUser() {
        return Optional.ofNullable(user);
    }

    public Optional<String> getSessionId() {
        return Optional.ofNullable(sessionId);
    }

    public Optional<String> getErrorMessage() {
        return Optional.ofNullable(errorMessage);
    }
}