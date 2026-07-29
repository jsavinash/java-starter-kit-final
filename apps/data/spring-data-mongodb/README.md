# Spring Data MongoDB — Document Database Example

Demonstrates Spring Data MongoDB for document-based NoSQL database access.

## 🎯 Purpose

Shows how to work with MongoDB document databases using Spring Data repositories, both imperative and reactive.

## 🧩 Key Dependencies

- `spring-boot-starter-web` — REST endpoints
- `spring-boot-starter-data-mongodb` — MongoDB data access
- `spring-boot-starter-data-mongodb-reactive` — Reactive MongoDB
- `spring-boot-starter-validation` — Bean validation

## 🚀 How to Run

```bash
./gradlew :apps:data:spring-data-mongodb:bootRun
```

## 📚 Concepts Demonstrated

- **Document Entities** — `@Document`, `@Field`, `@Id` annotations
- **MongoRepository** — CRUD and query methods
- **Reactive Repository** — `ReactiveMongoRepository` with Flux/Mono
- **MongoTemplate** — Programmatic data access
- **Aggregation Pipeline** — MongoDB aggregation operations
- **Embedded Documents** — `@DBRef` and embedded document relationships
- **Geospatial Queries** — `@GeoSpatialIndexed`, `near()` queries
- **Text Search** — Full-text search with `@TextIndexed`