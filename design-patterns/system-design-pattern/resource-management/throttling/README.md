# Throttling Pattern

## Overview
Limits the rate of operations from a client.

## Structure
```
throttling/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/throttling/
    └── Throttling.java
```

## Implementation
The Throttling pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:resource-management:throttling:build

# Run the pattern example
./gradlew :system-design-pattern:resource-management:throttling:run
```

## Category
Resource Management

## Java Version
Java 25
