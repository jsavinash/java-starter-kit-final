# Pipeline Pattern

## Overview
Processes data through a sequence of stages.

## Structure
```
pipeline/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/pipeline/
    └── Pipeline.java
```

## Implementation
The Pipeline pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:behavioral:pipeline:build

# Run the pattern example
./gradlew :system-design-pattern:behavioral:pipeline:run
```

## Category
Behavioral

## Java Version
Java 25
