# Command Query Responsibility Segregation Pattern

## Overview
Separates read and write operations.

## Structure
```
command-query-responsibility-segregation/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/commandqueryresponsibilitysegregation/
    └── CommandQueryResponsibilitySegregation.java
```

## Implementation
The Command Query Responsibility Segregation pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:architectural:command-query-responsibility-segregation:build

# Run the pattern example
./gradlew :system-design-pattern:architectural:command-query-responsibility-segregation:run
```

## Category
Architectural

## Java Version
Java 25
