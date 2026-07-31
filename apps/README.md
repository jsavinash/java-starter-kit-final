# Spring Boot Projects — Category-Based Reference

This directory contains a comprehensive collection of Spring Boot example applications organized by category. Each project demonstrates specific concepts, features, and best practices of the Spring ecosystem.

## 📁 Category Structure

| Category | Description |
|----------|-------------|
| [**batch-integration**](./batch-integration/) | Batch processing and enterprise integration (Spring Batch, Spring Integration) |
| [**cloud**](./cloud/) | Spring Cloud ecosystem (Configuration, Discovery, Gateway, Circuit Breakers) |
| [**data**](./data/) | Spring Data ecosystem (JPA, MongoDB, Redis, Cassandra, Elasticsearch, etc.) |
| [**extensions**](./extensions/) | Extended Spring capabilities (Shell, AI, Plugin, Modulith, Guice) |
| [**fundamentals**](./fundamentals/) | Core Spring Boot concepts (IoC, REST, Actuator, Auto-configuration) |
| [**messaging**](./messaging/) | Message-driven architectures (AMQP, Kafka, Pulsar, Cloud Stream) |
| [**security**](./security/) | Authentication, Authorization, Session management, Credentials |
| [**testing**](./testing/) | Testing and documentation tools (REST Docs) |
| [**web**](./web/) | Web layer technologies (Reactive, SOAP, GraphQL, gRPC, HATEOAS) |

## 🛠 Prerequisites

- **Java 25** (Amazon Corretto 25.0.4) — managed via SDKMAN and `.sdkmanrc`
- **Gradle 9.6.1** (wrapper included)
- **Kotlin 2.4.10** (for Gradle DSL)
- **Spring Boot 4.0.7**

## 🚀 How to Build

```bash
# Build all projects
./gradlew build

# Build a specific project
./gradlew :apps:fundamentals:webapp:build

# Run a specific application
./gradlew :apps:fundamentals:webapp:bootRun

# Apply code formatting fixes
./gradlew spotlessApply

# Run code quality checks (Checkstyle, SpotBugs, Spotless)
./gradlew check
```

## 📦 Build System

All projects use the monorepo's convention plugins from `build-logic/`:

- `com.javastarterkit.buildlogic.spring-boot-application` — Full Spring Boot application with Jib, Actuator, DevTools
- `com.javastarterkit.buildlogic.spring-boot-library` — Spring Boot library (no boot plugin)
- `com.javastarterkit.buildlogic.java-base` — Java toolchain (Java 25), encoding, compiler config
- `com.javastarterkit.buildlogic.testing` — JUnit Jupiter 5, AssertJ, Mockito
- `com.javastarterkit.buildlogic.code-quality` — Spotless, Checkstyle, SpotBugs, JaCoCo, OWASP, SonarQube

Dependencies are centralized in `gradle/libs.versions.toml` and accessed via type-safe `libs` accessors.

## 🔍 Code Quality

Each project includes:

- **Spotless** — Palantir Java Format for consistent code style
- **Checkstyle** — Google Java Style enforcement
- **SpotBugs** — Static bytecode analysis
- **JaCoCo** — Code coverage reports (0.8.13)
- **License headers** — Enforced on all Java source files

## 📝 Notes

- Some projects require external services (databases, message brokers, etc.) to run fully
- Test failures in certain modules may require additional configuration or external dependencies
- SpotBugs violations in some modules are pre-existing and may require configuration adjustments