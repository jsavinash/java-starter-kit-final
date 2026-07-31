# Tolerant Reader Pattern

## Overview
Reads only understood fields, ignoring unknown data.

## Structure
```
tolerant-reader/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/tolerantreader/
    └── TolerantReader.java
```

## Implementation
The Tolerant Reader pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:resilience:tolerant-reader:build

# Run the pattern example
./gradlew :system-design-pattern:resilience:tolerant-reader:run
```

## Category
Resilience

## Java Version
Java 25
