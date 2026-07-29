# Spring Batch — Batch Processing Example

Demonstrates core Spring Batch concepts for building robust batch processing applications.

## 🎯 Purpose

Shows how to configure and run batch jobs with chunk-oriented processing, readers, processors, writers, and job orchestration.

## 🧩 Key Dependencies

- `spring-boot-starter-web` — REST endpoints to trigger jobs
- `spring-boot-starter-batch` — Spring Batch framework
- `spring-boot-starter-jdbc` — JDBC for batch metadata storage
- `h2` — In-memory H2 database

## 🚀 How to Run

```bash
./gradlew :apps:batch-integration:spring-batch:bootRun
```

## 📚 Concepts Demonstrated

- **Job Configuration** — `@EnableBatchProcessing`, `@Bean` job definition
- **Chunk-oriented Processing** — Read → Process → Write commit intervals
- **ItemReader** — Read data from files, databases, or APIs
- **ItemProcessor** — Transform and validate data
- **ItemWriter** — Write processed data to destination
- **JobRepository** — Batch metadata and job execution tracking
- **JobLauncher** — Programmatic job execution
- **Step Execution** — Sequential and parallel step execution
- **Skip/Retry** — Fault-tolerant processing with skip/retry policies