# Role Object Pattern

## Overview
Allows objects to adapt to different roles.

## Structure
```
role-object/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/roleobject/
    └── RoleObject.java
```

## Implementation
The Role Object pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:structural:role-object:build

# Run the pattern example
./gradlew :system-design-pattern:structural:role-object:run
```

## Category
Structural

## Java Version
Java 25
