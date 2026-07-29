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

package com.javastarterkit.greeting.config;

/**
 * Configuration properties for the greeting service.
 *
 * @author Spring Boot Engineering
 * @since 3.3.0
 */
public class GreetingProperties {

    /**
     * Default greeting prefix.
     */
    private String prefix = "Hello";

    /**
     * Default suffix.
     */
    private String suffix = "!";

    /**
     * Whether to include the current time in greetings.
     */
    private boolean includeTime = false;

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public boolean isIncludeTime() {
        return this.includeTime;
    }

    public void setIncludeTime(boolean includeTime) {
        this.includeTime = includeTime;
    }

}