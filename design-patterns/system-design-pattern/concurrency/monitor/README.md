# Monitor Pattern

## Overview
Synchronizes access to shared resources.

## Structure
```
monitor/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/monitor/
    └── Monitor.java
```

## Implementation
The Monitor pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:concurrency:monitor:build

# Run the pattern example
./gradlew :system-design-pattern:concurrency:monitor:run
```

## Category
Concurrency

## Java Version
Java 25
