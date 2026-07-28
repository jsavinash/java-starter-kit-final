/*
 * Copyright 2012-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.javastarterkit.greeting.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.Test;

import com.javastarterkit.greeting.config.GreetingProperties;

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