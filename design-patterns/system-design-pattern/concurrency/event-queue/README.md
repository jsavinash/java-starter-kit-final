# Event Queue Pattern

## Overview
Manages event processing order.

## Structure
```
event-queue/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/eventqueue/
    └── EventQueue.java
```

## Implementation
The Event Queue pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:concurrency:event-queue:build

# Run the pattern example
./gradlew :system-design-pattern:concurrency:event-queue:run
```

## Category
Concurrency

## Java Version
Java 25
