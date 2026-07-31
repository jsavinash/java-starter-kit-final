# Singleton Pattern

## Overview
Ensures a class has only one instance.

## Structure
```
singleton/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/singleton/
    └── Singleton.java
```

## Implementation
The Singleton pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:structural:singleton:build

# Run the pattern example
./gradlew :system-design-pattern:structural:singleton:run
```

## Category
Structural

## Java Version
Java 25
