# Template Method Pattern

## Overview
Defines algorithm skeleton.

## Structure
```
template-method/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/templatemethod/
    └── Templatemethod.java
```

## Implementation
The Template Method pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:behavioral:template-method:build

# Run the pattern example
./gradlew :system-design-pattern:behavioral:template-method:run
```

## Category
Behavioral

## Java Version
Java 25
