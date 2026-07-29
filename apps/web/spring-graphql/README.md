# Spring GraphQL — GraphQL API Example

Demonstrates building GraphQL APIs with Spring for GraphQL using schema-first approach.

## 🎯 Purpose

Shows how to build flexible, efficient GraphQL APIs with schema-first design, resolvers, and DataLoader.

## 🧩 Key Dependencies

- `spring-boot-starter-web` — Web container
- `spring-boot-starter-graphql` — Spring for GraphQL

## 🚀 How to Run

```bash
./gradlew :apps:web:spring-graphql:bootRun
```

## 📚 Concepts Demonstrated

- **Schema-first Design** — Define GraphQL schema in `.graphqls` files
- **Query Resolvers** — `@QueryMapping` for data fetching
- **Mutation Resolvers** — `@MutationMapping` for data modification
- **DataLoader** — N+1 query optimization with batch loading
- **Type Mapping** — GraphQL types to Java objects
- **GraphiQL** — Interactive API exploration at `/graphiql`
- **Exception Handling** — GraphQL error resolution