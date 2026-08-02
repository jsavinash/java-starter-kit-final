package com.javastarterkit.patterns.hexagonalarchitecture.ports;

/**
 * Outbound port (driven): notification contract.
 *
 * <p>This interface defines the contract for sending notifications.
 * The application layer depends on this interface, not on concrete implementations.
 * This allows swapping different notification mechanisms (email, SMS, push, etc.)
 * without changing the application logic.
 */
public interface NotificationPort {

    /**
     * Sends a notification to the specified recipient.
     *
     * @param recipient the recipient identifier (e.g., email, phone number)
     * @param message the notification message
     */
    void notify(String recipient, String message);
}