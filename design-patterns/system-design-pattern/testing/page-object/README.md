# Page Object Pattern

## Overview
Encapsulates page details in test automation.

## Structure
```
page-object/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/pageobject/
    └── PageObject.java
```

## Implementation
The Page Object pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:testing:page-object:build

# Run the pattern example
./gradlew :system-design-pattern:testing:page-object:run
```

## Category
Testing

## Java Version
Java 25
