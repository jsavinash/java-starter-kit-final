# Data Locality Pattern

## Overview
Organizes data for optimal cache performance.

## Structure
```
data-locality/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/datalocality/
    └── DataLocality.java
```

## Implementation
The Data Locality pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:performance-optimization:data-locality:build

# Run the pattern example
./gradlew :system-design-pattern:performance-optimization:data-locality:run
```

## Category
Performance Optimization

## Java Version
Java 25
