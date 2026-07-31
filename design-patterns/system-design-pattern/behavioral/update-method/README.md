# Update Method Pattern

## Overview
Updates game objects each frame.

## Structure
```
update-method/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/updatemethod/
    └── UpdateMethod.java
```

## Implementation
The Update Method pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:behavioral:update-method:build

# Run the pattern example
./gradlew :system-design-pattern:behavioral:update-method:run
```

## Category
Behavioral

## Java Version
Java 25
