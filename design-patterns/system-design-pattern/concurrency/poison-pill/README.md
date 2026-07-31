# Poison Pill Pattern

## Overview
Signals shutdown of consumer threads.

## Structure
```
poison-pill/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/poisonpill/
    └── PoisonPill.java
```

## Implementation
The Poison Pill pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:concurrency:poison-pill:build

# Run the pattern example
./gradlew :system-design-pattern:concurrency:poison-pill:run
```

## Category
Concurrency

## Java Version
Java 25
