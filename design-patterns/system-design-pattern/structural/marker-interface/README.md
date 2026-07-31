# Marker Interface Pattern

## Overview
Uses empty interfaces to mark classes with metadata.

## Structure
```
marker-interface/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/markerinterface/
    └── MarkerInterface.java
```

## Implementation
The Marker Interface pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:structural:marker-interface:build

# Run the pattern example
./gradlew :system-design-pattern:structural:marker-interface:run
```

## Category
Structural

## Java Version
Java 25
