# Log Aggregation Pattern

## Overview
Centralizes logs from multiple services.

## Structure
```
log-aggregation/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/logaggregation/
    └── LogAggregation.java
```

## Implementation
The Log Aggregation pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:microservices:log-aggregation:build

# Run the pattern example
./gradlew :system-design-pattern:microservices:log-aggregation:run
```

## Category
Microservices

## Java Version
Java 25
