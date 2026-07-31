# Data Transfer Object Pattern

## Overview
Transfers data between subsystems without exposing internals.

## Structure
```
data-transfer-object/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/datatransferobject/
    └── DataTransferObject.java
```

## Implementation
The Data Transfer Object pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:structural:data-transfer-object:build

# Run the pattern example
./gradlew :system-design-pattern:structural:data-transfer-object:run
```

## Category
Structural

## Java Version
Java 25
