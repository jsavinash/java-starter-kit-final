// Copyright © 2012-2024 Java Starter Kit. All rights reserved.
package com.javastarterkit.greeting.service;

import com.javastarterkit.greeting.config.GreetingProperties;
import java.time.LocalTime;

/**
 * Default implementation of {@link GreetingService}.
 *
 * @author Spring Boot Engineering
 * @since 3.3.0
 */
public class DefaultGreetingService implements GreetingService {

    private final GreetingProperties properties;

    public DefaultGreetingService(GreetingProperties properties) {
        this.properties = properties;
    }

    @Override
    public String greet(String name) {
        return greet(name, this.properties.getPrefix());
    }

    @Override
    public String greet(String name, String prefix) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name must not be empty");
        }
        if (prefix == null || prefix.trim().isEmpty()) {
            throw new IllegalArgumentException("Prefix must not be empty");
        }
        String greeting = prefix + ", " + name + this.properties.getSuffix();
        if (this.properties.isIncludeTime()) {
            greeting = greeting + " The time is " + LocalTime.now().toString().substring(0, 5);
        }
        return greeting;
    }
}
