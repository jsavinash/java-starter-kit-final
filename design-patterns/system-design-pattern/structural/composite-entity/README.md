# Composite Entity Pattern

## Overview
Manages a group of related objects as a single entity.

## Structure
```
composite-entity/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/compositeentity/
    └── CompositeEntity.java
```

## Implementation
The Composite Entity pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:structural:composite-entity:build

# Run the pattern example
./gradlew :system-design-pattern:structural:composite-entity:run
```

## Category
Structural

## Java Version
Java 25
