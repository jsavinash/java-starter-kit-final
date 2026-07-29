# Spring Session Data Geode — Distributed Session Management with Apache Geode

Demonstrates Spring Session with Apache Geode for highly available, distributed session storage.

## 🎯 Purpose

Shows how to use Apache Geode (GemFire) as a distributed session store for high-availability, low-latency session management.

## 🧩 Key Dependencies

- `spring-boot-starter-web` — REST endpoints
- `spring-session-data-geode` — Spring Session with Geode backend
- `spring-boot-starter-data-geode` — Spring Data Geode

## 🚀 How to Run

```bash
./gradlew :apps:security:spring-session-data-geode:bootRun
```

## 📚 Concepts Demonstrated

- **Apache Geode Session Storage** — Distributed, in-memory session store
- **Geode Region-backed Sessions** — Session data stored in Geode regions
- **Session Expiration** — Configurable TTL for session cleanup
- **High Availability** — Geode replication for fault tolerance
- **Session Serialization** — Geode-specific session serialization
- **@EnableGemFireHttpSession** — Enable Geode-based session management