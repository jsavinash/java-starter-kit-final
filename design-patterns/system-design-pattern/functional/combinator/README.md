# Combinator Pattern

## Overview
Combines small functions into larger ones.

## Structure
```
combinator/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/combinator/
    └── Combinator.java
```

## Implementation
The Combinator pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:functional:combinator:build

# Run the pattern example
./gradlew :system-design-pattern:functional:combinator:run
```

## Category
Functional

## Java Version
Java 25
