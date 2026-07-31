# Sharding Pattern

## Overview
Horizontal partitioning across databases.

## Structure
```
sharding/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/sharding/
    └── Sharding.java
```

## Implementation
The Sharding pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:data-access:sharding:build

# Run the pattern example
./gradlew :system-design-pattern:data-access:sharding:run
```

## Category
Data Access

## Java Version
Java 25
