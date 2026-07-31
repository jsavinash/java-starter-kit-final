# Half Sync Half Async Pattern

## Overview
Separates sync and async processing.

## Structure
```
half-sync-half-async/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/halfsynchalfasync/
    └── HalfSyncHalfAsync.java
```

## Implementation
The Half Sync Half Async pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:concurrency:half-sync-half-async:build

# Run the pattern example
./gradlew :system-design-pattern:concurrency:half-sync-half-async:run
```

## Category
Concurrency

## Java Version
Java 25
