// Copyright © 2012-2024 Java Starter Kit. All rights reserved.
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
