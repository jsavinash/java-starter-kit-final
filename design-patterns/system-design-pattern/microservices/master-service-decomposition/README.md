# Master Service Decomposition Pattern

## Overview
Central orchestrator manages distributed services.

## Structure
```
master-service-decomposition/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/masterservicedecomposition/
    └── MasterServiceDecomposition.java
```

## Implementation
The Master Service Decomposition pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:microservices:master-service-decomposition:build

# Run the pattern example
./gradlew :system-design-pattern:microservices:master-service-decomposition:run
```

## Category
Microservices

## Java Version
Java 25
