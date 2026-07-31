# Timeouts Pattern

## Overview
Limits wait time for service responses.

## Structure
```
timeouts/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/timeouts/
    └── Timeouts.java
```

## Implementation
The Timeouts pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:resilience:timeouts:build

# Run the pattern example
./gradlew :system-design-pattern:resilience:timeouts:run
```

## Category
Resilience

## Java Version
Java 25
