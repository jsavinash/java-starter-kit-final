# Metadata Mapping Pattern

## Overview
Maps database metadata to objects.

## Structure
```
metadata-mapping/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/metadatamapping/
    └── MetadataMapping.java
```

## Implementation
The Metadata Mapping pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:data-access:metadata-mapping:build

# Run the pattern example
./gradlew :system-design-pattern:data-access:metadata-mapping:run
```

## Category
Data Access

## Java Version
Java 25
