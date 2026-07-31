# Messaging — Message-Driven Architectures

Spring messaging projects for building event-driven, message-based applications.

## 🛠 Tech Stack

| Component | Version |
|-----------|---------|
| Java | 25 (Amazon Corretto 25.0.4) |
| Spring Boot | 4.0.7 |
| Gradle | 9.6.1 |
| Spring AMQP | Managed by Spring Boot BOM |
| Spring Kafka | Managed by Spring Boot BOM |
| Spring Pulsar | Managed by Spring Boot BOM |

## Projects

| Project | Description |
|---------|-------------|
| [**spring-amqp**](./spring-amqp/) | RabbitMQ messaging — AMQP protocol, producers, consumers, RabbitTemplate |
| [**spring-kafka**](./spring-kafka/) | Apache Kafka — Event streaming, producers, consumers, KafkaTemplate |
| [**spring-pulsar**](./spring-pulsar/) | Apache Pulsar — Pub-sub messaging, producers, consumers, PulsarTemplate |
| [**spring-cloud-stream**](./spring-cloud-stream/) | Cloud-native messaging abstraction — Binder-based, multi-broker support |

## Concepts Covered

- Message Producers & Consumers
- Event-Driven Architecture
- Pub-Sub Messaging
- Stream Processing
- Binder Abstraction
- Message Serialization

## 🚀 Build Commands

```bash
# Build all messaging projects
./gradlew :apps:messaging:spring-amqp:build
./gradlew :apps:messaging:spring-kafka:build
./gradlew :apps:messaging:spring-pulsar:build
./gradlew :apps:messaging:spring-cloud-stream:build

# Run a specific application
./gradlew :apps:messaging:spring-kafka:bootRun

# Apply code formatting
./gradlew spotlessApply
```

> **Note:** These projects require external message brokers (RabbitMQ, Kafka, Pulsar) to run fully.
