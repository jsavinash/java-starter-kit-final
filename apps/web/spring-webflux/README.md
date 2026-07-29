# Spring WebFlux — Reactive Programming Example

Demonstrates reactive programming with Spring WebFlux and Project Reactor.

## 🎯 Purpose

Shows how to build non-blocking, reactive REST APIs using WebFlux with Flux and Mono types.

## 🧩 Key Dependencies

- `spring-boot-starter-webflux` — Reactive web framework
- `spring-boot-starter-data-mongodb-reactive` — Reactive MongoDB

## 🚀 How to Run

```bash
./gradlew :apps:web:spring-webflux:bootRun
```

## 📚 Concepts Demonstrated

- **Reactive Programming** — Event-driven, non-blocking architecture
- **Flux** — Reactive stream for 0..N elements
- **Mono** — Reactive stream for 0..1 elements
- **Reactive REST Endpoints** — `@RestController` with reactive return types
- **WebClient** — Non-blocking HTTP client
- **Reactive MongoDB** — Non-blocking database access
- **Backpressure** — Flow control in reactive streams
- **Schedulers** — Thread management for reactive operations