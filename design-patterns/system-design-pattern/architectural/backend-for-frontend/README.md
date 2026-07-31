# Backend For Frontend Pattern

## Overview
Creates separate backends for each client type.

## Structure
```
backend-for-frontend/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/backendforfrontend/
    └── BackendForFrontend.java
```

## Implementation
The Backend For Frontend pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:architectural:backend-for-frontend:build

# Run the pattern example
./gradlew :system-design-pattern:architectural:backend-for-frontend:run
```

## Category
Architectural

## Java Version
Java 25
