# Model View Controller Pattern

## Overview
Separates data, UI, and logic.

## Structure
```
model-view-controller/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/modelviewcontroller/
    └── ModelViewController.java
```

## Implementation
The Model View Controller pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:architectural:model-view-controller:build

# Run the pattern example
./gradlew :system-design-pattern:architectural:model-view-controller:run
```

## Category
Architectural

## Java Version
Java 25
