package com.javastarterkit.patterns.hexagonalarchitecture.adapters.driven;

import com.javastarterkit.patterns.hexagonalarchitecture.ports.NotificationPort;

/**
 * Driven adapter: SMS-based notification implementation.
 *
 * <p>This adapter sends notifications via SMS.
 * In a real system, this would use an SMS gateway or service.
 */
public class SmsNotificationAdapter implements NotificationPort {

    /**
     * Sends an SMS notification.
     *
     * @param recipient the recipient phone number
     * @param message the notification message
     */
    @Override
    public void notify(String recipient, String message) {
        System.out.println("  [SMS]   To: " + recipient + " | " + message);
    }
}