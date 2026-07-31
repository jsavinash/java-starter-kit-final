# Optimistic Offline Lock Pattern

## Overview
Prevents conflicts using version numbers.

## Structure
```
optimistic-offline-lock/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/optimisticofflinelock/
    └── OptimisticOfflineLock.java
```

## Implementation
The Optimistic Offline Lock pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:data-access:optimistic-offline-lock:build

# Run the pattern example
./gradlew :system-design-pattern:data-access:optimistic-offline-lock:run
```

## Category
Data Access

## Java Version
Java 25
