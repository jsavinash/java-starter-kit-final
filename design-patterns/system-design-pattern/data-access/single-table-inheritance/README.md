# Single Table Inheritance Pattern

## Overview
Stores inheritance hierarchy in one table.

## Structure
```
single-table-inheritance/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/singletableinheritance/
    └── SingleTableInheritance.java
```

## Implementation
The Single Table Inheritance pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:data-access:single-table-inheritance:build

# Run the pattern example
./gradlew :system-design-pattern:data-access:single-table-inheritance:run
```

## Category
Data Access

## Java Version
Java 25
