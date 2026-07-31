# Web Layer — Spring Web Technologies

Web-related Spring projects covering reactive programming, SOAP web services, GraphQL, gRPC, HATEOAS, and web flow orchestration.

## 🛠 Tech Stack

| Component | Version |
|-----------|---------|
| Java | 25 (Amazon Corretto 25.0.4) |
| Spring Boot | 4.0.7 |
| Spring WebFlux | Managed by Spring Boot BOM |
| Spring GraphQL | Managed by Spring Boot BOM |
| Gradle | 9.6.1 |

## Projects

| Project | Description |
|---------|-------------|
| [**spring-webflux**](./spring-webflux/) | Reactive programming with Project Reactor — Flux/Mono, reactive REST, WebClient |
| [**spring-web-services**](./spring-web-services/) | SOAP web services — WSDL contracts, endpoint configuration, XML marshalling |
| [**spring-web-flow**](./spring-web-flow/) | Web flow orchestration — Multi-page workflows, conversation scope, view-state |
| [**spring-graphql**](./spring-graphql/) | GraphQL API — Schema-first approach, Query/ Mutation resolvers, DataLoader |
| [**spring-grpc**](./spring-grpc/) | gRPC services — Protocol Buffers, streaming RPCs, interceptors |
| [**spring-hateoas**](./spring-hateoas/) | Hypermedia-driven REST — EntityModel, CollectionModel, links and assemblers |
| [**spring-mvc**](./spring-mvc/) | Spring MVC — Traditional servlet-based web framework, controllers, view resolution |
| [**spring-thymeleaf**](./spring-thymeleaf/) | Thymeleaf templating — Server-side rendering, template engines, layout dialect |
| [**spring-websocket**](./spring-websocket/) | WebSocket support — Real-time bidirectional communication, STOMP messaging |

## Concepts Covered

- Reactive Programming (WebFlux)
- SOAP Web Services
- GraphQL API Design
- gRPC Service Implementation
- HATEOAS / Hypermedia APIs
- Web Flow Orchestration
- Server-Side Rendering (Thymeleaf)
- WebSocket Communication
- Traditional MVC Architecture

## 🚀 Build Commands

```bash
# Build all web projects
./gradlew :apps:web:spring-webflux:build
./gradlew :apps:web:spring-graphql:build
./gradlew :apps:web:spring-hateoas:build
./gradlew :apps:web:spring-mvc:build

# Run a specific application
./gradlew :apps:web:spring-webflux:bootRun
./gradlew :apps:web:spring-mvc:bootRun

# Apply code formatting
./gradlew spotlessApply