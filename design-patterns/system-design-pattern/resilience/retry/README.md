# Retry Pattern

## Overview
Automatically retries failed operations.

## Structure
```
retry/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/retry/
    └── Retry.java
```

## Implementation
The Retry pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:resilience:retry:build

# Run the pattern example
./gradlew :system-design-pattern:resilience:retry:run
```

## Category
Resilience

## Java Version
Java 25
