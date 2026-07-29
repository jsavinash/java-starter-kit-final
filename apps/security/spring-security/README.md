# Spring Security — Example Application

Demonstrates core Spring Security concepts including authentication, authorization, JWT-based security, and method-level access control.

## 🎯 Purpose

Shows how to secure a Spring Boot application with JWT-based authentication, role-based authorization, and method-level security annotations.

## 🧩 Key Dependencies

- `spring-boot-starter-web` — REST endpoints
- `spring-boot-starter-security` — Spring Security
- `jjwt-api`, `jjwt-impl`, `jjwt-jackson` — JWT token support

## 🚀 How to Run

```bash
./gradlew :apps:security:spring-security:bootRun
```

## 📚 Concepts Demonstrated

- **Security Configuration** — `SecurityFilterChain` configuration
- **JWT Authentication** — Token generation, validation, and refresh
- **JWT Filters** — `OncePerRequestFilter` for JWT token extraction
- **Method-level Security** — `@PreAuthorize`, `@Secured` annotations
- **Role-based Access** — `hasRole()`, `hasAuthority()` authorization
- **Password Encoding** — `BCryptPasswordEncoder` for secure password storage
- **Authentication Provider** — Custom `AuthenticationProvider` implementation
- **UserDetailsService** — Custom user loading from database