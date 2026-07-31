# Object Mother Pattern

## Overview
Creates pre-configured test objects.

## Structure
```
object-mother/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/objectmother/
    └── ObjectMother.java
```

## Implementation
The Object Mother pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:testing:object-mother:build

# Run the pattern example
./gradlew :system-design-pattern:testing:object-mother:run
```

## Category
Testing

## Java Version
Java 25
