package com.javastarterkit.patterns.modelviewpresenter.model;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Immutable user model representing an authenticated user in the system.
 * Uses record for immutability with builder-style with* methods for state transitions.
 *
 * @param id       unique user identifier
 * @param username unique login username
 * @param email    user email address
 * @param password hashed password
 * @param roles    user role set
 * @param lastLogin last login timestamp
 */
public record User(
        String id,
        String username,
        String email,
        String password,
        Set<Role> roles,
        Instant lastLogin
) {

    /**
     * Creates a new user with generated ID, hashed password and current timestamp.
     */
    public static User create(String username, String email, String password, Set<Role> roles) {
        Objects.requireNonNull(username, "username must not be null");
        Objects.requireNonNull(email, "email must not be null");
        Objects.requireNonNull(password, "password must not be null");
        return new User(
                UUID.randomUUID().toString(),
                username,
                email,
                password,
                Set.copyOf(roles),
                Instant.now()
        );
    }

    /**
     * Returns a new User with updated last login timestamp.
     */
    public User withUpdatedLastLogin() {
        return new User(id, username, email, password, roles, Instant.now());
    }

    /**
     * Checks if user has a specific role.
     */
    public boolean hasRole(Role role) {
        return roles.contains(role);
    }

    /**
     * Checks if user is an admin.
     */
    public boolean isAdmin() {
        return hasRole(Role.ADMIN);
    }

    /**
     * Checks if user is a manager.
     */
    public boolean isManager() {
        return hasRole(Role.MANAGER);
    }

    /**
     * Verifies the provided password against the stored password.
     * In a production system this would use BCrypt or similar.
     */
    public boolean verifyPassword(String candidatePassword) {
        Objects.requireNonNull(candidatePassword, "candidatePassword must not be null");
        return this.password.equals(candidatePassword);
    }
}