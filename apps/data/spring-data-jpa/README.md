# Spring Data JPA — Relational Database Persistence Example

Demonstrates Spring Data JPA for relational database access with entities, repositories, and transactions.

## 🎯 Purpose

Shows how to build the persistence layer using JPA with Spring Data repositories, entity mappings, and transaction management.

## 🧩 Key Dependencies

- `spring-boot-starter-data-jpa` — Spring Data JPA
- `h2` — In-memory H2 database

## 🚀 How to Run

```bash
./gradlew :apps:data:spring-data-jpa:bootRun
```

## 📚 Concepts Demonstrated

- **JPA Entities** — `@Entity`, `@Table`, `@Column`, `@Id`, `@GeneratedValue`
- **Entity Relationships** — `@OneToMany`, `@ManyToOne`, `@ManyToMany`, `@JoinColumn`
- **Spring Data Repositories** — `JpaRepository`, `CrudRepository`, `PagingAndSortingRepository`
- **Query Methods** — Derived queries from method names
- **JPQL Queries** — `@Query` with custom JPQL
- **Native Queries** — `@Query(nativeQuery = true)` with SQL
- **Transaction Management** — `@Transactional` declarative transactions
- **Auditing** — `@CreatedDate`, `@LastModifiedDate`, `@CreatedBy`, `@LastModifiedBy`
- **Pagination & Sorting** — `Pageable`, `Sort`, `Page<T>`