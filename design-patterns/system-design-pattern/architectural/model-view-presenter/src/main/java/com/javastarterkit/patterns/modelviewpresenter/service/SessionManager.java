package com.javastarterkit.patterns.modelviewpresenter.service;

import com.javastarterkit.patterns.modelviewpresenter.model.Session;
import com.javastarterkit.patterns.modelviewpresenter.model.User;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Thread-safe session management service.
 * Handles session creation, validation, and cleanup of expired sessions.
 */
public final class SessionManager {

    private static final Duration DEFAULT_SESSION_TIMEOUT = Duration.ofHours(24);

    private final Map<String, Session> sessions = new ConcurrentHashMap<>();
    private final Duration sessionTimeout;

    public SessionManager() {
        this(DEFAULT_SESSION_TIMEOUT);
    }

    public SessionManager(Duration sessionTimeout) {
        this.sessionTimeout = Objects.requireNonNull(sessionTimeout, "Session timeout must not be null");
    }

    /**
     * Creates a new session for the given user.
     */
    public Session createSession(User user) {
        Objects.requireNonNull(user, "User must not be null");
        String sessionId = generateSessionId();
        Instant now = Instant.now();
        Session session = new Session(sessionId, user.id(), now, now);
        sessions.put(sessionId, session);
        return session;
    }

    /**
     * Retrieves a session by ID, or empty if not found or expired.
     */
    public Optional<Session> getSession(String sessionId) {
        Objects.requireNonNull(sessionId, "Session ID must not be null");
        Session session = sessions.get(sessionId);
        if (session == null) {
            return Optional.empty();
        }
        if (session.isExpired(sessionTimeout)) {
            sessions.remove(sessionId);
            return Optional.empty();
        }
        // Update last accessed time
        Session touched = session.withTouched();
        sessions.put(sessionId, touched);
        return Optional.of(touched);
    }

    /**
     * Invalidates (removes) a session.
     */
    public void invalidateSession(String sessionId) {
        Objects.requireNonNull(sessionId, "Session ID must not be null");
        sessions.remove(sessionId);
    }

    /**
     * Validates if a session exists and is not expired.
     */
    public boolean isValid(String sessionId) {
        return getSession(sessionId).isPresent();
    }

    /**
     * Removes all expired sessions from memory.
     */
    public void cleanupExpiredSessions() {
        sessions.entrySet().removeIf(entry -> entry.getValue().isExpired(sessionTimeout));
    }

    private String generateSessionId() {
        return UUID.randomUUID().toString();
    }
}