# Composite View Pattern

## Overview
Builds views from smaller, reusable components.

## Structure
```
composite-view/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/compositeview/
    └── CompositeView.java
```

## Implementation
The Composite View pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:structural:composite-view:build

# Run the pattern example
./gradlew :system-design-pattern:structural:composite-view:run
```

## Category
Structural

## Java Version
Java 25
