# Twin Pattern

## Overview
Provides a way to have multiple inheritance.

## Structure
```
twin/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/twin/
    └── Twin.java
```

## Implementation
The Twin pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:structural:twin:build

# Run the pattern example
./gradlew :system-design-pattern:structural:twin:run
```

## Category
Structural

## Java Version
Java 25
