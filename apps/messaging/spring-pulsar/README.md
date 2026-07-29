# Spring Pulsar — Apache Pulsar Messaging Example

Demonstrates pub-sub messaging with Apache Pulsar using Spring Pulsar.

## 🎯 Purpose

Shows how to build event-driven applications with Apache Pulsar, supporting both pub-sub and streaming messaging models.

## 🧩 Key Dependencies

- `spring-boot-starter-web` — REST endpoints
- `spring-pulsar` — Spring Pulsar integration

## 🚀 How to Run

```bash
./gradlew :apps:messaging:spring-pulsar:bootRun
```

## 📚 Concepts Demonstrated

- **PulsarTemplate** — Send messages to Pulsar topics
- **@PulsarListener** — Declarative message consumption
- **Topic Configuration** — Partitioned and non-partitioned topics
- **Subscription Types** — Exclusive, shared, failover subscriptions
- **Message Serialization** — JSON and Avro schemas
- **Dead Letter Policy** — Failed message redelivery
- **Pulsar Transactions** — Exactly-once message processing