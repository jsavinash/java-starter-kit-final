# Spring PetClinic — Example Application

A classic Spring Boot reference application demonstrating layered architecture, JPA entity relationships, and Spring Data repositories.

## 🎯 Purpose

A comprehensive reference application showcasing Spring Boot best practices with a focus on:
- Layered architecture (Controller → Service → Repository)
- JPA entity relationships and mappings
- Spring Data JPA repositories
- H2 in-memory database
- Thymeleaf templating

## 🧩 Key Dependencies

- `spring-boot-starter-web` — Web layer
- `spring-boot-starter-thymeleaf` — Server-side HTML templating
- `spring-boot-starter-data-jpa` — JPA persistence
- `h2` — In-memory H2 database

## 🚀 How to Run

```bash
./gradlew :apps:fundamentals:spring-petclinic:bootRun
```

Then visit:
- `http://localhost:8081/` — Application root
- `http://localhost:8081/h2-console` — H2 database console

## 📚 Concepts Demonstrated

- **Layered Architecture** — Controller → Service → Repository → Entity
- **JPA Entities** — `@Entity`, `@Table`, `@Id`, `@GeneratedValue`
- **Entity Relationships** — `@OneToMany`, `@ManyToOne`, `@JoinColumn`
- **Spring Data JPA** — `JpaRepository`, derived query methods
- **Derived Queries** — `findByLastName`, `findAllByOrderByLastNameAsc`
- **H2 Database** — In-memory database with `ddl-auto: update`
- **H2 Console** — Web-based database browser at `/h2-console`
- **Service Layer** — Business logic separation with constructor injection
- **Model Validation** — `@NotBlank` validation constraints