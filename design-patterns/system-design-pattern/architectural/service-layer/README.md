# Service Layer Pattern

## Overview
Defines the application boundary with a service layer.

## Structure
```
service-layer/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/servicelayer/
    └── ServiceLayer.java
```

## Implementation
The Service Layer pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:architectural:service-layer:build

# Run the pattern example
./gradlew :system-design-pattern:architectural:service-layer:run
```

## Category
Architectural

## Java Version
Java 25
