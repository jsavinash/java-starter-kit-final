# Spring Boot REST APIs — Example Application

Demonstrates building production-ready RESTful APIs with Spring Boot, including validation, exception handling, and JPA persistence.

## 🎯 Purpose

Shows how to build a complete REST API layer with proper DTOs, input validation, global exception handling, and database integration.

## 🧩 Key Dependencies

- `spring-boot-starter-web` — REST controllers and request mapping
- `spring-boot-starter-validation` — Bean Validation (Jakarta Validation)
- `spring-boot-starter-data-jpa` — JPA database access
- `h2` — In-memory H2 database

## 🚀 How to Run

```bash
./gradlew :apps:fundamentals:spring-boot-rest:bootRun
```

## 📚 Concepts Demonstrated

- `@RestController`, `@RequestMapping` — REST endpoint mapping
- `@Valid`, `@Validated` — Request body validation
- `@ExceptionHandler`, `@ControllerAdvice` — Global exception handling
- **DTO Pattern** — Request/Response model separation
- **CRUD Operations** — Create, Read, Update, Delete with JPA
- `ResponseEntity` — HTTP status codes and response customization
- `@Entity`, `@Repository` — JPA entity and repository patterns