# Greeting Service Library — Custom Auto-Configuration Example

A reusable library module demonstrating how to create custom Spring Boot auto-configuration for external consumption.

## 🎯 Purpose

Shows how to build a standalone library with its own auto-configuration that can be consumed by any Spring Boot application using `@EnableGreetingService`.

## 🧩 Key Dependencies

- `spring-boot-autoconfigure` — Auto-configuration support
- `spring-boot-configuration-processor` — Configuration metadata generation

## 🚀 How to Use

This is a library, not an application. Include it as a dependency:

```kotlin
implementation(project(":apps:fundamentals:greeting-service"))
```

## 📚 Concepts Demonstrated

- **Custom Auto-Configuration** — `@AutoConfiguration` class with conditional logic
- **`@Enable*` Annotations** — Feature toggle via `@EnableGreetingService` annotation
- **`@ConfigurationProperties`** — Type-safe configuration binding with `GreetingProperties`
- **spring.factories** — Auto-configuration discovery and registration
- **Library Packaging** — Reusable library distribution across projects
- **Conditional Configuration** — Apply config only when dependencies are available