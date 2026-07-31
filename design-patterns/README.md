# System Design Patterns - Composite Build

This is a **Gradle composite build** that includes comprehensive examples of **159+ system design patterns** and **41 system design theory topics** with working Java implementations.

## Project Structure

```
design-patterns/
├── settings.gradle.kts          # Composite build configuration
├── build.gradle.kts             # Root build configuration
├── system-design-theory/        # 41 theoretical topics
│   ├── ip/
│   ├── osi-model/
│   ├── dns/
│   ├── load-balancing/
│   ├── caching/
│   ├── databases-dbms/
│   └── ... (41 total modules)
└── system-design-pattern/       # 159+ pattern implementations
    ├── solid-principles/        #   - 5 patterns
    ├── structural/              #  - 31 patterns
    ├── creational/              #  - 14 patterns
    ├── concurrency/             #  - 18 patterns
    ├── behavioral/              #  - 37 patterns
    ├── integration/             #   - 3 patterns
    ├── microservices/           #  - 11 patterns
    ├── testing/                 #   - 3 patterns
    ├── performance-optimization/#   - 3 patterns
    ├── functional/              #   - 7 patterns
    ├── resilience/              #  - 10 patterns
    ├── architectural/           #  - 20 patterns
    ├── messaging/               #   - 2 patterns
    ├── data-access/             #  - 11 patterns
    └── resource-management/     #   - 3 patterns

### 2. System Design Patterns (159+ patterns)
Practical implementation examples organized by category:

- **S.O.L.I.D Principles** (5)
- **Structural Patterns** (31)
- **Creational Patterns** (14)
- **Concurrency Patterns** (18)
- **Behavioral Patterns** (37)
- **Integration Patterns** (3)
- **Microservices Patterns** (11)
- **Testing Patterns** (3)
- **Performance Optimization** (3)
- **Functional Patterns** (7)
- **Resilience Patterns** (10)
- **Architectural Patterns** (20)
- **Messaging Patterns** (2)
- **Data Access Patterns** (11)
- **Resource Management** (3)

## Integration with Main Project

This composite build is automatically included in the root project via `settings.gradle.kts`:

```kotlin
includeBuild("design-patterns")
```

## Usage

### Build All Patterns and Theory
```bash
cd /Users/avinash/Documents/development/java-starter-kit-final
./gradlew :system-design-pattern:build
./gradlew :system-design-theory:build
```

### Build Specific Pattern Category
```bash
# Creational patterns
./gradlew :system-design-pattern:creational:build

# Structural patterns
./gradlew :system-design-pattern:structural:build

# Behavioral patterns
./gradlew :system-design-pattern:behavioral:build

# Architectural patterns
./gradlew :system-design-pattern:architectural:build
```

### Build Individual Pattern
```bash
# Singleton pattern
./gradlew :system-design-pattern:creational:singleton:build

# Observer pattern
./gradlew :system-design-pattern:behavioral:observer:build

# Factory pattern
./gradlew :system-design-pattern:creational:factory:build
```

### Build Theory Topics
```bash
# All theory topics
./gradlew :system-design-theory:build

# Specific topic
./gradlew :system-design-theory:ip:build
./gradlew :system-design-theory:caching:build
./gradlew :system-design-theory:cap-theorem:build
```

### List All Available Modules
```bash
./gradlew projects
```

### Run Tests
```bash
# Run all tests
./gradlew test

# Run specific pattern tests
./gradlew :system-design-pattern:creational:singleton:test
./gradlew :system-design-pattern:structural:adapter:test
```

## Java Version

All patterns are configured for **Java 25** with modern language features including:
- Records
- Sealed classes
- Pattern matching
- Virtual threads
- Structured concurrency

## Testing

JUnit 5.11.4 is used across all modules. Tests can be run individually or as part of the full build.

## Included Patterns (159+ Total)

### Creational Patterns (14)
- Abstract Factory
- Builder
- Dependency Injection
- Factory Method
- Factory
- Factory Kit
- Monostate
- Multiton
- Object Pool
- Prototype
- Registry
- Singleton
- Step Builder
- Type Object

### Structural Patterns (31)
- Adapter
- Bridge
- Business Delegate
- Component
- Composite
- Composite Entity
- Composite View
- Converter
- Curiously Recurring Template Pattern
- Data Access Object
- Data Transfer Object
- Decorator
- Domain Model
- Dynamic Proxy
- Extension Objects
- Facade
- Flyweight
- Marker Interface
- Parameter Object
- Private Class Data
- Proxy
- Role Object
- Separated Interface
- Servant
- Service Locator
- Spatial Partition
- Special Case
- Strangler
- Twin
- Value Object
- Virtual Proxy
- Sidecar

### Behavioral Patterns (37)
- Acyclic Visitor
- Bytecode
- Chain of Responsibility
- Client Session
- Collecting Parameter
- Command
- Commander
- Context Object
- Data Mapper
- Delegation
- Dirty Flag
- Double Buffer
- Double Dispatch
- Execute Around
- Feature Toggle
- Filterer
- Fluent Interface
- Game Loop
- Health Check
- Identity Map
- Interpreter
- Iterator
- Mediator
- Memento
- Mute Idiom
- Null Object
- Notification
- Observer
- Partial Response
- Pipeline
- Property
- Specification
- State
- Strategy
- Subclass Sandbox
- Template Method
- Update Method
- Visitor

### Architectural Patterns (20)
- Command Query Responsibility Segregation (CQRS)
- Event-Driven Architecture
- Event Sourcing
- Flux
- Front Controller
- Hexagonal Architecture
- Intercepting Filter
- Layered Architecture
- Microservices Aggregator
- Model-View-Controller (MVC)
- Model-View-Intent (MVI)
- Model-View-Presenter (MVP)
- Model-View-ViewModel (MVVM)
- Naked Objects
- Page Controller
- Presentation Model
- Service Layer
- Service to Worker
- Backend for Frontend (BFF)
- Composable Architecture

### Concurrency Patterns (18)
- Active Object
- Async Method Invocation
- Balking
- Double Checked Locking
- Event-Based Asynchronous
- Event Queue
- Fan-Out/Fan-In
- Guarded Suspension
- Half-Sync/Half-Async
- Leader Election
- Leader Followers
- Lockable Object
- Master-Worker
- Monitor
- Poison Pill
- Producer-Consumer
- Promise
- Reactor

### Resilience Patterns (10)
- Circuit Breaker
- Queue-Based Load Leveling
- Retry
- Saga
- Tolerant Reader
- Rate Limiting
- Bulkheads
- Fallbacks
- Timeouts
- Graceful Degradation

### Microservices Patterns (11)
- API Gateway
- Service Discovery
- Service Registry
- Config Server
- Log Aggregation
- Database per Service
- Service Mesh
- Observability
- Health Monitoring
- Monitoring
- Master Service Decomposition

### Data Access Patterns (11)
- Metadata Mapping
- Optimistic Offline Lock
- Repository
- Serialized Entity
- Serialized LOB
- Sharding
- Single Table Inheritance
- Table Module
- Transaction Script
- Unit of Work
- Version Number

### Integration Patterns (3)
- Ambassador
- Anti-Corruption Layer
- Gateway

### Functional Patterns (7)
- Callback
- Collection Pipeline
- Combinator
- Currying
- Function Composition
- Monad
- Trampoline

### Messaging Patterns (2)
- Data Bus
- Event Aggregator

### Testing Patterns (3)
- Arrange-Act-Assert (AAA)
- Object Mother
- Page Object

### Performance Optimization Patterns (3)
- Caching
- Data Locality
- Lazy Loading

### Resource Management Patterns (3)
- Resource Acquisition Is Initialization (RAII)
- Server Session
- Throttling

### S.O.L.I.D Principles (5)
- Single Responsibility Principle
- Open/Close Principle
- Liskov Substitution Principle
- Interface Segregation Principle
- Dependency Inversion Principle

## Statistics

- **Total System Design Theory Topics**: 41
- **Total Pattern Implementations**: 159+
- **Total Java Examples**: 152+ working examples
- **Java Version**: 25
- **Build System**: Gradle with composite builds

## Build Features

- ✅ Java 25 compilation
- ✅ JUnit 5.11.4 testing framework
- ✅ Modular architecture with composite builds
- ✅ Type-safe accessors via libs.versions.toml
- ✅ Standardized build configuration across all modules
- ✅ No external dependencies required (pure Java implementations)

## Troubleshooting

### Build Failures
If you encounter build failures:
1. Ensure Java 25 is installed: `java -version`
2. Clean and rebuild: `./gradlew clean build`
3. Check for compilation errors in specific modules

### Missing Dependencies
All patterns use standard Java libraries. No external dependencies are required for most patterns.

## Contributing

When adding new patterns:
1. Follow the existing package structure: `com.javastarterkit.patterns.<category>.<pattern>`
2. Include comprehensive JavaDoc comments
3. Add unit tests demonstrating the pattern
4. Update `settings.gradle.kts` if adding new modules
5. Update this README with the new pattern

## License

Part of the java-starter-kit project.
