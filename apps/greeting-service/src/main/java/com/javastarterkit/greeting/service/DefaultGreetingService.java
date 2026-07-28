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

import java.time.LocalTime;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.javastarterkit.greeting.config.GreetingProperties;
import com.javastarterkit.greeting.GreetingServiceAutoConfiguration;

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