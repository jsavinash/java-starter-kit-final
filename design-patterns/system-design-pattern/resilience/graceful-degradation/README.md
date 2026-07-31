# Graceful Degradation Pattern

## Overview
Provides reduced functionality when service is down.

## Structure
```
graceful-degradation/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/gracefuldegradation/
    └── GracefulDegradation.java
```

## Implementation
The Graceful Degradation pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:resilience:graceful-degradation:build

# Run the pattern example
./gradlew :system-design-pattern:resilience:graceful-degradation:run
```

## Category
Resilience

## Java Version
Java 25
