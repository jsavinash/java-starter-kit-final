# Private Class Data Pattern

## Overview
Restricts access to class data.

## Structure
```
private-class-data/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/privateclassdata/
    └── PrivateClassData.java
```

## Implementation
The Private Class Data pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:structural:private-class-data:build

# Run the pattern example
./gradlew :system-design-pattern:structural:private-class-data:run
```

## Category
Structural

## Java Version
Java 25
