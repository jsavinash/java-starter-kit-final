# Type Object Pattern

## Overview
Allows creation of flexible type systems at runtime.

## Structure
```
type-object/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/typeobject/
    └── TypeObject.java
```

## Implementation
The Type Object pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:creational:type-object:build

# Run the pattern example
./gradlew :system-design-pattern:creational:type-object:run
```

## Category
Creational

## Java Version
Java 25
