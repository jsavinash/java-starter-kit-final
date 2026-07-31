# Serialized Entity Pattern

## Overview
Serializes entities for storage.

## Structure
```
serialized-entity/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/serializedentity/
    └── SerializedEntity.java
```

## Implementation
The Serialized Entity pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:data-access:serialized-entity:build

# Run the pattern example
./gradlew :system-design-pattern:data-access:serialized-entity:run
```

## Category
Data Access

## Java Version
Java 25
