package com.javastarterkit.patterns.frontcontroller;

import java.util.Collections;
import java.util.Map;

/**
 * Immutable request record representing an HTTP-like request.
 *
 * <p>This record is immutable by design, making it inherently thread-safe.
 *
 * @param path the request path (e.g., "/home", "/login")
 * @param params query parameters or form data
 */
public record Request(String path, Map<String, String> params) {

    /**
     * Creates a new Request with the given path and no parameters.
     *
     * @param path the request path
     * @return a new Request instance
     */
    public static Request of(String path) {
        return new Request(path, Collections.emptyMap());
    }

    /**
     * Creates a new Request with the given path and parameters.
     *
     * @param path the request path
     * @param params the request parameters
     * @return a new Request instance
     */
    public static Request of(String path, Map<String, String> params) {
        return new Request(path, params != null ? Collections.unmodifiableMap(params) : Collections.emptyMap());
    }

    /**
     * Retrieves a parameter value by key.
     *
     * @param key the parameter key
     * @return the parameter value, or null if not found
     */
    public String param(String key) {
        return params != null ? params.get(key) : null;
    }

    /**
     * Retrieves a parameter value by key with a default value.
     *
     * @param key the parameter key
     * @param defaultValue the default value to return if key not found
     * @return the parameter value, or defaultValue if not found
     */
    public String param(String key, String defaultValue) {
        return params != null ? params.getOrDefault(key, defaultValue) : defaultValue;
    }
}