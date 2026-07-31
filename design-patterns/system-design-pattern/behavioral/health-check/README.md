# Health Check Pattern

## Overview
Monitors system component health.

## Structure
```
health-check/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/healthcheck/
    └── HealthCheck.java
```

## Implementation
The Health Check pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:behavioral:health-check:build

# Run the pattern example
./gradlew :system-design-pattern:behavioral:health-check:run
```

## Category
Behavioral

## Java Version
Java 25
