# Object Pool Pattern

## Overview
Manages reusable objects to avoid expensive creation.

## Structure
```
object-pool/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/objectpool/
    └── ObjectPool.java
```

## Implementation
The Object Pool pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:creational:object-pool:build

# Run the pattern example
./gradlew :system-design-pattern:creational:object-pool:run
```

## Category
Creational

## Java Version
Java 25
