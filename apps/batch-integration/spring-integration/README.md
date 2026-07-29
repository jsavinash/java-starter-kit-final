# Spring Integration — Enterprise Integration Patterns Example

Demonstrates Spring Integration for implementing Enterprise Integration Patterns (EIP).

## 🎯 Purpose

Shows how to build message-driven, loosely coupled systems using Spring Integration's messaging abstractions and enterprise integration patterns.

## 🧩 Key Dependencies

- `spring-boot-starter-web` — REST endpoints
- `spring-boot-starter-integration` — Spring Integration framework

## 🚀 How to Run

```bash
./gradlew :apps:batch-integration:spring-integration:bootRun
```

## 📚 Concepts Demonstrated

- **Message Channels** — Point-to-point and publish-subscribe channels
- **Message Endpoints** — Service activators, transformers, routers
- **Message Gateway** — Interface-based messaging entry points
- **Channel Adapters** — Inbound/outbound adapters for external systems
- **Message Router** — Content-based and header-based routing
- **Message Transformer** — Message format conversion
- **Splitter/Aggregator** — Split and combine messages
- **Integration Configuration** — XML and annotation-based setup