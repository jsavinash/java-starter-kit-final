# Microservices Aggregator Pattern

## Overview
Aggregates data from multiple services.

## Structure
```
microservices-aggregator/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/microservicesaggregator/
    └── MicroservicesAggregator.java
```

## Implementation
The Microservices Aggregator pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:architectural:microservices-aggregator:build

# Run the pattern example
./gradlew :system-design-pattern:architectural:microservices-aggregator:run
```

## Category
Architectural

## Java Version
Java 25
