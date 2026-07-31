# Data Mapper Pattern

## Overview
Maps between objects and data stores.

## Structure
```
data-mapper/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/datamapper/
    └── DataMapper.java
```

## Implementation
The Data Mapper pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:behavioral:data-mapper:build

# Run the pattern example
./gradlew :system-design-pattern:behavioral:data-mapper:run
```

## Category
Behavioral

## Java Version
Java 25
