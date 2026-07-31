# Domain Model Pattern

## Overview
Rich domain model with behavior and business logic.

## Structure
```
domain-model/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/domainmodel/
    └── DomainModel.java
```

## Implementation
The Domain Model pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:structural:domain-model:build

# Run the pattern example
./gradlew :system-design-pattern:structural:domain-model:run
```

## Category
Structural

## Java Version
Java 25
