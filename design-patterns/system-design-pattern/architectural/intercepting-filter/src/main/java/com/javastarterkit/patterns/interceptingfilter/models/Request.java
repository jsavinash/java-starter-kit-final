package com.javastarterkit.patterns.interceptingfilter.models;

import java.util.Objects;

/**
 * Immutable request record carrying HTTP method, user, path, and payload.
 *
 * <p>This record is immutable by design, making it inherently thread-safe.
 *
 * @param method the HTTP method (e.g., "GET", "POST")
 * @param user the authenticated user (empty string if unauthenticated)
 * @param path the request path
 * @param payload the request payload (e.g., JSON body)
 */
public record Request(String method, String user, String path, String payload) {

    /**
     * Creates a new Request with validation.
     *
     * @param method the HTTP method
     * @param user the authenticated user
     * @param path the request path
     * @param payload the request payload
     * @throws IllegalArgumentException if method or path is null
     */
    public Request {
        Objects.requireNonNull(method, "Method cannot be null");
        Objects.requireNonNull(path, "Path cannot be null");
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (payload == null) {
            throw new IllegalArgumentException("Payload cannot be null");
        }
    }

    /**
     * Checks if the user is authenticated.
     *
     * @return true if the user is not null and not empty
     */
    public boolean isAuthenticated() {
        return user != null && !user.isEmpty();
    }
}