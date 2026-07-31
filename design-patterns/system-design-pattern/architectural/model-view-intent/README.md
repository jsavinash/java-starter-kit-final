# Model View Intent Pattern

## Overview
Unidirectional data flow with intents.

## Structure
```
model-view-intent/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/modelviewintent/
    └── ModelViewIntent.java
```

## Implementation
The Model View Intent pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:architectural:model-view-intent:build

# Run the pattern example
./gradlew :system-design-pattern:architectural:model-view-intent:run
```

## Category
Architectural

## Java Version
Java 25
