# Spring Cloud — Cloud-Native Microservices

Spring Cloud ecosystem projects for building cloud-native microservices with service discovery, configuration management, gateways, and resilience patterns.

## 🛠 Tech Stack

| Component | Version |
|-----------|---------|
| Java | 25 (Amazon Corretto 25.0.4) |
| Spring Boot | 4.0.7 |
| Spring Cloud | Managed via `libs.versions.toml` |
| Gradle | 9.6.1 |

## Projects

| Project | Description |
|---------|-------------|
| [**spring-cloud-gateway**](./spring-cloud-gateway/) | API Gateway — Route routing, filters, predicates, cross-cutting concerns |
| [**spring-cloud-config**](./spring-cloud-config/) | External Configuration — Centralized config server, property sources |
| [**spring-cloud-openfeign**](./spring-cloud-openfeign/) | Declarative HTTP Clients — Feign clients, load balancing, service discovery |
| [**spring-cloud-netflix-eureka**](./spring-cloud-netflix-eureka/) | Service Discovery — Eureka server and client registration |
| [**spring-cloud-netflix-ribbon**](./spring-cloud-netflix-ribbon/) | Client-side Load Balancing — Ribbon load balancer integration |
| [**spring-cloud-netflix-hystrix**](./spring-cloud-netflix-hystrix/) | Circuit Breaker — Hystrix fault tolerance, fallback patterns |
| [**spring-cloud-consul**](./spring-cloud-consul/) | HashiCorp Consul — Service discovery and configuration |
| [**spring-cloud-zookeeper**](./spring-cloud-zookeeper/) | Apache Zookeeper — Service discovery and configuration |
| [**spring-cloud-sleuth**](./spring-cloud-sleuth/) | Distributed Tracing — Trace IDs, span IDs, Zipkin integration |
| [**spring-cloud-bus**](./spring-cloud-bus/) | Event Bus — Distributed configuration updates, event propagation |
| [**spring-cloud-task**](./spring-cloud-task/) | Short-lived Tasks — Task lifecycle, task repository |

## Concepts Covered

- API Gateway Pattern
- Service Discovery
- Configuration Management
- Circuit Breaker Pattern
- Distributed Tracing
- Client-side Load Balancing
- Declarative HTTP Clients
- Event Bus / Message Bus
- Short-lived Task Execution

## 🚀 Build Commands

```bash
# Build all cloud projects
./gradlew :apps:cloud:spring-cloud-gateway:build
./gradlew :apps:cloud:spring-cloud-config:build
./gradlew :apps:cloud:spring-cloud-openfeign:build
./gradlew :apps:cloud:spring-cloud-netflix-eureka:build

# Run a specific application
./gradlew :apps:cloud:spring-cloud-config:bootRun

# Apply code formatting
./gradlew spotlessApply
```

> **Note:** Some Spring Cloud Netflix components (Hystrix, Ribbon) are in maintenance mode. Prefer Spring Cloud LoadBalancer and Resilience4j for new projects.
