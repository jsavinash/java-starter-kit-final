# Batch Processing & Enterprise Integration

Spring Batch and Spring Integration projects for batch processing and enterprise integration patterns.

## 🛠 Tech Stack

| Component | Version |
|-----------|---------|
| Java | 25 (Amazon Corretto 25.0.4) |
| Spring Boot | 4.0.7 |
| Gradle | 9.6.1 |
| Spring Batch | Managed by Spring Boot BOM |
| Spring Integration | Managed by Spring Boot BOM |

## Projects

| Project | Description |
|---------|-------------|
| [**spring-batch**](./spring-batch/) | Core Spring Batch — Chunk-oriented processing, ItemReader/Processor/Writer, JobRepository |
| [**spring-batch-extensions**](./spring-batch-extensions/) | Extended batch capabilities — Async processing, additional job repository options |
| [**spring-integration**](./spring-integration/) | Enterprise Integration Patterns — Message channels, gateways, transformers, routers |
| [**spring-integration-flow**](./spring-integration-flow/) | Integration Flow DSL — Java DSL for defining integration flows |

## Concepts Covered

- Batch Job Configuration
- Chunk-oriented Processing
- ItemReader / ItemProcessor / ItemWriter
- Enterprise Integration Patterns
- Message Channels & Gateways
- Integration Flow DSL

## 🚀 Build Commands

```bash
# Build all batch-integration projects
./gradlew :apps:batch-integration:spring-batch:build
./gradlew :apps:batch-integration:spring-batch-extensions:build
./gradlew :apps:batch-integration:spring-integration:build
./gradlew :apps:batch-integration:spring-integration-flow:build

# Run a specific application
./gradlew :apps:batch-integration:spring-batch:bootRun

# Apply code formatting
./gradlew spotlessApply
```
