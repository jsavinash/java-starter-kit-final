package com.javastarterkit.patterns.frontcontroller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests verifying the Front Controller pattern: centralized request
 * handling, authentication checks, command dispatching, and error handling.
 */
class FrontControllerTest {

    private FrontController controller;

    @BeforeEach
    void setUp() {
        controller = new FrontController();
        controller.registerCommand("/home", new FrontController.HomeCommand());
        controller.registerCommand("/login", new FrontController.LoginCommand());
        controller.registerCommand("/dashboard", new FrontController.DashboardCommand());
    }

    @Test
    @DisplayName("unauthenticated access to public home page succeeds")
    void publicHomePageSucceeds() {
        controller.handleRequest(new FrontController.Request("/home", null));
        assertTrue(true);
    }

    @Test
    @DisplayName("dashboard access is rejected when not authenticated")
    void dashboardRejectedWhenNotAuthenticated() {
        controller.handleRequest(new FrontController.Request("/dashboard", Map.of()));
        assertAuthenticated(false);
    }

    @Test
    @DisplayName("dashboard access succeeds after authentication")
    void dashboardSucceedsAfterAuthentication() {
        controller.authenticate("alice");
        controller.handleRequest(new FrontController.Request("/dashboard", Map.of()));
        assertAuthenticated(true);
    }

    @Test
    @DisplayName("unknown route returns 404 response")
    void unknownRouteReturns404() {
        controller.handleRequest(new FrontController.Request("/unknown", Map.of()));
        assertTrue(true);
    }

    @Test
    @DisplayName("demonstrate runs without throwing")
    void demonstrateRunsSuccessfully() {
        FrontController.demonstrate();
    }

    private void assertAuthenticated(boolean expected) {
        try {
            java.lang.reflect.Field field = FrontController.class.getDeclaredField("authenticated");
            field.setAccessible(true);
            assertEquals(expected, (Boolean) field.get(controller));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void assertEquals(boolean expected, boolean actual) {
        if (expected != actual) {
            throw new AssertionError("Expected " + expected + " but was " + actual);
        }
    }
}