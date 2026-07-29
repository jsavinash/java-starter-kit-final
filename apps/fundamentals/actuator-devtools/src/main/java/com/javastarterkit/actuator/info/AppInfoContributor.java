// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.actuator.info;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

/**
 * Custom info contributor demonstrating Actuator info endpoint.
 */
@Component
public class AppInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, Object> appDetails = new HashMap<>();
        appDetails.put("name", "Spring Boot Actuator & DevTools Example");
        appDetails.put("version", "1.0.0-SNAPSHOT");
        appDetails.put("description", "Demonstrates Actuator and DevTools features");
        appDetails.put("javaVersion", Runtime.version().toString());
        appDetails.put("activeProfiles", "default");

        builder.withDetail("application", appDetails);
    }
}
