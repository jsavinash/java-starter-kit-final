# Spring Boot Starters & Auto-configuration — Example Application

Demonstrates how Spring Boot's auto-configuration mechanism works under the hood.

## 🎯 Purpose

Explains the magic behind Spring Boot starters — how `@EnableAutoConfiguration` intelligently configures beans based on classpath dependencies and `@Conditional` annotations.

## 🧩 Key Dependencies

- `spring-boot-starter-web` — Web auto-configuration
- `spring-boot-starter-jdbc` — JDBC auto-configuration
- `spring-boot-starter-actuator` — Actuator monitoring
- `h2` — In-memory database

## 🚀 How to Run

```bash
./gradlew :apps:fundamentals:starters-autoconfig:bootRun
```

## 📚 Concepts Demonstrated

- `@SpringBootApplication` — Composed annotation including `@EnableAutoConfiguration`
- `@ConditionalOnClass` — Auto-configure only when a class is on the classpath
- `@ConditionalOnMissingBean` — Auto-configure only when no custom bean exists
- `@ConditionalOnProperty` — Auto-configure based on property values
- **Custom Auto-configuration** — Creating your own auto-configuration classes
- **spring.factories** — Registration file for auto-configuration discovery
- **Starter Dependencies** — How starters bundle related dependencies