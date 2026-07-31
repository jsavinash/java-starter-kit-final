# Model View Viewmodel Pattern

## Overview
Separates UI from business logic with data binding.

## Structure
```
model-view-viewmodel/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/modelviewviewmodel/
    └── ModelViewViewmodel.java
```

## Implementation
The Model View Viewmodel pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:architectural:model-view-viewmodel:build

# Run the pattern example
./gradlew :system-design-pattern:architectural:model-view-viewmodel:run
```

## Category
Architectural

## Java Version
Java 25
