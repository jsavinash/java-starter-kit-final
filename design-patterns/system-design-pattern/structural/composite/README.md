# Composite Pattern

## Overview
Treats individual and composite objects uniformly.

## Structure
```
composite/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/composite/
    └── Composite.java
```

## Implementation
The Composite pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:structural:composite:build

# Run the pattern example
./gradlew :system-design-pattern:structural:composite:run
```

## Category
Structural

## Java Version
Java 25
