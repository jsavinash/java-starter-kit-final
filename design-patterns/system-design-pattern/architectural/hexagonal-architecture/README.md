# Hexagonal Architecture Pattern

## Overview

Hexagonal Architecture, also known as **Ports & Adapters**, isolates the core business logic (the **domain**) from external concerns such as databases, UIs, and messaging systems. The domain sits at the center and communicates with the outside world only through well-defined **ports** (interfaces). Each port can have multiple **adapters** (implementations) that plug into it — for example, a REST controller or a console command as a *driving* adapter, and an in-memory or JDBC repository as a *driven* adapter.

This example models a simple **bank account** system to demonstrate the full hexagonal flow.

## Structure

```
hexagonal-architecture/
├── build.gradle.kts
├── README.md
└── src/
    ├── main/java/com/javastarterkit/patterns/hexagonalarchitecture/
    │   └── HexagonalArchitecture.java
    └── test/java/com/javastarterkit/patterns/hexagonalarchitecture/
        └── HexagonalArchitectureTest.java
```

## Implementation

The example is a single self-contained Java file with inner static classes/interfaces organized into four layers:

### Domain (Core)
| Component | Responsibility |
|-----------|---------------|
| `Money` | Immutable value object for non-negative amounts (uses `BigDecimal`) |
| `Account` | Domain entity with owner, balance, and transaction history; enforces business rules (no negative balances, positive deposits/withdrawals) |
| `Transaction` | Immutable value object recording a single deposit/withdrawal |

### Application (Use Cases)
| Component | Responsibility |
|-----------|---------------|
| `AccountRepository` | **Outbound port** (driven): persistence contract — the application depends on this interface, never a concrete database |
| `NotificationPort` | **Outbound port** (driven): notification contract — the application sends notifications without knowing the delivery mechanism |
| `AccountService` | Application service implementing use cases (`openAccount`, `deposit`, `withdraw`, `getAccount`) by coordinating the domain and the ports |

### Driving Adapters (Inbound)
| Component | Responsibility |
|-----------|---------------|
| `ConsoleAdapter` | CLI adapter that translates user input into calls on `AccountService` |
| `RestAdapter` | Simulated REST controller (HTTP verbs with JSON-ish payloads) that drives the same use cases |

### Driven Adapters (Outbound)
| Component | Responsibility |
|-----------|---------------|
| `InMemoryAccountRepository` | In-memory implementation of `AccountRepository` (tests, demos, prototypes) |
| `JdbcAccountRepository` | Simulated JDBC implementation of `AccountRepository` (shows adapter swap requires no core changes) |
| `EmailNotificationAdapter` | Email-based implementation of `NotificationPort` |
| `SmsNotificationAdapter` | SMS-based implementation of `NotificationPort` |

### Flow
1. A **driving adapter** (CLI or REST) calls the `AccountService` (the application port).
2. `AccountService` orchestrates the use case by invoking domain logic on `Account`.
3. The service persists through the `AccountRepository` port and notifies through the `NotificationPort`.
4. **Driven adapters** (InMemory/Jdbc, Email/SMS) implement those ports and can be swapped without touching core logic.

## Usage

```bash
# Build the pattern
./gradlew :system-design-pattern:architectural:hexagonal-architecture:build

# Run the tests
./gradlew :system-design-pattern:architectural:hexagonal-architecture:test
```

## Sample Output

```
=== Hexagonal Architecture (Ports & Adapters) ===
Isolate core business logic from external concerns

--- Driving adapter: Console (CLI) ---
  [EMAIL] To: Alice | Welcome! Account <uuid> opened with 100.00
  [CLI] Opened account <uuid> for Alice with balance 100.00
  [EMAIL] To: Alice | Deposited 50.00. New balance: 150.00
  [CLI] Deposited 50.00 -> Account{id=<uuid>, owner=Alice, balance=150.00}
  [EMAIL] To: Alice | Withdrew 30.00. New balance: 120.00
  [CLI] Withdrew 30.00 -> Account{id=<uuid>, owner=Alice, balance=120.00}
  [CLI] Balance for Alice: 120.00

--- Driving adapter: REST (simulated HTTP) ---
  [EMAIL] To: Bob | Welcome! Account <uuid> opened with 200.00
  [REST] POST /accounts -> 201 Created: <uuid>
  [EMAIL] To: Bob | Deposited 100.00. New balance: 300.00
  [REST] POST /accounts/<uuid>/deposits -> 200 OK: Account{id=<uuid>, owner=Bob, balance=300.00}
  [REST] GET /accounts/<uuid> -> 200 OK: Account{id=<uuid>, owner=Bob, balance=300.00}

--- Swapping driven adapter: InMemory -> Jdbc ---
  [SMS]   To: Carol | Welcome! Account <uuid> opened with 500.00
  [JDBC] Persisted account <uuid> (balance=500.00)
  [CLI] Opened account <uuid> for Carol with balance 500.00
  [SMS]   To: Carol | Withdrew 120.00. New balance: 380.00
  [JDBC] Persisted account <uuid> (balance=380.00)
  [CLI] Withdrew 120.00 -> Account{id=<uuid>, owner=Carol, balance=380.00}
  [CLI] Balance for Carol: 380.00

Benefits:
- Core domain is framework-agnostic (no DB/HTTP/UI dependencies)
- Ports define contracts; adapters are swappable
- Driving adapters (CLI, REST) share the same use cases
- Driven adapters (InMemory, Jdbc) are interchangeable
- Easy to test: mock ports instead of real infrastructure
```

## Benefits

- **Framework-agnostic core** — the domain and application layers have zero dependencies on databases, HTTP, or UI frameworks.
- **Swappable adapters** — replacing an in-memory repository with a JDBC one (or email with SMS) requires no changes to core logic.
- **Multiple driving adapters** — CLI, REST, and other inbound adapters all share the same use cases.
- **Testability** — ports can be mocked or replaced with lightweight fakes in unit tests.
- **Clear boundaries** — the dependency rule points inward: adapters depend on ports, ports depend on the application, the application depends on the domain.

## Trade-offs

- **Complexity** — requires discipline to keep the domain free of framework concerns and to define ports up front.
- **Indirection** — the extra port/adapter layer can feel like over-engineering for simple CRUD applications.
- **Mapping overhead** — data often needs to be mapped between domain objects and adapter-specific DTOs/entities.
- **Learning curve** — teams must understand the port/adapter mental model to avoid leaking infrastructure into the core.

## Category

Architectural

## Java Version

Java 25