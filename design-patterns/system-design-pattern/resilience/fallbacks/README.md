# Fallbacks Pattern

## Overview
Provides alternative response when service fails.

## Structure
```
fallbacks/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/fallbacks/
    └── Fallbacks.java
```

## Implementation
The Fallbacks pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:resilience:fallbacks:build

# Run the pattern example
./gradlew :system-design-pattern:resilience:fallbacks:run
```

## Category
Resilience

## Java Version
Java 25
