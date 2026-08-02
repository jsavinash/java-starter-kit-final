# Project Rules for Claude Code

## Project Context

**java-starter-kit-final** is a Java 25 / Gradle 9.6.1 / Spring Boot 4.0 monorepo with composite builds containing 232+ design pattern modules and Spring Boot applications.

## Build System

### Commands
- `./gradlew build` — build all modules
- `./gradlew :design-patterns:system-design-pattern:architectural:<pattern>:build` — build a specific pattern
- `./gradlew :design-patterns:system-design-pattern:architectural:<pattern>:test` — test a specific pattern
- `./gradlew :apps:<category>:<app>:bootRun` — run a Spring Boot app
- Always use `./gradlew` from project root

### Gradle Configuration
- Kotlin DSL (`build.gradle.kts`)
- Version catalog: `gradle/libs.versions.toml`
- Composite builds: `build-logic/`, `platforms/spring-boot/`, `design-patterns/`
- Java toolchain: Amazon Corretto 25

## Java Coding Standards

### Language Features
- Java 25 toolchain (Amazon Corretto)
- One public top-level type per file
- Records for DTOs/value types
- Sealed interfaces for closed hierarchies
- Pattern matching in switch statements
- `var` only for local variable type inference with obvious types

### Naming Conventions
- **Types**: PascalCase (`TodoStore`, `FilterAction`)
- **Methods/Fields**: camelCase (`getState`, `todoStore`)
- **Constants**: SCREAMING_SNAKE_CASE (`MAX_RETRIES`, `DEFAULT_FILTER`)
- **Packages**: lowercase, `com.javastarterkit.patterns.*`

### Immutability
- Fields `final` by default
- Defensive copies: `List.copyOf()`, `Collections.unmodifiableList()`
- Records for all state objects
- No setters; use `with*()` methods for state transitions

### Error Handling
- Unchecked exceptions for domain errors
- Custom exception classes extending `RuntimeException`
- Validate input at boundaries with `Objects.requireNonNull()`
- Never catch `Exception` broadly; catch specific exceptions

### Concurrency
- `AtomicReference` for lock-free state swaps
- `CopyOnWriteArrayList` for read-heavy concurrent collections
- `ConcurrentHashMap` for concurrent key-value storage
- `ReentrantLock` for fine-grained locking when needed
- `CountDownLatch` + `ExecutorService` for concurrency tests

### SOLID Principles
- **S**: Single responsibility; one reason to change per class
- **O**: Open for extension, closed for modification (sealed interfaces)
- **L**: Liskov substitution; subtypes must be substitutable
- **I**: Interface segregation; small, focused interfaces
- **D**: Dependency inversion; depend on abstractions

## Testing Standards

### Framework
- JUnit 5 + AssertJ + Mockito
- Mirror `src/main/java` package structure in `src/test/java`

### Naming
- Test methods: `methodName_scenario_expectedBehavior()`
- Always use `@DisplayName` for readable test descriptions
- Test classes: `<ClassName>Test` or `<ClassName>ConcurrencyTest`

### Coverage
- Target 80%+ coverage (JaCoCo)
- Test both happy path and edge cases
- Include concurrency tests for thread-safe components

## Architecture Patterns

### Design Patterns Module
- Each pattern in `design-patterns/system-design-pattern/<category>/<pattern>/`
- Standard structure: `core/`, `models/`, `actions/`, `stores/`, `exception/`
- Include `Main.java` for demo execution
- Include `LLD.md` for documentation
- Include `README.md` for pattern overview

### Spring Boot Apps
- Each app in `apps/<category>/<app>/`
- Constructor injection only
- Thin controllers, logic in services
- Repository interfaces for data access

## Security

- No hardcoded secrets; use `System.getenv()`
- Parameterized queries only
- Validate input at boundaries
- Never log sensitive information

## Workflow

### Branch Naming
- `feature/` - New features
- `fix/` - Bug fixes
- `refactor/` - Code refactoring
- `docs/` - Documentation changes

### Before Pushing
1. Run `./gradlew build` to verify compilation
2. Run `./gradlew test` to verify tests pass
3. Update `MEMORY_BANK.md` for significant changes

### Memory Bank
- Read `MEMORY_BANK.md` only when project context is needed
- Update on significant changes (new modules, architecture changes)
- Keep concise; avoid duplicating information