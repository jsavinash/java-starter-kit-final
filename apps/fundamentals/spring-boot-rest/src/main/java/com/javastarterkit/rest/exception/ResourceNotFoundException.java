// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.rest.exception;

/**
 * Custom exception for resource not found scenarios.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}