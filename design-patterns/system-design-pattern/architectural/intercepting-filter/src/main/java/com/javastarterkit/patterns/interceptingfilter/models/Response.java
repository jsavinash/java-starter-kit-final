package com.javastarterkit.patterns.interceptingfilter.models;

import java.util.Objects;

/**
 * Mutable response object that flows through the filter chain.
 *
 * <p>This response is mutated by filters and the target handler.
 * Each request gets its own Response instance, ensuring thread-safety.
 */
public class Response {
    private int status;
    private String body;

    /**
     * Creates a new Response with default status 200 and empty body.
     */
    public Response() {
        this.status = 200;
        this.body = "";
    }

    /**
     * Sets the HTTP status code.
     *
     * @param status the HTTP status code (e.g., 200, 403, 404)
     * @return this Response for method chaining
     */
    public Response status(int status) {
        this.status = status;
        return this;
    }

    /**
     * Sets the response body.
     *
     * @param body the response body
     * @return this Response for method chaining
     */
    public Response body(String body) {
        this.body = Objects.requireNonNull(body, "Body cannot be null");
        return this;
    }

    /**
     * Returns the HTTP status code.
     *
     * @return the status code
     */
    public int status() {
        return status;
    }

    /**
     * Returns the response body.
     *
     * @return the body
     */
    public String body() {
        return body;
    }

    @Override
    public String toString() {
        return "Response{status=" + status + ", body='" + body + "'}";
    }
}