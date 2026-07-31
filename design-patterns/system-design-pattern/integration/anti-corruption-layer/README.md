# Anti Corruption Layer Pattern

## Overview
Protects domain from legacy system contamination.

## Structure
```
anti-corruption-layer/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/anticorruptionlayer/
    └── AntiCorruptionLayer.java
```

## Implementation
The Anti Corruption Layer pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:integration:anti-corruption-layer:build

# Run the pattern example
./gradlew :system-design-pattern:integration:anti-corruption-layer:run
```

## Category
Integration

## Java Version
Java 25
