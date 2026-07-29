# Spring Data JDBC — Simple Relational Database Access

Demonstrates Spring Data JDBC for straightforward relational database access using plain SQL queries.

## 🎯 Purpose

Shows how to build the persistence layer with Spring Data JDBC, providing a simpler alternative to JPA for database access without complex ORM mapping.

## 🧩 Key Dependencies

- `spring-boot-starter-data-jdbc` — Spring Data JDBC

## 🚀 How to Run

```bash
./gradlew :apps:data:spring-data-jdbc:bootRun
```

## 📚 Concepts Demonstrated

- **@JdbcAggregateReference** — Reference to other aggregates
- **CrudRepository** — CRUD operations without JPA
- **Query Methods** — Derived queries from method names
- **@Query** — Custom SQL queries
- **RowMapper** — Map ResultSet rows to domain objects
- **NamedParameterJdbcTemplate** — Parameterized queries
- **Transaction Management** — Declarative transactions
- **Database Initialization** — Schema and data initialization scripts