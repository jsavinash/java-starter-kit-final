# Front Controller Pattern

**Category**: Architectural  
**Difficulty**: Intermediate  
**Java Version**: Java 25 (Amazon Corretto)

## Overview

A centralized entry point (`FrontController`) handles all incoming requests, performs common processing (logging, authentication, authorization), and dispatches to the appropriate `Command`. This eliminates duplicate code across controllers and provides a single place to enforce cross-cutting concerns.

This example models a simple web application with three commands:
- `HomeCommand` — renders the home page
- `LoginCommand` — authenticates a user
- `DashboardCommand` — renders a protected dashboard

## Structure

```
front-controller/
├── build.gradle.kts
├── LLD.md
├── README.md
└── src/
    ├── main/java/com/javastarterkit/patterns/frontcontroller/
    │   ├── Command.java
    │   ├── CommandRegistry.java
    │   ├── FrontController.java
    │   ├── AuthenticationService.java
    │   ├── Request.java
    │   ├── RequestLogger.java
    │   ├── Response.java
    │   └── commands/
    │       ├── DashboardCommand.java
    │       ├── HomeCommand.java
    │       ├── LoginCommand.java
    │       └── UnknownCommand.java
    └── test/java/com/javastarterkit/patterns/frontcontroller/
        └── FrontControllerTest.java
```

## Implementation

The example is a single self-contained Java file with inner static classes/interfaces organized into core abstractions and concrete commands:

### Core Abstractions
| Component | Responsibility |
|-----------|---------------|
| `Request` | Immutable record representing an incoming HTTP-like request (path, params) |
| `Response` | Immutable record representing an outgoing response (status, body) |
| `Command` | Interface for request handlers; each command handles a specific path |
| `FrontController` | Centralized entry point that preprocesses requests and dispatches to commands |

### Concrete Commands
| Command | Path | Behavior |
|---------|------|----------|
| `HomeCommand` | `/home` | Publicly accessible; returns welcome message |
| `LoginCommand` | `/login` | Validates username/password; returns success or error |
| `DashboardCommand` | `/dashboard` | Protected; only accessible after authentication |

### Flow
1. All requests arrive at the `FrontController`.
2. Common preprocessing occurs: logging, authentication/authorization checks.
3. If authentication is required and the user is not authenticated, a redirect response is returned.
4. The controller looks up the `Command` for the request path.
5. If no command is found, a 404 error is returned.
6. Otherwise, the command executes and returns a `Response`.

## Usage

```bash
# Build the pattern
./gradlew :system-design-pattern:architectural:front-controller:build

# Run the tests
./gradlew :system-design-pattern:architectural:front-controller:test
```

## Sample Output

```
=== Front Controller Pattern ===
Centralized request handling with common preprocessing

--- Processing requests ---
[FrontController] Received request: /home
[FrontController] Logging request: /home
Response: 200 OK | Welcome to the home page!

[FrontController] Received request: /login
[FrontController] Logging request: /login
Response: 200 OK | Login successful for user: alice

[FrontController] Received request: /dashboard
[FrontController] Logging request: /dashboard
[FrontController] Authentication required. Redirecting to /login.
Response: 302 Found | Redirecting to /login

[Auth] User 'alice' authenticated successfully.
[FrontController] Received request: /dashboard
[FrontController] Logging request: /dashboard
Response: 200 OK | Welcome to your protected dashboard!

[FrontController] Received request: /unknown
[FrontController] Logging request: /unknown
[FrontController] No command found for path: /unknown
Response: 500 Internal Server Error | 404 Not Found

Benefits:
- Centralized request handling and cross-cutting concerns
- Single point for authentication, authorization, logging
- Easy to add new commands without changing the controller
- Consistent request processing across the application
```

## Benefits

- **Centralized control** — all requests flow through a single controller, making it easy to manage cross-cutting concerns.
- **Consistent preprocessing** — logging, authentication, and authorization are handled in one place.
- **Easy extensibility** — new commands can be added without modifying the controller.
- **Improved security** — access control is enforced uniformly.

## Trade-offs

- **Single point of failure** — if the front controller fails, all requests fail.
- **Potential bottleneck** — all requests must pass through the controller, which can become a performance bottleneck under high load.
- **Increased complexity** — adds an extra layer of indirection compared to direct controller dispatching.

## Category

Architectural

## Java Version

Java 25