package com.javastarterkit.patterns.frontcontroller;

import com.javastarterkit.patterns.frontcontroller.commands.DashboardCommand;
import com.javastarterkit.patterns.frontcontroller.commands.HomeCommand;
import com.javastarterkit.patterns.frontcontroller.commands.LoginCommand;
import com.javastarterkit.patterns.frontcontroller.commands.UnknownCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests verifying the Front Controller pattern: centralized request handling,
 * authentication checks, command dispatching, and error handling.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Front Controller Tests")
class FrontControllerTest {

    private FrontController controller;
    private CommandRegistry registry;
    private AuthenticationService authService;
    private RequestLogger logger;

    @BeforeEach
    void setUp() {
        registry = new CommandRegistry();
        authService = new AuthenticationService();
        logger = new RequestLogger();
        controller = new FrontController(registry, authService, logger);

        // Register commands
        registry.register("/home", new HomeCommand());
        registry.register("/login", new LoginCommand(authService));
        registry.register("/dashboard", new DashboardCommand());
    }

    @Test
    @DisplayName("public home page succeeds without authentication")
    void publicHomePageSucceeds() {
        Response response = controller.handleRequest(Request.of("/home"));

        assertThat(response).isNotNull();
        assertThat(response.isSuccess()).isTrue();
        assertThat(response.body()).contains("Welcome to the home page");
    }

    @Test
    @DisplayName("dashboard access is rejected when not authenticated")
    void dashboardRejectedWhenNotAuthenticated() {
        Response response = controller.handleRequest(Request.of("/dashboard"));

        assertThat(response).isNotNull();
        assertThat(response.isRedirect()).isTrue();
        assertThat(response.body()).contains("Redirecting to /login");
    }

    @Test
    @DisplayName("dashboard access succeeds after authentication")
    void dashboardSucceedsAfterAuthentication() {
        // Authenticate user
        boolean authenticated = controller.authenticate("alice", "password123");
        assertThat(authenticated).isTrue();

        // Now access dashboard
        Response response = controller.handleRequest(Request.of("/dashboard", Map.of("username", "alice")));

        assertThat(response).isNotNull();
        assertThat(response.isSuccess()).isTrue();
        assertThat(response.body()).contains("Welcome to your protected dashboard");
    }

    @Test
    @DisplayName("unknown route returns 404 error response")
    void unknownRouteReturns404() {
        Response response = controller.handleRequest(Request.of("/unknown"));

        assertThat(response).isNotNull();
        assertThat(response.isError()).isTrue();
        assertThat(response.body()).contains("404 Not Found");
    }

    @Test
    @DisplayName("login with valid credentials succeeds")
    void loginWithValidCredentialsSucceeds() {
        Response response = controller.handleRequest(
            Request.of("/login", Map.of("username", "alice", "password", "secret"))
        );

        assertThat(response).isNotNull();
        assertThat(response.isSuccess()).isTrue();
        assertThat(response.body()).contains("Login successful for user: alice");
    }

    @Test
    @DisplayName("login with missing credentials fails")
    void loginWithMissingCredentialsFails() {
        Response response = controller.handleRequest(
            Request.of("/login", Map.of("username", "alice"))
        );

        assertThat(response).isNotNull();
        assertThat(response.isError()).isTrue();
        assertThat(response.body()).contains("Invalid credentials");
    }

    @Test
    @DisplayName("null request throws NullPointerException")
    void nullRequestThrowsNullPointerException() {
        assertThatThrownBy(() -> controller.handleRequest(null))
            .isInstanceOf(NullPointerException.class)
            .hasMessage("Request cannot be null");
    }

    @Test
    @DisplayName("demonstrate runs without throwing exceptions")
    void demonstrateRunsSuccessfully() {
        FrontController.demonstrate();
    }

    @Test
    @DisplayName("command registry lookup is case sensitive")
    void commandRegistryIsCaseSensitive() {
        Response response = controller.handleRequest(Request.of("/HOME"));
        assertThat(response).isNotNull();
        assertThat(response.isError()).isTrue();
        assertThat(response.body()).contains("404 Not Found");
    }

    @Test
    @DisplayName("dashboard access with query params succeeds after authentication")
    void dashboardAccessWithQueryParamsSucceeds() {
        controller.authenticate("bob", "pass456");
        Response response = controller.handleRequest(
            Request.of("/dashboard", Map.of("username", "bob", "tab", "settings"))
        );

        assertThat(response).isNotNull();
        assertThat(response.isSuccess()).isTrue();
    }
}