# Spring Cloud Stream — Cloud-Native Messaging Example

Demonstrates Spring Cloud Stream for building event-driven microservices with a binder abstraction over multiple messaging brokers.

## 🎯 Purpose

Shows how to build cloud-native event-driven microservices that can switch between RabbitMQ, Kafka, Pulsar, or other brokers without code changes.

## 🧩 Key Dependencies

- `spring-cloud-stream` — Cloud-native messaging framework
- `spring-cloud-stream-binder-kafka` or `spring-cloud-stream-binder-rabbit` — Binder implementations

## 🚀 How to Run

```bash
./gradlew :apps:messaging:spring-cloud-stream:bootRun
```

## 📚 Concepts Demonstrated

- **Binder Abstraction** — Broker-agnostic messaging API
- **@EnableBinding** — Declarative binding configuration
- **Input/Output Channels** — Message channel definitions
- **@StreamListener** — Declarative message consumption
- **Functional Programming Model** — Supplier/Function/Consumer beans
- **Message Routing** — Dynamic routing based on headers
- **Error Handling** — DLQ and retry configuration per binding
- **Multi-Binder Support** — Connect to multiple brokers