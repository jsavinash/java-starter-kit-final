# IoC Container Example — Spring Boot Application

Demonstrates Spring's Inversion of Control (IoC) container — the core of the Spring Framework.

## 🎯 Purpose

Shows how Spring manages object creation, wiring, and lifecycle through its IoC container.

## 🧩 Key Dependencies

- `spring-boot-starter-web` — REST endpoints to demonstrate injected services

## 🚀 How to Run

```bash
./gradlew :apps:fundamentals:ioc-example:bootRun
```

## 📚 Concepts Demonstrated

- **Constructor Injection** — Preferred DI approach
- **Setter Injection** — Alternative DI approach
- **Bean Scopes** — Singleton, Prototype, Request, Session
- **Lifecycle Callbacks** — `@PostConstruct`, `@PreDestroy`
- **`@Configuration` + `@Bean`** — Java-based configuration
- **Component Scanning** — `@Component`, `@Service`, `@Repository`
- **`@Autowired`** — Automatic dependency resolution