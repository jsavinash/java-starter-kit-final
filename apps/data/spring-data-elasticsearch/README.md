# Spring Data Elasticsearch — Search Engine Example

Demonstrates Spring Data Elasticsearch for full-text search and analytics.

## 🎯 Purpose

Shows how to integrate Elasticsearch for document indexing, full-text search, and aggregations using Spring Data repositories.

## 🧩 Key Dependencies

- `spring-boot-starter-web` — REST endpoints
- `spring-boot-starter-data-elasticsearch` — Spring Data Elasticsearch

## 🚀 How to Run

```bash
./gradlew :apps:data:spring-data-elasticsearch:bootRun
```

## 📚 Concepts Demonstrated

- **Elasticsearch Repositories** — `ElasticsearchRepository` for CRUD and search
- **Document Mapping** — `@Document`, `@Field`, `@Id` annotations
- **Full-Text Search** — `@Query` with Elasticsearch Query DSL
- **Aggregations** — Terms, range, date histograms
- **Geo Queries** — Geospatial distance and shape queries
- **Index Management** — Index creation, mappings, and settings
- **Highlighting** — Search result highlighting
- **Multi-entity Search** — Search across multiple document types