# Repository Pattern

## Overview
Mediates between domain and data mapping layers.

## Structure
```
repository/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/repository/
    └── Repository.java
```

## Implementation
The Repository pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:data-access:repository:build

# Run the pattern example
./gradlew :system-design-pattern:data-access:repository:run
```

## Category
Data Access

## Java Version
Java 25
