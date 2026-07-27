// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Spring Boot Web Application.
 *
 * This application demonstrates the monorepo convention:
 * - Uses {@code com.javastarterkit.buildlogic.spring-boot-application} plugin
 * - Dependency versions managed by {@code spring-boot-platform} BOM
 * - Testing libraries provided by the testing convention plugin
 * - Code formatting enforced by Spotless via code-quality plugin
 */
@SpringBootApplication
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
