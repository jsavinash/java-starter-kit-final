# Function Composition Pattern

## Overview
Combines simple functions to build complex ones.

## Structure
```
function-composition/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/functioncomposition/
    └── FunctionComposition.java
```

## Implementation
The Function Composition pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:functional:function-composition:build

# Run the pattern example
./gradlew :system-design-pattern:functional:function-composition:run
```

## Category
Functional

## Java Version
Java 25
