# Spring gRPC — gRPC Service Example

Demonstrates building high-performance gRPC services with Spring Boot.

## 🎯 Purpose

Shows how to implement gRPC services using Protocol Buffers for service contracts, supporting both unary and streaming RPCs.

## 🧩 Key Dependencies

- `spring-boot-starter-web` — Web container
- `spring-grpc` — Spring gRPC integration

## 🚀 How to Run

```bash
./gradlew :apps:web:spring-grpc:bootRun
```

## 📚 Concepts Demonstrated

- **Protocol Buffers** — `.proto` service definition files
- **Unary RPC** — Single request/single response
- **Server Streaming** — Single request, stream of responses
- **Client Streaming** — Stream of requests, single response
- **Bidirectional Streaming** — Stream of requests and responses
- **gRPC Interceptors** — Client/server interceptors for cross-cutting concerns
- **Service Implementation** — gRPC service stub implementation