// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.webapp;

import static org.assertj.core.api.Assertions.assertThat;

import com.javastarterkit.webapp.web.GreetingController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Integration test for the Spring Boot Web Application.
 *
 * Verifies that:
 * - The application context loads successfully
 * - The greeting endpoint returns expected responses
 * - The health endpoint returns UP status
 * - The controller bean is properly injected
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebApplicationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private GreetingController greetingController;

    @Test
    void contextLoads() {
        // Verify the application context starts without errors
        assertThat(greetingController).isNotNull();
    }

    @Test
    void greetingEndpointReturnsOk() {
        // Act
        ResponseEntity<String> response =
                restTemplate.getForEntity("http://localhost:%d/api/greeting".formatted(port), String.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull().startsWith("Hello, World!");
    }

    @Test
    void greetingEndpointWithCustomName() {
        // Act
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:%d/api/greeting?name=SpringBoot".formatted(port), String.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull().startsWith("Hello, SpringBoot!");
    }

    @Test
    void healthEndpointReturnsUp() {
        // Act
        ResponseEntity<String> response =
                restTemplate.getForEntity("http://localhost:%d/api/health".formatted(port), String.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull().isEqualTo("UP");
    }

    @Test
    void greetingContainsTimestamp() {
        // Act
        ResponseEntity<String> response =
                restTemplate.getForEntity("http://localhost:%d/api/greeting".formatted(port), String.class);

        // Assert — the response should contain an ISO-like timestamp
        assertThat(response.getBody())
                .isNotNull()
                .contains("The current server time is ")
                .containsPattern("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}");
    }
}
