# Unit Of Work Pattern

## Overview
Groups operations into a single transaction.

## Structure
```
unit-of-work/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/unitofwork/
    └── UnitOfWork.java
```

## Implementation
The Unit Of Work pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:data-access:unit-of-work:build

# Run the pattern example
./gradlew :system-design-pattern:data-access:unit-of-work:run
```

## Category
Data Access

## Java Version
Java 25
