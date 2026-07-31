# Dependency Injection Pattern

## Overview
Passes dependencies from outside rather than creating them internally.

## Structure
```
dependency-injection/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/dependencyinjection/
    └── DependencyInjection.java
```

## Implementation
The Dependency Injection pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:creational:dependency-injection:build

# Run the pattern example
./gradlew :system-design-pattern:creational:dependency-injection:run
```

## Category
Creational

## Java Version
Java 25
