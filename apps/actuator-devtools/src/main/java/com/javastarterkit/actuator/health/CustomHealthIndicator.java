// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.actuator.health;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Custom health check component demonstrating Actuator concepts.
 */
@Component
public class CustomHealthIndicator {

    @Value("${spring.application.name:unknown}")
    private String appName;

    public String getHealthStatus() {
        // Perform custom health check logic here
        boolean isHealthy = checkCustomService();

        if (isHealthy) {
            return "UP - " + appName;
        } else {
            return "DOWN - " + appName;
        }
    }

    private boolean checkCustomService() {
        // Simulate health check
        return true;
    }
}
