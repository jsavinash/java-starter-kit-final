# Spring Session — Distributed Session Management Example

Demonstrates Spring Session for managing user sessions in distributed systems using Redis.

## 🎯 Purpose

Shows how to externalize HTTP session storage from the application server to Redis, enabling session sharing across multiple instances.

## 🧩 Key Dependencies

- `spring-boot-starter-web` — REST endpoints
- `spring-session-data-redis` — Spring Session with Redis backend
- `spring-boot-starter-data-redis` — Spring Data Redis

## 🚀 How to Run

```bash
./gradlew :apps:security:spring-session:bootRun
```

## 📚 Concepts Demonstrated

- **Distributed Sessions** — Share sessions across application instances
- **Redis-backed Session Storage** — External session persistence
- **@EnableRedisHttpSession** — Enable Redis-based session management
- **Session Repository** — `SessionRepository` for CRUD operations
- **Session Expiration** — Configurable session timeout
- **Session Events** — `SessionCreatedEvent`, `SessionDestroyedEvent`
- **Session Serialization** — JSON/Java serialization of session attributes