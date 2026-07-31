# Factory Kit Pattern

## Overview
A flexible factory that can be configured with different builders.

## Structure
```
factory-kit/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/factorykit/
    └── FactoryKit.java
```

## Implementation
The Factory Kit pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:creational:factory-kit:build

# Run the pattern example
./gradlew :system-design-pattern:creational:factory-kit:run
```

## Category
Creational

## Java Version
Java 25
