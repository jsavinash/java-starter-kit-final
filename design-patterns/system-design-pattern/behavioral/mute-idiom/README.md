# Mute Idiom Pattern

## Overview
Suppresses exceptions for cleaner code.

## Structure
```
mute-idiom/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/muteidiom/
    └── MuteIdiom.java
```

## Implementation
The Mute Idiom pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:behavioral:mute-idiom:build

# Run the pattern example
./gradlew :system-design-pattern:behavioral:mute-idiom:run
```

## Category
Behavioral

## Java Version
Java 25
