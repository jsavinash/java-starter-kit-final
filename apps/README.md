# Spring Boot Projects — Category-Based Reference

This directory contains a comprehensive collection of Spring Boot example applications organized by category. Each project demonstrates specific concepts, features, and best practices of the Spring ecosystem.

## 📁 Category Structure

| Category | Description |
|----------|-------------|
| [**batch-integration**](./batch-integration/) | Batch processing and enterprise integration (Spring Batch, Spring Integration) |
| [**cloud**](./cloud/) | Spring Cloud ecosystem (Configuration, Discovery, Gateway, Circuit Breakers) |
| [**data**](./data/) | Spring Data ecosystem (JPA, MongoDB, Redis, Cassandra, Elasticsearch, etc.) |
| [**extensions**](./extensions/) | Extended Spring capabilities (Shell, AI, Plugin, Modulith, Guice) |
| [**fundamentals**](./fundamentals/) | Core Spring Boot concepts (IoC, REST, Actuator, Auto-configuration) |
| [**messaging**](./messaging/) | Message-driven architectures (AMQP, Kafka, Pulsar, Cloud Stream) |
| [**security**](./security/) | Authentication, Authorization, Session management, Credentials |
| [**testing**](./testing/) | Testing and documentation tools (REST Docs) |
| [**web**](./web/) | Web layer technologies (Reactive, SOAP, GraphQL, gRPC, HATEOAS) |

## 🛠 Prerequisites

- Java 25 (managed via SDKMAN and `.sdkmanrc`)
- Gradle (wrapper included)
- Spring Boot 4.0.7

## 🚀 How to Build

```bash
# Build all projects
./gradlew build

# Build a specific project
./gradlew :apps:fundamentals:webapp:build

# Run a specific application
./gradlew :apps:fundamentals:webapp:bootRun