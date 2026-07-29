# Spring Cloud Gateway — API Gateway Example

Demonstrates Spring Cloud Gateway for building API gateways with routing, filtering, and cross-cutting concerns.

## 🎯 Purpose

Shows how to implement the API Gateway pattern with Spring Cloud Gateway, enabling centralized routing, filtering, and load balancing for microservices.

## 🧩 Key Dependencies

- `spring-cloud-starter-gateway` — Reactive API Gateway
- `spring-cloud-starter-netflix-eureka-client` — Service discovery integration

## 🚀 How to Run

```bash
./gradlew :apps:cloud:spring-cloud-gateway:bootRun
```

## 📚 Concepts Demonstrated

- **Route Configuration** — Define routes with predicates and filters
- **Route Predicates** — Path, header, query parameter, and method predicates
- **Gateway Filters** — Request/response modification, rate limiting, circuit breaking
- **Load Balancing** — `lb://` URI scheme with service discovery
- **Circuit Breaker** — Integration with resilience patterns
- **CORS Configuration** — Cross-origin resource sharing
- **Custom Filters** — Implement custom gateway filters
- **WebSocket Support** — Proxying WebSocket connections