# Read Write Lock Pattern

## Overview
Allows concurrent reads, exclusive writes.

## Structure
```
read-write-lock/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/readwritelock/
    └── Readwritelock.java
```

## Implementation
The Read Write Lock pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:concurrency:read-write-lock:build

# Run the pattern example
./gradlew :system-design-pattern:concurrency:read-write-lock:run
```

## Category
Concurrency

## Java Version
Java 25
