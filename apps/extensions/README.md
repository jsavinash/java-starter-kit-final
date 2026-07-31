# Extensions — Extended Spring Ecosystem

Additional Spring ecosystem projects extending the framework's capabilities beyond the core modules.

## 🛠 Tech Stack

| Component | Version |
|-----------|---------|
| Java | 25 (Amazon Corretto 25.0.4) |
| Spring Boot | 4.0.7 |
| Gradle | 9.6.1 |

## Projects

| Project | Description |
|---------|-------------|
| [**spring-shell**](./spring-shell/) | Interactive CLI applications — Command definitions, parameter binding, shell prompts |
| [**spring-ai**](./spring-ai/) | AI/ML integration — LLM interactions, vector databases, AI-powered workflows |
| [**spring-plugin**](./spring-plugin/) | Plugin architecture — Plugin registry, lifecycle management, extensible applications |
| [**spring-modulith**](./spring-modulith/) | Modular monoliths — Module boundaries, event-driven communication, architectural verification |
| [**spring-guice**](./spring-guice/) | Google Guice integration — DI framework interoperability |

## Concepts Covered

- CLI Application Development
- AI/ML Integration
- Plugin-based Architecture
- Modular Monolith Design
- Dependency Injection Interoperability

## 🚀 Build Commands

```bash
# Build all extension projects
./gradlew :apps:extensions:spring-shell:build
./gradlew :apps:extensions:spring-ai:build
./gradlew :apps:extensions:spring-modulith:build

# Run a specific application
./gradlew :apps:extensions:spring-shell:bootRun

# Apply code formatting
./gradlew spotlessApply
```
