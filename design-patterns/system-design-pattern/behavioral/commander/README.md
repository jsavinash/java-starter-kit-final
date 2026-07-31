# Commander Pattern

## Overview
Manages distributed transaction execution.

## Structure
```
commander/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/commander/
    └── Commander.java
```

## Implementation
The Commander pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:behavioral:commander:build

# Run the pattern example
./gradlew :system-design-pattern:behavioral:commander:run
```

## Category
Behavioral

## Java Version
Java 25
