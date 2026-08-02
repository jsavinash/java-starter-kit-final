# MEMORY_BANK

> Last updated: 2026-08-01 16:58 UTC (Asia/Calcutta, UTC+5:30)
> Project: `java-starter-kit-final` — Gradle 9.6.1 Kotlin DSL monorepo, Java 25, Spring Boot 4.0.7
> Git remote: `https://github.com/jsavinash/java-starter-kit-final.git` (commit `d1ee6965`)

---

## 1. Architectural Decisions & Tech Stack

### 1.1 Build System & Tooling

| Layer | Technology | Version | Module |
|---|---|---|---|
| **Language** | Java | 25 (Amazon Corretto 25.0.4) | — |
| **DSL** | Kotlin | 2.4.10 | — |
| **Build** | Gradle | 9.6.1 (wrapper) | `gradle/wrapper/` |
| **Version Catalog** | TOML | centralized | `gradle/libs.versions.toml` (303 lines, 97 version refs, 70+ libraries) |
| **Conventions** | Kotlin DSL Precompiled | — | `build-logic/convention-plugins/` (5 plugins) |
| **Platform BOM** | Java Platform / Spring Boot BOM | Spring Boot 4.0.7 | `platforms/spring-boot/` (composite build) |

### 1.2 Gradle Multi-Build Topology

```
java-starter-kit-final/                 ← root (build.gradle.kts: group=com.javastarterkit)
├── settings.gradle.kts                 ← 1 root + 3 composite builds + 62 app subprojects
├── build-logic/                        ← composite build: convention plugins
│   ├── settings.gradle.kts
│   └── convention-plugins/
│       ├── build.gradle.kts            ← kotlin-dsl + java-gradle-plugin
│       └── src/main/kotlin/com/javastarterkit/buildlogic/
│           ├── JavaBaseConventionPlugin.kt       ← Java 25 toolchain, sourceCompatibility
│           ├── TestingConventionPlugin.kt        ← JUnit Jupiter + AssertJ + Mockito
│           ├── CodeQualityConventionPlugin.kt    ← Spotless, Checkstyle, SpotBugs, JaCoCo, OWASP, SonarQube
│           ├── SpringBootApplicationConventionPlugin.kt  ← apps (executable JARs)
│           └── SpringBootLibraryConventionPlugin.kt      ← libraries (greeting-service, etc.)
├── platforms/                          ← composite build: Spring Boot BOM platform
│   ├── settings.gradle.kts
│   └── spring-boot/build.gradle.kts    ← 60-line Java platform POM exporting Spring Boot 4.0.7 BOM
├── design-patterns/                    ← composite build: system design theory + patterns
│   ├── settings.gradle.kts           ← 232 subprojects (52 theory + 180 patterns)
│   ├── build.gradle.kts              ← root: pure Java, JUnit 5 platform, no Spring
│   ├── system-design-theory/         ← 52 modules (OSI model, CAP theorem, CQRS, etc.)
│   └── system-design-pattern/        ← 180 modules across 14 category dirs
│       ├── architectural/           ← 20 (Composable Architecture, CQRS, MVC, Hexagonal, etc.)
│       ├── behavioral/              ← 38 (Observer, Strategy, Command, Visitor, etc.)
│       ├── creational/              ← 14 (Factory, Builder, Singleton, Abstract Factory, etc.)
│       ├── concurrency/             ← 18 (Producer-Consumer, Reactor, Thread Pool, etc.)
│       ├── data-access/             ← 11 (Repository, Unit of Work, Sharding, etc.)
│       ├── functional/              ←  7 (Monad, Callback, Curry, Combinator, etc.)
│       ├── integration/             ←  3 (Ambassador, Anti-Corruption Layer, Gateway)
│       ├── microservices/           ← 11 (API Gateway, Service Discovery, Service Mesh, etc.)
│       ├── messaging/               ←  2 (Data Bus, Event Aggregator)
│       ├── performance-optimization/←  3 (Caching, Data Locality, Lazy Loading)
│       ├── resilience/              ← 10 (Circuit Breaker, Retry, Saga, Bulkheads, etc.)
│       ├── resource-management/     ←  3 (RAII, Server Session, Throttling)
│       ├── solid-principles/        ←  5 (SRP, OCP, LSP, ISP, DIP)
│       ├── structural/              ← 32 (Adapter, Facade, Decorator, Proxy, Composite, etc.)
│       └── testing/                 ←  3 (Arrange-Act-Assert, Object Mother, Page Object)
├── apps/                              ← 62 active Spring Boot subprojects (14 categories)
│   ├── README.md
│   ├── STUDY-MATERIAL.md
│   ├── spring-rewrite/              ← standalone: build.gradle.kts + src/
│   ├── fundamentals/                ← 12 modules | Core Spring Boot concepts
│   ├── data/                        ← 15 modules | Spring Data ecosystem
│   ├── security/                    ←  3 modules | Auth, LDAP, Session
│   ├── web/                         ←  9 modules | MVC, WebFlux, GraphQL, SOAP, WebSocket, HATEOAS
│   ├── batch-integration/           ←  1 module  | Batch extensions
│   ├── cloud/                       ← 12 modules | Eureka, Hystrix, LoadBalancer, OpenFeign, etc.
│   ├── messaging/                   ←  6 modules | AMQP, Kafka, Pulsar, Cloud Stream, JMS, Mail
│   ├── extensions/                  ←  1 module  | Feature flags
│   └── testing/                     ←  2 modules | REST Docs, MockMVC
├── gradle/
│   ├── libs.versions.toml            ← 303 lines: 97 version refs + 70 libraries + 15 plugins
│   ├── checkstyle/checkstyle.xml + checkstyle-suppressions.xml
│   ├── spotbugs/spotbugs-exclude.xml
│   ├── owasp/dependency-check-suppressions.xml
│   └── wrapper/
├── build.gradle.kts                  ← root: subprojects { group="com.javastarterkit", version="1.0.0-SNAPSHOT" }
├── .sdkmanrc                         ← java=25.0.4-amzn, kotlin=2.4.10, gradle=9.6.1, springboot=4.0.7
├── .clinerules                       ← 5 rules: no large file reads, MEMORY_BANK sync, ultra-short responses
├── .gitignore                        ← 115 lines: Gradle/Kotlin/Android/Kotlin/Idea/VSCode/Python/Node patterns
├── README.md                         ← monorepo documentation with tech stack table, structure tree
├── LEARN-MONOREPO.md                 ← 14-section learning guide (composite builds, convention plugins, BOM)
├── spring-projects-analysis.md       ← deep analysis of 77 Spring repos + local project mapping
├── count_patterns.py                 ← pattern inventory script
├── restructure_patterns.py           ← pattern restructuring script
└── restructure_system_design.py      ← theory restructuring script
```

### 1.3 Event-Driven Architecture (Featured Module)

Path: `design-patterns/system-design-pattern/architectural/event-driven-architecture/`

**Core architecture** (thread-safe, decoupled pub-sub):
- `core/Event.java` — base event interface (`eventId()`, `occurredAt()`, `source()`)
- `core/EventListener.java` — `@FunctionalInterface` for subscribers
- `core/EventBus.java` — thread-safe pub-sub bus: `ConcurrentHashMap` + `CopyOnWriteArrayList` + `AtomicLong` + virtual-thread executor

**Domain example (Order lifecycle)**:
- `events/`: `OrderPlacedEvent`, `OrderShippedEvent` (immutable records)
- `service/`: `OrderService` (publisher), `EmailService`, `InventoryService`, `ShippingService` (subscribers)
- `exception/`: `EventDrivenArchitectureException`, `InvalidOrderException`
- `Main.java` — entry point with `mainClass` in build.gradle.kts

**Tests** (6 tests, all passing):
- `EventBusTest.java` — end-to-end flow, multiple orders, invalid input, unsubscribe, fault isolation
- `EventBusConcurrencyTest.java` — 16 threads × 100 orders = 1,600 concurrent events verified

**Docs**: `README.md` (pattern overview), `LLD.md` (low-level design with Mermaid diagrams)

**Build**: `build.gradle.kts` — Java 25, SLF4J + Logback, JUnit 5 (BOM), AssertJ, Mockito

### 1.4 Composable Architecture (Featured Module)

Path: `design-patterns/system-design-pattern/architectural/composable-architecture/`

**Core architecture** (thread-safe, immutable, composable):
- `core/Store.java` — `ReentrantLock` dispatch, `CopyOnWriteArrayList` subscribers, `AtomicLong` revisioning
- `core/State.java` — immutable state interface (generic `S extends State`)
- `core/Action.java` — action interface (generic `A extends Action`)
- `core/Reducer.java` — `Reducer<S extends State, A extends Action, E extends Environment>` functional interface
- `core/component/Component.java` — component interface for modular composition

**Domain example (Pizza ordering)**:
- `core/` states: `PizzaState`, `DeliveryState`, `OrderState`
- `ui/actions/`: `PizzaAction`, `DeliveryAction`, `OrderAction`
- `ui/reducers/`: `PizzaReducer`, `DeliveryReducer`, `OrderReducer`
- `ui/models/`: `PizzaSize`, `Topping`, `DeliveryState`, `OrderState`
- `composition/OrderComposer.java` — composes pizza + delivery reducers into an order flow
- `Main.java` — entry point with `mainClass` in build.gradle.kts

**Infrastructure**:
- `exception/ComposableArchitectureException.java` — base exception
- `exception/InvalidPizzaException.java`, `InvalidDeliveryException.java` — domain exceptions

**Tests** (26 tests, all passing):
- `StoreConcurrencyTest.java` — thread-safety under concurrent dispatch
- `OrderCompositionTest.java` — end-to-end order flow through composed reducers
- `PizzaReducerTest.java` — pizza state transitions
- `DeliveryReducerTest.java` — delivery state transitions

**Docs**: `README.md` (pattern overview), `LLD.md` (low-level design)

**Build**: `build.gradle.kts` — Java 25, SLF4J + Logback, JUnit 5 (BOM), AssertJ, Mockito

### 1.5 Key Design Patterns in Codebase

| Pattern | Where Applied |
|---|---|
| Thread-safe State Holder | `Store.java` (ReentrantLock + AtomicLong) |
| Immutable State | `State.java` interface + record-style implementations |
| Reducer Composition | `OrderComposer.java` composes multiple reducers |
| Observer (pub-sub) | `Store` notifies `CopyOnWriteArrayList` subscribers |
| Exception Hierarchy | `ComposableArchitectureException` → `InvalidPizzaException`, `InvalidDeliveryException` |
| Convention Plugins | 5 precompiled Kotlin DSL plugins in `build-logic/` |
| Composite Builds | 3 included builds: `platforms/`, `build-logic/`, `design-patterns/` |
| Version Catalog | Centralized `libs.versions.toml` (TOML format, Gradle 9.x) |
| BOM Platform | `platforms/spring-boot/` exports Spring Boot 4.0.7 dependency constraints |

### 1.6 Code Quality & Security Tooling

| Tool | Config Path | Scope |
|---|---|---|
| **Spotless** | `CodeQualityConventionPlugin.kt` | Java (palantirJavaFormat), Kotlin (ktlint), Gradle Kotlin DSL |
| **Checkstyle** | `gradle/checkstyle/checkstyle.xml` + `-suppressions.xml` | 13 rules: FileTabCharacter, AvoidStarImport, UnusedImports, ModifierOrder, etc. |
| **SpotBugs** | `gradle/spotbugs/spotbugs-exclude.xml` | Bytecode static analysis (plugin v6.5.9, lib v4.10.3) |
| **JaCoCo** | `CodeQualityConventionPlugin.kt` | Code coverage (v0.8.13) |
| **OWASP Dep-Check** | `gradle/owasp/dependency-check-suppressions.xml` | Security vulnerability scanning (v12.2.2) |
| **SonarQube** | — | Centralized quality dashboard (v7.3.1.8318) |
| **Copyright Header** | Spotless | `// Copyright © $YEAR Java Starter Kit. All rights reserved.` |

### 1.7 AI Tooling & MCP Integration

| Component | Version/Config | Details |
|---|---|---|
| **Claude Code** | 2.1.197 (npm global) | `~/.claude/settings.json`; enabled ECC plugin |
| **ECC (everything-claude-code)** | 2.1.0 plugin | marketplace `affaan-m/everything-claude-code`; MCP catalog + skills/resources |
| **Cline** | VS Code ext (`saoudrizwan.claude-dev`) | MCP config at `~/Library/Application Support/Code/User/globalStorage/saoudrizwan.claude-dev/settings/cline_mcp_settings.json` |
| **Memory MCP** | `@modelcontextprotocol/server-memory` | session knowledge graph; seeded with project/tool entities via knowledge graph |
| **Filesystem MCP** | `@modelcontextprotocol/server-filesystem` | rooted at project path |

**Configured Cline MCP servers (from ECC catalog)**: `memory`, `sequential-thinking`, `filesystem`, `github`, `parallel-search`, `playwright`, `context7`, `exa-web-search`.

- **Project rule** (`.clinerules`): read `MEMORY_BANK.md` at session start; update on exit — this file remains the canonical memory anchor, with the memory MCP acting as complementary knowledge graph.
- **ECC MCP catalog source**: `~/.claude/plugins/marketplaces/ecc/mcp-configs/mcp-servers.json` (30+ servers to pick from).

---

## 2. Project Progress & Completed Features

- [x] **Composable Architecture pattern** module complete with full LLD (Low-Level Design) docs
- [x] **Event-Driven Architecture pattern** module complete with full LLD (Mermaid diagrams) + 6 tests passing
- [x] **All 26 JUnit tests** passing (`StoreConcurrencyTest`, `OrderCompositionTest`, `PizzaReducerTest`, `DeliveryReducerTest`)
- [x] **Build verified** — `design-patterns/` composite build compiles and tests pass
- [x] **62 Spring Boot app modules** included in `settings.gradle.kts` across 14 categories
- [x] **232 design pattern/theory modules** included in `design-patterns/settings.gradle.kts`
- [x] **Convention plugins** (5) implemented in `build-logic/convention-plugins/`
- [x] **Platform BOM** (`platforms/spring-boot/`) exporting Spring Boot 4.0.7 constraints
- [x] **Version catalog** (`gradle/libs.versions.toml`) with 97 version refs + 70 libraries + 15 plugins
- [x] **Code quality** tooling (Spotless, Checkstyle, SpotBugs, JaCoCo, OWASP) configured
- [x] **Documentation**: `README.md`, `LEARN-MONOREPO.md`, `spring-projects-analysis.md`, `apps/STUDY-MATERIAL.md`

---

## 3. Module Inventory Summary

### 3.1 Active Apps Modules (62) by Category

| Category | Active | Disabled | Disabled Reasons |
|---|---|---|---|
| **fundamentals** | 12 | 1 | `spring-framework-loaded` (Spring Boot 4 incompatibility) |
| **data** | 15 | 3 | `spring-data-bom`, `spring-data-dev-tools`, `spring-boot-data-geode` (artifact unavailable / SB4 incompatible) |
| **security** | 3 | 4 | `spring-security-kerberos`, `spring-authorization-server`, `spring-vault`, `spring-credhub`, `spring-session-data-geode` (artifact unavailable / dependency chain) |
| **web** | 7 | 2 | `spring-web-flow` (Spring WebFlow artifact unavailable), `spring-grpc` (Spring Boot 4 incompatibility) |
| **batch-integration** | 1 | 3 | `spring-batch` (Spring Cloud BOM 401 error), `spring-integration` (compile error: missing `IntegrationFlows`), `spring-integration-flow` (dependency on spring-integration) |
| **cloud** | 10 | 4 | `spring-cloud-gateway` (artifact unavailable), `spring-cloud-config` (compile error: `EnableConfigServer` missing) |
| **messaging** | 6 | 0 | — |
| **extensions** | 1 | 6 | `spring-ai` (SB4 incompatible), `spring-shell` (artifact unavailable), `spring-plugin`, `spring-modulith`, `spring-guice`, `spring-retry` (all SB4 incompatible or unavailable) |
| **testing** | 2 | 1 | `spring-testcontainers` (missing PostgreSQL artifact) |

### 3.2 Design Patterns Modules (232 total in `design-patterns/settings.gradle.kts`)

| Category | Count | Modules |
|---|---|---|
| **system-design-theory** | 52 | IP, OSI Model, TCP/UDP, DNS, Load Balancing, Caching, CDN, CAP Theorem, CQRS, CQRS, Circuit Breaker, etc. |
| **solid-principles** | 5 | SRP, OCP, LSP, ISP, DIP |
| **structural** | 32 | Adapter, Bridge, Business Delegate, Component, Composite, DAO, DTO, Decorator, Facade, Factory, Flyweight, Proxy, Singleton, etc. |
| **creational** | 14 | Abstract Factory, Builder, Dependency Injection, Factory Kit, Factory Method, Factory, Monostate, Multiton, Object Pool, Prototype, Registry, Singleton, Step Builder, Type Object |
| **concurrency** | 18 | Active Object, Async Method Invocation, Balking, Double-Checked Locking, Event-Based Async, Event Queue, Fan-Out/Fan-In, Guarded Suspension, Half-Sync/Half-Async, Leader Election, Leader-Followers, Lockable Object, Master-Worker, Monitor, Poison Pill, Producer-Consumer, Promise, Reactor |
| **behavioral** | 38 | Acyclic Visitor, Bytecode, Chain of Responsibility, Command, Commander, Context Object, Data Mapper, Delegation, Dirty Flag, Double Buffer, Double Dispatch, Execute Around, Feature Toggle, Filterer, Fluent Interface, Game Loop, Health Check, Identity Map, Interpreter, Iterator, Mediator, Memento, Null Object, Observer, Partial Response, Pipeline, Property, Specification, State, Strategy, Subclass Sandbox, Template Method, Update Method, Visitor |
| **integration** | 3 | Ambassador, Anti-Corruption Layer, Gateway |
| **microservices** | 11 | API Gateway, Service Discovery, Service Registry, Config Server, Log Aggregation, Database Per Service, Service Mesh, Observability, Health Monitoring, Monitoring, Master Service Decomposition |
| **performance-optimization** | 3 | Caching, Data Locality, Lazy Loading |
| **functional** | 7 | Callback, Collection Pipeline, Combinator, Currying, Function Composition, Monad, Trampoline |
| **resource-management** | 3 | RAII, Server Session, Throttling |
| **resilience** | 10 | Circuit Breaker, Queue-Based Load Leveling, Retry, Saga, Tolerant Reader, Rate Limiting, Bulkheads, Fallbacks, Timeouts, Graceful Degradation |
| **architectural** | 20 | Backend-for-Frontend, Command-Query, CQRS, Event-Driven Architecture, Event Sourcing, Flux, Front Controller, Hexagonal Architecture, Intercepting Filter, Layered Architecture, Microservices Aggregator, MVC, Model-View-Intent, MVP, MVVM, Naked Objects, Page Controller, Presentation Model, Service Layer, Service to Worker, Composable Architecture |
| **messaging** | 2 | Data Bus, Event Aggregator |
| **testing** | 3 | Arrange-Act-Assert, Object Mother, Page Object |
| **data-access** | 11 | Metadata Mapping, Optimistic Offline Lock, Repository, Serialized Entity, Serialized LOB, Sharding, Single Table Inheritance, Table Module, Transaction Script, Unit of Work, Version Number |

---

## 4. Quick Start Commands

```bash
# Activate toolchain
sdk env install            # uses .sdkmanrc (Java 25, Kotlin 2.4.10, Gradle 9.6.1)

# Build everything
./gradlew build            # 62 apps + 232 design-pattern modules

# Build design-patterns composite only
./gradlew :design-patterns:build       # or: cd design-patterns && ./gradlew build

# Test composable architecture specifically
./gradlew :design-patterns:system-design-pattern:architectural:composable-architecture:test

# Run code quality checks
./gradlew check          # Checkstyle + SpotBugs + JaCoCo + OWASP + Spotless
./gradlew spotlessApply   # auto-format code
```

---

## 5. Active Local System State

- MCP memory server configured globally: `npx -y @modelcontextprotocol/server-memory`
- Project root: `/Users/avinash/Documents/development/java-starter-kit-final`
- Design-patterns composite build: 232 subprojects (180 patterns + 52 theory topics)
- Composable Architecture module: 26 tests passing, build verified
- 62 Spring Boot app modules active across 14 categories in `apps/`
