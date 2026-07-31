# Data Access Object Pattern

## Overview
Abstracts database operations behind a clean interface.

## Structure
```
data-access-object/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/dataaccessobject/
    └── DataAccessObject.java
```

## Implementation
The Data Access Object pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:structural:data-access-object:build

# Run the pattern example
./gradlew :system-design-pattern:structural:data-access-object:run
```

## Category
Structural

## Java Version
Java 25
