package com.javastarterkit.patterns.frontcontroller;

import java.util.concurrent.locks.ReentrantLock;
import java.util.Objects;

/**
 * Thread-safe request logger for logging incoming requests and outgoing responses.
 *
 * <p>Uses ReentrantLock to ensure thread-safe logging operations.
 */
public class RequestLogger {

    private final ReentrantLock lock = new ReentrantLock();

    /**
     * Logs an incoming request.
     *
     * <p>This operation is thread-safe.
     *
     * @param request the request to log
     * @throws IllegalArgumentException if request is null
     */
    public void logRequest(Request request) {
        Objects.requireNonNull(request, "Request cannot be null");
        lock.lock();
        try {
            System.out.println("[Logger] Incoming request: " + request.path() + " at " + System.currentTimeMillis());
        } finally {
            lock.unlock();
        }
    }

    /**
     * Logs an outgoing response.
     *
     * <p>This operation is thread-safe.
     *
     * @param response the response to log
     * @throws IllegalArgumentException if response is null
     */
    public void logResponse(Response response) {
        Objects.requireNonNull(response, "Response cannot be null");
        lock.lock();
        try {
            System.out.println("[Logger] Outgoing response: " + response.status() + " at " + System.currentTimeMillis());
        } finally {
            lock.unlock();
        }
    }

    /**
     * Logs an error message.
     *
     * <p>This operation is thread-safe.
     *
     * @param message the error message to log
     */
    public void logError(String message) {
        Objects.requireNonNull(message, "Message cannot be null");
        lock.lock();
        try {
            System.out.println("[Logger] Error: " + message + " at " + System.currentTimeMillis());
        } finally {
            lock.unlock();
        }
    }
}