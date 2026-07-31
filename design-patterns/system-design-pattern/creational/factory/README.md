# Factory Pattern

## Overview
Creates objects without specifying exact class.

## Structure
```
factory/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/factory/
    └── Factory.java
```

## Implementation
The Factory pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:creational:factory:build

# Run the pattern example
./gradlew :system-design-pattern:creational:factory:run
```

## Category
Creational

## Java Version
Java 25
