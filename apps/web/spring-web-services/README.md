# Spring Web Services — SOAP Web Service Example

Demonstrates contract-first SOAP web services with Spring Web Services.

## 🎯 Purpose

Shows how to build SOAP-based web services using WSDL contracts, XML marshalling, and endpoint configuration.

## 🧩 Key Dependencies

- `spring-boot-starter-web` — Web container
- `spring-ws-core` — Spring Web Services core

## 🚀 How to Run

```bash
./gradlew :apps:web:spring-web-services:bootRun
```

## 📚 Concepts Demonstrated

- **Contract-first SOAP** — WSDL-driven development
- **Endpoint Configuration** — `@Endpoint`, `@PayloadRoot` annotations
- **XML Marshalling** — JAXB marshalling/unmarshalling
- **Message Dispatch** — SOAP message routing
- **WSDL Publishing** — Auto-generated WSDL at runtime
- **SOAP Fault Handling** — Error handling in SOAP responses
- **XSD Schema** — XML Schema Definition for request/response types