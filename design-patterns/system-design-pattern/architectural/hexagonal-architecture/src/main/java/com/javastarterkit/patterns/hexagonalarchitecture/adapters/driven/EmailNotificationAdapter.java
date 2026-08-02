package com.javastarterkit.patterns.hexagonalarchitecture.adapters.driven;

import com.javastarterkit.patterns.hexagonalarchitecture.ports.NotificationPort;

/**
 * Driven adapter: email-based notification implementation.
 *
 * <p>This adapter sends notifications via email.
 * In a real system, this would use an email service or SMTP.
 */
public class EmailNotificationAdapter implements NotificationPort {

    @Override
    public void notify(String recipient, String message) {
        System.out.println("  [EMAIL] To: " + recipient + " | " + message);
    }
}