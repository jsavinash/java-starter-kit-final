# Master Worker Pattern

## Overview
Distributes work among worker threads.

## Structure
```
master-worker/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/masterworker/
    └── MasterWorker.java
```

## Implementation
The Master Worker pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:concurrency:master-worker:build

# Run the pattern example
./gradlew :system-design-pattern:concurrency:master-worker:run
```

## Category
Concurrency

## Java Version
Java 25
