# Caching Pattern

## Overview
Stores frequently accessed data for fast retrieval.

## Structure
```
caching/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/caching/
    └── Caching.java
```

## Implementation
The Caching pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:performance-optimization:caching:build

# Run the pattern example
./gradlew :system-design-pattern:performance-optimization:caching:run
```

## Category
Performance Optimization

## Java Version
Java 25
