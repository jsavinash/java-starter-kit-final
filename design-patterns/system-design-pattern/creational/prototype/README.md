# Prototype Pattern

## Overview
Creates new objects by copying existing ones.

## Structure
```
prototype/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/prototype/
    └── Prototype.java
```

## Implementation
The Prototype pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:creational:prototype:build

# Run the pattern example
./gradlew :system-design-pattern:creational:prototype:run
```

## Category
Creational

## Java Version
Java 25
