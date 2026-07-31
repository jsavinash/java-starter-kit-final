# Ambassador Pattern

## Overview
Helper service handling retries, logging, latency.

## Structure
```
ambassador/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/ambassador/
    └── Ambassador.java
```

## Implementation
The Ambassador pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:integration:ambassador:build

# Run the pattern example
./gradlew :system-design-pattern:integration:ambassador:run
```

## Category
Integration

## Java Version
Java 25
