# Step Builder Pattern

## Overview
Guides object construction through predefined steps.

## Structure
```
step-builder/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/stepbuilder/
    └── StepBuilder.java
```

## Implementation
The Step Builder pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:creational:step-builder:build

# Run the pattern example
./gradlew :system-design-pattern:creational:step-builder:run
```

## Category
Creational

## Java Version
Java 25
