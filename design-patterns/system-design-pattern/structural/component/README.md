# Component Pattern

## Overview
Allows individual objects to be composed into larger structures.

## Structure
```
component/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/component/
    └── Component.java
```

## Implementation
The Component pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:structural:component:build

# Run the pattern example
./gradlew :system-design-pattern:structural:component:run
```

## Category
Structural

## Java Version
Java 25
