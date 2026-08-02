package com.javastarterkit.patterns.modelviewpresenter.service;

import com.javastarterkit.patterns.modelviewpresenter.model.AuthenticationResult;
import com.javastarterkit.patterns.modelviewpresenter.model.Session;
import com.javastarterkit.patterns.modelviewpresenter.model.User;
import com.javastarterkit.patterns.modelviewpresenter.repository.UserRepository;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Service for user authentication and credential validation.
 * Provides login, logout, and session validation capabilities.
 */
public final class AuthenticationService {

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{3,20}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^.{6,100}$");

    private final UserRepository userRepository;
    private final SessionManager sessionManager;

    public AuthenticationService(UserRepository userRepository, SessionManager sessionManager) {
        this.userRepository = Objects.requireNonNull(userRepository, "UserRepository must not be null");
        this.sessionManager = Objects.requireNonNull(sessionManager, "SessionManager must not be null");
    }

    /**
     * Authenticates a user with username and password.
     */
    public AuthenticationResult authenticate(String username, String password) {
        Objects.requireNonNull(username, "Username must not be null");
        Objects.requireNonNull(password, "Password must not be null");

        if (!USERNAME_PATTERN.matcher(username).matches()) {
            return AuthenticationResult.failure("Invalid username format");
        }
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            return AuthenticationResult.failure("Invalid password format");
        }

        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null || !user.verifyPassword(password)) {
            return AuthenticationResult.failure("Invalid username or password");
        }

        User updatedUser = user.withUpdatedLastLogin();
        userRepository.save(updatedUser);

        Session session = sessionManager.createSession(updatedUser);
        return AuthenticationResult.success(session.id(), updatedUser);
    }

    /**
     * Logs out a user by invalidating their session.
     */
    public void logout(String sessionId) {
        Objects.requireNonNull(sessionId, "Session ID must not be null");
        sessionManager.invalidateSession(sessionId);
    }

    /**
     * Validates if a session is still active.
     */
    public boolean validateSession(String sessionId) {
        Objects.requireNonNull(sessionId, "Session ID must not be null");
        return sessionManager.isValid(sessionId);
    }

    /**
     * Gets the current user from a session.
     */
    public User getCurrentUser(String sessionId) {
        Objects.requireNonNull(sessionId, "Session ID must not be null");
        return sessionManager.getSession(sessionId)
                .map(Session::userId)
                .flatMap(userRepository::findById)
                .orElse(null);
    }
}