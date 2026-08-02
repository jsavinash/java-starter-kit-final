package com.javastarterkit.patterns.modelviewpresenter.repository;

import com.javastarterkit.patterns.modelviewpresenter.model.User;
import java.util.Optional;

/**
 * Repository interface for User aggregate persistence.
 * Provides thread-safe operations for user CRUD.
 */
public interface UserRepository {

    /**
     * Finds a user by their unique ID.
     */
    Optional<User> findById(String userId);

    /**
     * Finds a user by their username (must be unique).
     */
    Optional<User> findByUsername(String username);

    /**
     * Saves a user (create or update).
     */
    User save(User user);

    /**
     * Deletes a user by ID.
     */
    void deleteById(String userId);

    /**
     * Checks if a username already exists.
     */
    boolean existsByUsername(String username);
}