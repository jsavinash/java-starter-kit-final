# Guarded Suspension Pattern

## Overview
Suspends execution until condition is met.

## Structure
```
guarded-suspension/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/guardedsuspension/
    └── GuardedSuspension.java
```

## Implementation
The Guarded Suspension pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:concurrency:guarded-suspension:build

# Run the pattern example
./gradlew :system-design-pattern:concurrency:guarded-suspension:run
```

## Category
Concurrency

## Java Version
Java 25
