# Spring Data — Data Access & Persistence

Spring Data ecosystem projects for database access across SQL and NoSQL databases.

## 🛠 Tech Stack

| Component | Version |
|-----------|---------|
| Java | 25 (Amazon Corretto 25.0.4) |
| Spring Boot | 4.0.7 |
| Spring Data | Managed by Spring Boot BOM |
| Gradle | 9.6.1 |
| Flyway | 11.6.0 (database migrations) |

## Projects

| Project | Description |
|---------|-------------|
| [**spring-data-jpa**](./spring-data-jpa/) | JPA persistence — Entities, repositories, JPQL queries, auditing |
| [**spring-data-mongodb**](./spring-data-mongodb/) | MongoDB — Document databases, repositories, aggregations |
| [**spring-data-redis**](./spring-data-redis/) | Redis — Key-value stores, repositories, cache abstraction |
| [**spring-data-elasticsearch**](./spring-data-elasticsearch/) | Elasticsearch — Full-text search, repositories, aggregations |
| [**spring-data-cassandra**](./spring-data-cassandra/) | Cassandra — Wide-column stores, CQL queries, repositories |
| [**spring-data-couchbase**](./spring-data-couchbase/) | Couchbase — Document database, N1QL queries, repositories |
| [**spring-data-neo4j**](./spring-data-neo4j/) | Neo4j — Graph databases, nodes, relationships, Cypher queries |
| [**spring-data-r2dbc**](./spring-data-r2dbc/) | Reactive SQL — Non-blocking database access with R2DBC |
| [**spring-data-jdbc**](./spring-data-jdbc/) | JDBC persistence — Simple SQL-based repositories |
| [**spring-data-ldap**](./spring-data-ldap/) | LDAP repositories — Directory-based entity persistence |
| [**spring-data-rest**](./spring-data-rest/) | REST exporters — Auto-generated REST APIs from repositories |
| [**spring-data-commons**](./spring-data-commons/) | Common abstractions — Repository infrastructure, pagination |
| [**spring-data-keyvalue**](./spring-data-keyvalue/) | Key-value stores — Redis, Riak, and custom key-value implementations |
| [**spring-data-envers**](./spring-data-envers/) | Auditing — Entity versioning, revision history, audit logs |
| [**spring-data-relational**](./spring-data-relational/) | Relational — R2DBC and JDBC abstractions |
| [**spring-data-bom**](./spring-data-bom/) | Bill of Materials — Version management for Spring Data modules |
| [**spring-data-dev-tools**](./spring-data-dev-tools/) | Development tools — Repository inspection, query debugging |
| [**spring-data-samples**](./spring-data-samples/) | Sample applications — Reference implementations |
| [**spring-boot-data-geode**](./spring-boot-data-geode/) | Apache Geode — In-memory data grids, caching |

## Concepts Covered

- SQL Databases (JPA, JDBC, R2DBC)
- NoSQL Databases (MongoDB, Redis, Cassandra, Couchbase, Neo4j)
- Search Engines (Elasticsearch)
- Reactive Data Access
- Data Auditing
- REST Data Exporters
- Repository Abstractions
- Cache Management

## 🚀 Build Commands

```bash
# Build all data projects
./gradlew :apps:data:spring-data-jpa:build
./gradlew :apps:data:spring-data-mongodb:build
./gradlew :apps:data:spring-data-redis:build

# Run a specific application
./gradlew :apps:data:spring-data-jpa:bootRun

# Apply code formatting
./gradlew spotlessApply
```

> **Note:** Some projects require external databases (MongoDB, Redis, Cassandra, etc.) to run fully. JPA and JDBC projects use embedded H2 for development.
