# Spring Modulith — Modular Monolith Example

Demonstrates Spring Modulith for building modular monolith applications with well-defined module boundaries.

## 🎯 Purpose

Shows how to structure applications as a modular monolith with strict module boundaries, event-driven communication, and architectural verification.

## 🧩 Key Dependencies

- `spring-modulith-core` — Spring Modulith framework

## 🚀 How to Run

```bash
./gradlew :apps:extensions:spring-modulith:bootRun
```

## 📚 Concepts Demonstrated

- **Module Boundaries** — Well-defined module separation
- **Module Visibility** — Public API vs internal implementation
- **Event-Driven Communication** — Asynchronous inter-module events
- **Architectural Verification** — Enforce module rules with tests
- **Module Documentation** — Auto-generated module structure docs
- **Event Publication** — Internal and external event publishing
- **Module Testing** — Isolated module integration tests