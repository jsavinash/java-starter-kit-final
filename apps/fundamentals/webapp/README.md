# Web Application — Spring Boot Example

A basic Spring Boot web application that demonstrates the monorepo's convention plugins and platform BOM setup.

## 🎯 Purpose

This is the simplest possible Spring Boot web application, serving as a reference for:
- How to use the `spring-boot-application` convention plugin
- How the platform BOM manages dependency versions
- Minimal Spring Boot project structure

## 🧩 Key Dependencies

- `spring-boot-starter-web` — RESTful web endpoints
- `spring-boot-starter-test` — Testing with JUnit Jupiter + Mockito

## 🚀 How to Run

```bash
./gradlew :apps:fundamentals:webapp:bootRun
```

## 📚 Concepts Demonstrated

- Convention plugin integration
- Version catalog (libs.versions.toml) usage
- Spring Boot auto-configuration
- REST controller basics