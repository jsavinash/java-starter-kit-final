# Spring PetClinic — Example Application

The classic Spring PetClinic reference application demonstrating Spring Boot best practices with Thymeleaf templating and JPA persistence.

## 🎯 Purpose

A comprehensive reference application showcasing a complete web application with server-side rendering, database access, and layered architecture.

## 🧩 Key Dependencies

- `spring-boot-starter-web` — Web layer
- `spring-boot-starter-thymeleaf` — Server-side HTML templating
- `spring-boot-starter-data-jpa` — JPA persistence
- `h2` — In-memory H2 database

## 🚀 How to Run

```bash
./gradlew :apps:fundamentals:spring-petclinic:bootRun
```

## 📚 Concepts Demonstrated

- **Thymeleaf Templates** — Server-side HTML rendering with Thymeleaf
- **JPA Repositories** — Data access with Spring Data JPA
- **Entity Relationships** — `@OneToMany`, `@ManyToOne`, `@JoinColumn`
- **Layered Architecture** — Controller → Service → Repository pattern
- **H2 Console** — In-memory database browser at `/h2-console`
- **CRUD Operations** — Full Create, Read, Update, Delete workflows
- **Form Validation** — Client and server-side form validation