# Transaction Script Pattern

## Overview
Organizes business logic by transaction.

## Structure
```
transaction-script/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/transactionscript/
    └── TransactionScript.java
```

## Implementation
The Transaction Script pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:data-access:transaction-script:build

# Run the pattern example
./gradlew :system-design-pattern:data-access:transaction-script:run
```

## Category
Data Access

## Java Version
Java 25
