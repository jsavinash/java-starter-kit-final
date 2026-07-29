# Spring Integration Flow — Java DSL Integration Example

Demonstrates Spring Integration's Java DSL for defining enterprise integration flows in a functional style.

## 🎯 Purpose

Shows how to define integration flows using Spring Integration's Java DSL, providing a type-safe, fluent API for building message processing pipelines.

## 🧩 Key Dependencies

- `spring-boot-starter-integration` — Spring Integration framework
- `spring-integration-flow` — Integration Flow DSL

## 🚀 How to Run

```bash
./gradlew :apps:batch-integration:spring-integration-flow:bootRun
```

## 📚 Concepts Demonstrated

- **IntegrationFlow DSL** — Fluent API for defining message flows
- **Message Channels** — Direct, queue, and publish-subscribe channels
- **Transformers** — Message transformation with `.transform()`
- **Filters** — Message filtering with `.filter()`
- **Routers** — Message routing with `.route()`
- **Service Activators** — Invoke services with `.handle()`
- **Gateway Interfaces** — Type-safe entry points with `@MessagingGateway`
- **Error Handling** — Error channels and error handling flows