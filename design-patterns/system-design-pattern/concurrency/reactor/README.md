# Reactor Pattern

## Overview
Handles service requests from multiple sources.

## Structure
```
reactor/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/reactor/
    └── Reactor.java
```

## Implementation
The Reactor pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:concurrency:reactor:build

# Run the pattern example
./gradlew :system-design-pattern:concurrency:reactor:run
```

## Category
Concurrency

## Java Version
Java 25
