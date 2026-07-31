# Page Controller Pattern

## Overview
Each page has its own controller.

## Structure
```
page-controller/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/pagecontroller/
    └── PageController.java
```

## Implementation
The Page Controller pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:architectural:page-controller:build

# Run the pattern example
./gradlew :system-design-pattern:architectural:page-controller:run
```

## Category
Architectural

## Java Version
Java 25
