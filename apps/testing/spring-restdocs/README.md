# Spring REST Docs — API Documentation Through Tests

Demonstrates Spring REST Docs for generating accurate, test-driven API documentation.

## 🎯 Purpose

Shows how to produce high-quality API documentation that is automatically verified by tests, ensuring documentation always matches implementation.

## 🧩 Key Dependencies

- `spring-boot-starter-web` — REST endpoints
- `spring-restdocs-mockmvc` — REST Docs with MockMvc
- `spring-restdocs-core` — Core REST Docs framework

## 🚀 How to Run

```bash
./gradlew :apps:testing:spring-restdocs:bootRun
```

## 📚 Concepts Demonstrated

- **@AutoConfigureRestDocs** — Automatic REST Docs configuration
- **Snippet Generation** — Auto-generated API documentation snippets
- **MockMvc Integration** — Documentation from MockMvc tests
- **Asciidoctor Integration** — AsciiDoc documentation generation
- **Request/Response Documentation** — Document HTTP requests and responses
- **Path Parameters** — Document URI template variables
- **Request Fields** — Document request body fields with constraints
- **Response Fields** — Document response body fields