package com.javastarterkit.patterns.modelviewpresenter.model;

import java.time.Instant;
import java.util.Objects;

/**
 * Immutable record representing a user notification.
 */
public record Notification(
        String id,
        String message,
        NotificationType type,
        Instant timestamp,
        boolean read
) {
    public Notification {
        Objects.requireNonNull(id, "Notification ID must not be null");
        Objects.requireNonNull(message, "Message must not be null");
        Objects.requireNonNull(type, "Notification type must not be null");
        Objects.requireNonNull(timestamp, "Timestamp must not be null");
    }

    /**
     * Returns a new Notification marked as read.
     */
    public Notification markAsRead() {
        return new Notification(id, message, type, timestamp, true);
    }

    /**
     * Factory method for creating an informational notification.
     */
    public static Notification info(String id, String message) {
        return new Notification(id, message, NotificationType.INFO, Instant.now(), false);
    }

    /**
     * Factory method for creating a success notification.
     */
    public static Notification success(String id, String message) {
        return new Notification(id, message, NotificationType.SUCCESS, Instant.now(), false);
    }

    /**
     * Factory method for creating a warning notification.
     */
    public static Notification warning(String id, String message) {
        return new Notification(id, message, NotificationType.WARNING, Instant.now(), false);
    }

    /**
     * Factory method for creating an error notification.
     */
    public static Notification error(String id, String message) {
        return new Notification(id, message, NotificationType.ERROR, Instant.now(), false);
    }
}