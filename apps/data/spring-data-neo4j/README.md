# Spring Data Neo4j — Graph Database Example

Demonstrates Spring Data Neo4j for graph database operations with nodes, relationships, and Cypher queries.

## 🎯 Purpose

Shows how to model and query graph data using Neo4j with Spring Data Neo4j repositories and Cypher query language.

## 🧩 Key Dependencies

- `spring-boot-starter-web` — REST endpoints
- `spring-boot-starter-data-neo4j` — Spring Data Neo4j

## 🚀 How to Run

```bash
./gradlew :apps:data:spring-data-neo4j:bootRun
```

## 📚 Concepts Demonstrated

- **@Node** — Graph node entity mapping
- **@Relationship** — Relationship between nodes
- **Neo4jRepository** — Graph CRUD operations
- **Cypher Queries** — `@Query` with Cypher query language
- **Graph Traversal** — Path finding and relationship traversal
- **Node Labels** — Typed nodes with labels
- **Relationship Types** — Typed relationships
- **Graph Projections** — DTO projections from graph queries