# Separated Interface Pattern

## Overview
Separates interface definition from implementation.

## Structure
```
separated-interface/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/separatedinterface/
    └── SeparatedInterface.java
```

## Implementation
The Separated Interface pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:structural:separated-interface:build

# Run the pattern example
./gradlew :system-design-pattern:structural:separated-interface:run
```

## Category
Structural

## Java Version
Java 25
