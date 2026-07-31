# Special Case Pattern

## Overview
Handles special cases with polymorphic objects.

## Structure
```
special-case/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/specialcase/
    └── SpecialCase.java
```

## Implementation
The Special Case pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:structural:special-case:build

# Run the pattern example
./gradlew :system-design-pattern:structural:special-case:run
```

## Category
Structural

## Java Version
Java 25
