# Version Number Pattern

## Overview
Manages concurrent access with version numbers.

## Structure
```
version-number/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/versionnumber/
    └── VersionNumber.java
```

## Implementation
The Version Number pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:data-access:version-number:build

# Run the pattern example
./gradlew :system-design-pattern:data-access:version-number:run
```

## Category
Data Access

## Java Version
Java 25
