# Spring HATEOAS — Hypermedia-Driven REST API Example

Demonstrates building REST APIs that follow the HATEOAS (Hypermedia as the Engine of Application State) principle.

## 🎯 Purpose

Shows how to create self-describing REST APIs where responses include links to navigate related resources.

## 🧩 Key Dependencies

- `spring-boot-starter-web` — REST endpoints
- `spring-boot-starter-hateoas` — HATEOAS support

## 🚀 How to Run

```bash
./gradlew :apps:web:spring-hateoas:bootRun
```

## 📚 Concepts Demonstrated

- **EntityModel** — Wrapping entities with hypermedia links
- **CollectionModel** — Wrapping collections with links
- **RepresentationModelAssembler** — Converting entities to resource models
- **WebMvcLinkBuilder** — Building links to controller methods
- **Link Relations** — `self`, `all`, `next`, `prev` relation types
- **Affordances** — Describing available operations on resources
- **HAL Format** — Hypertext Application Language response format