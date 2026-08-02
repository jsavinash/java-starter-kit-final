package com.javastarterkit.patterns.frontcontroller;

/**
 * Immutable response record representing an HTTP-like response.
 *
 * <p>This record is immutable by design, making it inherently thread-safe.
 *
 * @param status the HTTP status line (e.g., "200 OK", "404 Not Found")
 * @param body the response body content
 */
public record Response(String status, String body) {

    /**
     * Creates a successful 200 OK response.
     *
     * @param body the response body
     * @return a new Response with 200 OK status
     */
    public static Response ok(String body) {
        return new Response("200 OK", body);
    }

    /**
     * Creates a redirect response.
     *
     * @param location the redirect location
     * @return a new Response with 302 Found status
     */
    public static Response redirect(String location) {
        return new Response("302 Found", "Redirecting to " + location);
    }

    /**
     * Creates an error response.
     *
     * @param message the error message
     * @return a new Response with 500 Internal Server Error status
     */
    public static Response error(String message) {
        return new Response("500 Internal Server Error", message);
    }

    /**
     * Returns whether this response is successful (2xx status).
     *
     * @return true if the response is successful
     */
    public boolean isSuccess() {
        return status != null && status.startsWith("2");
    }

    /**
     * Returns whether this response is a redirect (3xx status).
     *
     * @return true if the response is a redirect
     */
    public boolean isRedirect() {
        return status != null && status.startsWith("3");
    }

    /**
     * Returns whether this response is an error (4xx or 5xx status).
     *
     * @return true if the response is an error
     */
    public boolean isError() {
        return status != null && (status.startsWith("4") || status.startsWith("5"));
    }
}