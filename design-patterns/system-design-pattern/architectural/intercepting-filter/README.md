# Intercepting Filter Pattern

## Overview

The **Intercepting Filter** pattern provides a mechanism to process a request before it reaches the target handler (and after the response is produced). Instead of embedding cross-cutting concerns directly into the handler, these concerns (authentication, logging, compression, rate-limiting, auditing) are implemented as reusable **filters** that are chained together. Each filter runs in sequence, optionally aborting the chain, and the target handler executes only after all filters pass.

This example models a simple **web request pipeline** to demonstrate the full intercepting filter flow.

## Structure

```
intercepting-filter/
├── build.gradle.kts
├── README.md
├── LLD.md
└── src/
    ├── main/java/com/javastarterkit/patterns/interceptingfilter/
    │   ├── InterceptingFilterApp.java
    │   ├── core/
    │   │   ├── Filter.java
    │   │   ├── Target.java
    │   │   ├── FilterChain.java
    │   │   ├── FilterManager.java
    │   │   └── HomePageTarget.java
    │   ├── models/
    │   │   ├── Request.java
    │   │   └── Response.java
    │   └── filters/
    │       ├── AuthenticationFilter.java
    │       ├── LoggingFilter.java
    │       ├── RateLimitFilter.java
    │       ├── CompressionFilter.java
    │       └── AuditFilter.java
    └── test/java/com/javastarterkit/patterns/interceptingfilter/
        └── InterceptingFilterAppTest.java
```

## Implementation

### Core Components

| Component | Responsibility |
|-----------|---------------|
| `Request` | Immutable record carrying method, user, path, and payload |
| `Response` | Mutable response produced by the pipeline (status + body) |
| `Filter` | Abstract base class with `before` / `after` hooks |
| `FilterChain` | Holds ordered filters and invokes them around a `Target`; aborts if any filter returns `false` from `before` |
| `Target` | The actual business handler interface |
| `FilterManager` | Entry point for clients; owns the `FilterChain` and exposes `process()` |

### Target

| Component | Responsibility |
|-----------|---------------|
| `HomePageTarget` | Simple target that renders the home page for a user |

### Concrete Filters

| Filter | Responsibility |
|--------|---------------|
| `AuthenticationFilter` | Rejects unauthenticated requests (aborts chain) |
| `LoggingFilter` | Logs every request (before) and response (after) |
| `RateLimitFilter` | Limits requests per user; aborts when limit exceeded |
| `CompressionFilter` | Simulates gzip-style compression of the response body (after) |
| `AuditFilter` | Records every request after processing |

### Manager

| Component | Responsibility |
|-----------|---------------|
| `FilterManager` | Entry point for clients; owns the `FilterChain` and exposes `process()` |

### Flow
1. Client calls `FilterManager.process(request)`.
2. `FilterChain` runs each filter's `before` hook in order.
3. If any `before` returns `false`, the chain aborts with a `403` response (target never invoked).
4. Otherwise the `Target` executes and produces the response.
5. All filters' `after` hooks run in reverse order.

## Usage

```bash
# Build the pattern
./gradlew :system-design-pattern:architectural:intercepting-filter:build

# Run the tests
./gradlew :system-design-pattern:architectural:intercepting-filter:test
```

## Sample Output

```
=== Intercepting Filter Pattern ===
Process requests through a chain of reusable filters

--- Request 1: valid user 'alice' ---
  [AUTH] Authenticated user 'alice'
  [LOG]  -> GET /home user=alice
  [RATE] User 'alice' request 1/3
  [COMP] Preparing compression for /home
  [TARGET] Home page rendered for user 'alice'
  [COMP] Compressed response (XX -> XX chars)
  [LOG]  <- status=200 body='<compressed>Welcome alice...<compressed>'
  [AUDIT] Recorded GET /home for user 'alice' (status=200)
  Response status: 200 | body: <compressed>Welcome alice...</compressed>

--- Request 2: rate limit exceeded ---
  ...
  Response status: 403 | body: Blocked by RateLimitFilter

--- Request 3: unauthenticated ---
  [AUTH] Rejecting unauthenticated request to /admin
  Response status: 403 | body: Blocked by AuthenticationFilter

Benefits:
- Cross-cutting concerns isolated into reusable filters
- Filters can abort the chain (auth failure, rate limit)
- New concerns added without changing the target handler
- Filters execute in configurable order
```

## Benefits

- **Separation of concerns** — cross-cutting logic (auth, logging, compression) is isolated in reusable filters rather than scattered through handlers.
- **Reusable** — the same filter can be applied to multiple targets or requests.
- **Composable** — filters can be added, removed, or reordered without changing the target.
- **Short-circuiting** — a filter can abort the chain (e.g., auth failure, rate limit) before the target is invoked.
- **Symmetry** — `before` hooks run in order; `after` hooks run in reverse (stack-like semantics).

## Trade-offs

- **Ordering complexity** — filter order is significant; incorrect ordering can break behavior.
- **Indirection** — requests pass through many layers, making debugging slightly harder.
- **Overhead** — each filter adds a method-call hop and potential processing cost.
- **Duplication risk** — if filters are not carefully designed, similar logic can appear in multiple filters.

## Category

Architectural

## Java Version

Java 25
