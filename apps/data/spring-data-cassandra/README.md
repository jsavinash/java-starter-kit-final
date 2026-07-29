# Spring Data Cassandra — Wide-Column Store Example

Demonstrates Spring Data Cassandra for distributed wide-column database operations.

## 🎯 Purpose

Shows how to work with Apache Cassandra using Spring Data Cassandra, including CQL queries, repositories, and data modeling.

## 🧩 Key Dependencies

- `spring-boot-starter-web` — REST endpoints
- `spring-boot-starter-data-cassandra` — Spring Data Cassandra

## 🚀 How to Run

```bash
./gradlew :apps:data:spring-data-cassandra:bootRun
```

## 📚 Concepts Demonstrated

- **Cassandra Configuration** — Connection to Cassandra cluster
- **@Table** — Entity mapping to Cassandra tables
- **@PrimaryKey** — Primary key with partition and clustering columns
- **@Column** — Column mapping
- **CassandraRepository** — CRUD operations
- **CQL Queries** — Cassandra Query Language with `@Query`
- **Partition Keys** — Data modeling for distributed storage
- **Clustering Keys** — Sorting within partitions