# Collection Pipeline Pattern

## Overview
Chains collection operations in sequence.

## Structure
```
collection-pipeline/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/collectionpipeline/
    └── CollectionPipeline.java
```

## Implementation
The Collection Pipeline pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:functional:collection-pipeline:build

# Run the pattern example
./gradlew :system-design-pattern:functional:collection-pipeline:run
```

## Category
Functional

## Java Version
Java 25
