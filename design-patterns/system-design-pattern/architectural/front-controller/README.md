# Front Controller Pattern

## Overview
Centralizes request handling.

## Structure
```
front-controller/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/frontcontroller/
    └── FrontController.java
```

## Implementation
The Front Controller pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:architectural:front-controller:build

# Run the pattern example
./gradlew :system-design-pattern:architectural:front-controller:run
```

## Category
Architectural

## Java Version
Java 25
