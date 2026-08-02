package com.javastarterkit.patterns.modelviewpresenter.repository;

import com.javastarterkit.patterns.modelviewpresenter.model.User;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Thread-safe in-memory implementation of UserRepository.
 * Uses ConcurrentHashMap for lock-free concurrent access.
 * Maintains a username index for unique username lookups.
 */
public final class InMemoryUserRepository implements UserRepository {

    private final Map<String, User> usersById = new ConcurrentHashMap<>();
    private final Map<String, String> usernameIndex = new ConcurrentHashMap<>();

    @Override
    public Optional<User> findById(String userId) {
        Objects.requireNonNull(userId, "User ID must not be null");
        return Optional.ofNullable(usersById.get(userId));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Objects.requireNonNull(username, "Username must not be null");
        return Optional.ofNullable(usernameIndex.get(username.toLowerCase()))
                .flatMap(id -> Optional.ofNullable(usersById.get(id)));
    }

    @Override
    public synchronized User save(User user) {
        Objects.requireNonNull(user, "User must not be null");
        String userId = user.id();
        String usernameKey = user.username().toLowerCase();

        String existingId = usernameIndex.get(usernameKey);
        if (existingId != null && !existingId.equals(userId)) {
            throw new IllegalArgumentException(
                    "Username '" + user.username() + "' already exists"
            );
        }

        User previous = usersById.put(userId, user);
        if (previous != null && !previous.username().equalsIgnoreCase(user.username())) {
            usernameIndex.remove(previous.username().toLowerCase());
        }
        usernameIndex.put(usernameKey, userId);
        return user;
    }

    @Override
    public void deleteById(String userId) {
        Objects.requireNonNull(userId, "User ID must not be null");
        User removed = usersById.remove(userId);
        if (removed != null) {
            usernameIndex.remove(removed.username().toLowerCase());
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        Objects.requireNonNull(username, "Username must not be null");
        return usernameIndex.containsKey(username.toLowerCase());
    }
}