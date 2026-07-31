# Extension Objects Pattern

## Overview
Adds functionality to objects through extension interfaces.

## Structure
```
extension-objects/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/extensionobjects/
    └── ExtensionObjects.java
```

## Implementation
The Extension Objects pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:structural:extension-objects:build

# Run the pattern example
./gradlew :system-design-pattern:structural:extension-objects:run
```

## Category
Structural

## Java Version
Java 25
