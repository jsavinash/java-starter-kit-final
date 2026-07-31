# Resource Acquisition Is Initialization Pattern

## Overview
Ties resource lifecycle to object lifetime.

## Structure
```
resource-acquisition-is-initialization/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/resourceacquisitionisinitialization/
    └── RAII.java
```

## Implementation
The Resource Acquisition Is Initialization pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:resource-management:resource-acquisition-is-initialization:build

# Run the pattern example
./gradlew :system-design-pattern:resource-management:resource-acquisition-is-initialization:run
```

## Category
Resource Management

## Java Version
Java 25
