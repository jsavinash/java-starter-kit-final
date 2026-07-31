# Service To Worker Pattern

## Overview
Separates request processing from view management.

## Structure
```
service-to-worker/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/servicetoworker/
    └── ServiceToWorker.java
```

## Implementation
The Service To Worker pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:architectural:service-to-worker:build

# Run the pattern example
./gradlew :system-design-pattern:architectural:service-to-worker:run
```

## Category
Architectural

## Java Version
Java 25
