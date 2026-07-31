# Monostate Pattern

## Overview
Shares state across instances via static fields.

## Structure
```
monostate/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/monostate/
    └── Monostate.java
```

## Implementation
The Monostate pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:creational:monostate:build

# Run the pattern example
./gradlew :system-design-pattern:creational:monostate:run
```

## Category
Creational

## Java Version
Java 25
