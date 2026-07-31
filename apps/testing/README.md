# Testing — Testing & Documentation

Tools and frameworks for testing Spring Boot applications and generating API documentation.

## 🛠 Tech Stack

| Component | Version |
|-----------|---------|
| Java | 25 (Amazon Corretto 25.0.4) |
| Spring Boot | 4.0.7 |
| JUnit Jupiter | 5.12.x (managed by Spring Boot BOM) |
| AssertJ | 3.27.3 |
| Mockito | 5.16.1 |
| Testcontainers | Managed by Spring Boot BOM |
| Gradle | 9.6.1 |

## Projects

| Project | Description |
|---------|-------------|
| [**spring-restdocs**](./spring-restdocs/) | API documentation through tests — Snippet generation, Asciidoctor integration |
| [**spring-mockmvc**](./spring-mockmvc/) | MockMvc testing — Web layer testing without a running server |
| [**spring-testcontainers**](./spring-testcontainers/) | Testcontainers integration — Integration testing with Docker containers |

## Concepts Covered

- Test-Driven Documentation
- REST API Documentation
- Asciidoctor Integration
- Snippet Generation
- Web Layer Testing (MockMvc)
- Integration Testing with Testcontainers
- Test Fixtures and Utilities

## 🚀 Build Commands

```bash
# Build all testing projects
./gradlew :apps:testing:spring-restdocs:build
./gradlew :apps:testing:spring-mockmvc:build
./gradlew :apps:testing:spring-testcontainers:build

# Run tests for a specific project
./gradlew :apps:testing:spring-mockmvc:test

# Apply code formatting
./gradlew spotlessApply
```

> **Note:** Testcontainers requires Docker to be running for integration tests.