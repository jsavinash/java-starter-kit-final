# Value Object Pattern

## Overview
Immutable objects compared by their values.

## Structure
```
value-object/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/valueobject/
    └── ValueObject.java
```

## Implementation
The Value Object pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:structural:value-object:build

# Run the pattern example
./gradlew :system-design-pattern:structural:value-object:run
```

## Category
Structural

## Java Version
Java 25
