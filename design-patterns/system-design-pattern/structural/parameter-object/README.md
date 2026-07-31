# Parameter Object Pattern

## Overview
Groups method parameters into a single object.

## Structure
```
parameter-object/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/parameterobject/
    └── ParameterObject.java
```

## Implementation
The Parameter Object pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:structural:parameter-object:build

# Run the pattern example
./gradlew :system-design-pattern:structural:parameter-object:run
```

## Category
Structural

## Java Version
Java 25
