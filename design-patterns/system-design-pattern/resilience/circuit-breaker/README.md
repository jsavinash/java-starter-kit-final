# Circuit Breaker Pattern

## Overview
Detects failures and prevents cascading.

## Structure
```
circuit-breaker/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/circuitbreaker/
    └── CircuitBreaker.java
```

## Implementation
The Circuit Breaker pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:resilience:circuit-breaker:build

# Run the pattern example
./gradlew :system-design-pattern:resilience:circuit-breaker:run
```

## Category
Resilience

## Java Version
Java 25
