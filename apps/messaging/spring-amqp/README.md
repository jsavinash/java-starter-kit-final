# Spring AMQP — RabbitMQ Messaging Example

Demonstrates messaging with RabbitMQ using Spring AMQP.

## 🎯 Purpose

Shows how to implement message-based communication using RabbitMQ with producers, consumers, and RabbitTemplate.

## 🧩 Key Dependencies

- `spring-boot-starter-web` — REST endpoints
- `spring-boot-starter-amqp` — Spring AMQP with RabbitMQ

## 🚀 How to Run

```bash
./gradlew :apps:messaging:spring-amqp:bootRun
```

## 📚 Concepts Demonstrated

- **RabbitTemplate** — Send and receive messages
- **@RabbitListener** — Declarative message consumption
- **Queue/Exchange/Binding** — AMQP topology configuration
- **Message Converters** — JSON and Java serialization
- **Publisher Confirms** — Reliable message publishing
- **Dead Letter Queues** — Failed message handling
- **Retry Policies** — Message processing retry