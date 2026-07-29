// Copyright © 2012-2024 Java Starter Kit. All rights reserved.
package com.javastarterkit.greeting.service;

/**
 * Service interface for generating greetings.
 *
 * @author Spring Boot Engineering
 * @since 3.3.0
 */
public interface GreetingService {

    /**
     * Generate a greeting message.
     * @param name the name to greet
     * @return the greeting message
     */
    String greet(String name);

    /**
     * Generate a greeting message with a custom prefix.
     * @param name the name to greet
     * @param prefix the greeting prefix
     * @return the greeting message
     */
    String greet(String name, String prefix);
}
