# Spring Kafka — Apache Kafka Event Streaming Example

Demonstrates event streaming with Apache Kafka using Spring Kafka.

## 🎯 Purpose

Shows how to build event-driven applications with Apache Kafka, including producers, consumers, and stream processing.

## 🧩 Key Dependencies

- `spring-boot-starter-web` — REST endpoints
- `spring-kafka` — Spring Kafka integration

## 🚀 How to Run

```bash
./gradlew :apps:messaging:spring-kafka:bootRun
```

## 📚 Concepts Demonstrated

- **KafkaTemplate** — Send messages to Kafka topics
- **@KafkaListener** — Declarative message consumption
- **Topic Configuration** — Programmatic topic creation
- **Message Serialization** — JSON and Avro serialization
- **Consumer Groups** — Scalable consumer group processing
- **Offset Management** — Commit strategies (auto, manual, ack)
- **Error Handling** — Dead letter topics and retry
- **Stream Processing** — Kafka Streams DSL