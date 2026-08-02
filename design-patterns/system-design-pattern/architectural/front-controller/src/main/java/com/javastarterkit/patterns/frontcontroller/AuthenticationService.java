package com.javastarterkit.patterns.frontcontroller;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Objects;

/**
 * Thread-safe authentication service for managing user authentication state.
 *
 * <p>Uses ConcurrentHashMap for lock-free reads and ReentrantLock for compound
 * operations requiring atomicity (e.g., login/logout).
 */
public class AuthenticationService {

    private final ConcurrentMap<String, User> authenticatedUsers = new ConcurrentHashMap<>();
    private final ReentrantLock lock = new ReentrantLock();

    /**
     * Authenticates a user with the given credentials.
     *
     * <p>This operation is thread-safe and uses a lock to ensure atomicity.
     *
     * @param username the username
     * @param password the password (in real applications, this would be hashed)
     * @return true if authentication succeeded, false otherwise
     */
    public boolean authenticate(String username, String password) {
        Objects.requireNonNull(username, "Username cannot be null");
        Objects.requireNonNull(password, "Password cannot be null");

        lock.lock();
        try {
            // Simulate authentication logic
            if (username != null && !username.isBlank() && password != null && !password.isBlank()) {
                User user = new User(username, true, System.currentTimeMillis());
                authenticatedUsers.put(username, user);
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Checks if a user is currently authenticated.
     *
     * <p>This operation is thread-safe and lock-free (read-only).
     *
     * @param username the username to check
     * @return true if the user is authenticated, false otherwise
     */
    public boolean isAuthenticated(String username) {
        if (username == null) {
            return false;
        }
        User user = authenticatedUsers.get(username);
        return user != null && user.authenticated();
    }

    /**
     * Logs out a user, removing their authentication state.
     *
     * <p>This operation is thread-safe and uses a lock to ensure atomicity.
     *
     * @param username the username to logout
     */
    public void logout(String username) {
        Objects.requireNonNull(username, "Username cannot be null");
        lock.lock();
        try {
            authenticatedUsers.remove(username);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns the number of currently authenticated users.
     *
     * @return the number of authenticated users
     */
    public int getAuthenticatedUserCount() {
        return authenticatedUsers.size();
    }

    /**
     * Clears all authenticated users.
     *
     * <p>This operation is thread-safe.
     */
    public void clearAll() {
        lock.lock();
        try {
            authenticatedUsers.clear();
        } finally {
            lock.unlock();
        }
    }

    /**
     * User record representing an authenticated user.
     *
     * @param username the username
     * @param authenticated whether the user is authenticated
     * @param loginTime the timestamp when the user logged in
     */
    public record User(String username, boolean authenticated, long loginTime) {
    }
}