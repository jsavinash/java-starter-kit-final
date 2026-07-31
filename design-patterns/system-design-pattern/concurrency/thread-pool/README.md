# Thread Pool Pattern

## Overview
Manages a pool of reusable threads.

## Structure
```
thread-pool/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/threadpool/
    └── Threadpool.java
```

## Implementation
The Thread Pool pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:concurrency:thread-pool:build

# Run the pattern example
./gradlew :system-design-pattern:concurrency:thread-pool:run
```

## Category
Concurrency

## Java Version
Java 25
