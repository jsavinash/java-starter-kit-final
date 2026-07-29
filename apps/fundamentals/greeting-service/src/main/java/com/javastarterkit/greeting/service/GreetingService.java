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