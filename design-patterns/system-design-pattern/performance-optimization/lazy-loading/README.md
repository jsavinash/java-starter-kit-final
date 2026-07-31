# Lazy Loading Pattern

## Overview
Defers object creation until needed.

## Structure
```
lazy-loading/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/lazyloading/
    └── Lazyloading.java
```

## Implementation
The Lazy Loading pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:performance-optimization:lazy-loading:build

# Run the pattern example
./gradlew :system-design-pattern:performance-optimization:lazy-loading:run
```

## Category
Performance Optimization

## Java Version
Java 25
