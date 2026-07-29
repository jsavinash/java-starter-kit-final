# Spring Data Couchbase — Document Database Example

Demonstrates Spring Data Couchbase for NoSQL document database access with N1QL queries.

## 🎯 Purpose

Shows how to work with Couchbase using Spring Data repositories, supporting both imperative and reactive data access.

## 🧩 Key Dependencies

- `spring-boot-starter-web` — REST endpoints
- `spring-boot-starter-data-couchbase` — Spring Data Couchbase

## 🚀 How to Run

```bash
./gradlew :apps:data:spring-data-couchbase:bootRun
```

## 📚 Concepts Demonstrated

- **Couchbase Configuration** — Connection to Couchbase cluster
- **@Document** — Entity mapping to Couchbase buckets
- **CouchbaseRepository** — CRUD operations
- **N1QL Queries** — SQL-like queries with `@Query`
- **Reactive Support** — `ReactiveCouchbaseRepository`
- **Buckets and Scopes** — Data organization in Couchbase
- **Sub-Document Operations** — Atomic field-level operations
- **Index Management** — Primary and secondary indexes