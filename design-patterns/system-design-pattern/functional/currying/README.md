# Currying Pattern

## Overview
Transforms multi-argument functions into chains.

## Structure
```
currying/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/currying/
    └── Currying.java
```

## Implementation
The Currying pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:functional:currying:build

# Run the pattern example
./gradlew :system-design-pattern:functional:currying:run
```

## Category
Functional

## Java Version
Java 25
