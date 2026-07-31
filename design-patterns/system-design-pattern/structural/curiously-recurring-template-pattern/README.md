# Curiously Recurring Template Pattern Pattern

## Overview
Template where a class passes itself as a parameter.

## Structure
```
curiously-recurring-template-pattern/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/curiouslyrecurringtemplatepattern/
    └── CuriouslyRecurringTemplatePattern.java
```

## Implementation
The Curiously Recurring Template Pattern pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:structural:curiously-recurring-template-pattern:build

# Run the pattern example
./gradlew :system-design-pattern:structural:curiously-recurring-template-pattern:run
```

## Category
Structural

## Java Version
Java 25
