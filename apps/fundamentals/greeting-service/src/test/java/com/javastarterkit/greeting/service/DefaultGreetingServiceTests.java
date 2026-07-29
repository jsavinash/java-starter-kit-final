// Copyright © 2012-2024 Java Starter Kit. All rights reserved.
package com.javastarterkit.greeting.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.javastarterkit.greeting.config.GreetingProperties;
import org.junit.jupiter.api.Test;

class DefaultGreetingServiceTests {

    private final GreetingProperties properties = new GreetingProperties();

    private final DefaultGreetingService service = new DefaultGreetingService(this.properties);

    @Test
    void greetWithDefaultPrefixReturnsGreeting() {
        assertThat(this.service.greet("World")).isEqualTo("Hello, World!");
    }

    @Test
    void greetWithCustomPrefixReturnsCustomGreeting() {
        assertThat(this.service.greet("Spring", "Hi")).isEqualTo("Hi, Spring!");
    }

    @Test
    void greetWithNullNameThrowsException() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> this.service.greet(null))
                .withMessage("Name must not be empty");
    }

    @Test
    void greetWithEmptyNameThrowsException() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> this.service.greet(""))
                .withMessage("Name must not be empty");
    }

    @Test
    void greetWithCustomPropertiesUsesConfiguredValues() {
        this.properties.setPrefix("Good morning");
        this.properties.setSuffix("!!!");
        assertThat(this.service.greet("User")).isEqualTo("Good morning, User!!!");
    }

    @Test
    void greetWithTimeEnabledIncludesCurrentTime() {
        this.properties.setIncludeTime(true);
        String greeting = this.service.greet("World");
        assertThat(greeting).contains("The time is ");
    }
}
