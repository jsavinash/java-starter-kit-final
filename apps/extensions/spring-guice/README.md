# Spring Guice — Google Guice Integration Example

Demonstrates interoperability between Spring Framework and Google Guice dependency injection frameworks.

## 🎯 Purpose

Shows how to integrate Google Guice-managed components into a Spring Boot application, enabling gradual migration or hybrid DI setups.

## 🧩 Key Dependencies

- `spring-boot-starter-web` — Web endpoints
- `guice` — Google Guice DI framework

## 🚀 How to Run

```bash
./gradlew :apps:extensions:spring-guice:bootRun
```

## 📚 Concepts Demonstrated

- **Guice Module Configuration** — Define bindings in Guice modules
- **Spring-Guice Bridge** — Integrate Guice injector with Spring context
- **Injector Creation** — Programmatic Guice injector setup
- **Binding Annotations** — `@Named`, custom binding annotations
- **Provider Methods** — Complex object creation with Guice providers
- **Scope Interoperability** — Singleton, prototype scope mapping