#!/usr/bin/env python3
"""
Restructure design-patterns folder to match iluwatar/java-design-patterns categories
"""
import os
import shutil

# Define the category mapping based on iluwatar/java-design-patterns
CATEGORIES = {
    "creational": [
        "abstract-factory", "builder", "factory-method", "prototype", "singleton",
        "factory-kit", "object-mother", "pool"
    ],
    "structural": [
        "adapter", "bridge", "composite", "decorator", "facade", "flyweight", "proxy",
        "delegation", "extension-objects"
    ],
    "behavioral": [
        "chain-of-responsibility", "command", "interpreter", "iterator", "mediator",
        "memento", "observer", "state", "strategy", "template-method", "visitor",
        "acyclic-visitor", "double-dispatch", "null-object", "specification", "servant",
        "class-data", "role-object", "type-object", "twin", "marker-interface",
        "property", "special-case", "transaction-script", "domain-model"
    ],
    "concurrency": [
        "producer-consumer", "read-write-lock", "thread-pool", "thread-specific-storage",
        "active-object", "async-method-invocation", "leader-followers", "leader-election",
        "balking", "guarded-suspension"
    ],
    "architectural": [
        "layered-architecture", "monolithic-architecture", "microservices-api-gateway",
        "microservices-client-side-ui-composition", "microservices-distributed-tracing",
        "microservices-idempotent-consumer", "microservices-self-registration",
        "event-driven-architecture", "clean-architecture", "hexagonal-architecture",
        "cqrs", "event-sourcing", "saga"
    ],
    "enterprise": [
        "service-layer", "repository", "unit-of-work", "data-mapper", "active-record",
        "identity-map", "lazy-loading", "optimistic-offline-lock", "data-access-object",
        "dao-factory", "metadata-mapping", "query-object", "service-stub", "data-transfer-object"
    ],
    "integration": [
        "message-channel", "message-router", "message-translator", "publish-subscribe",
        "competing-consumers", "event-aggregator", "message-bus", "request-reply",
        "correlation-identifier", "return-address", "scatter-gather", "aggregator",
        "resequencer", "content-based-router", "message-filter"
    ],
    "additional": [
        "abstract-document", "actor-model", "ambassador", "anti-corruption-layer",
        "arrange-act-assert", "backpressure", "bloc", "business-delegate", "bytecode",
        "caching", "callback", "circuit-breaker", "client-session", "collecting-parameter",
        "collection-pipeline", "combinator", "command-query-responsibility-segregation",
        "commander", "component", "composite-entity", "composite-view", "context-object",
        "converter", "curiously-recurring-template-pattern", "currying", "data-bus",
        "data-locality", "dependency-injection", "dirty-flag", "double-buffer",
        "double-checked-locking", "dynamic-proxy", "event-based-asynchronous", "event-queue",
        "execute-around", "factory", "fanout-fanin", "feature-toggle", "filterer",
        "fluent-interface", "flux", "front-controller", "function-composition", "game-loop",
        "gateway", "half-sync-half-async", "health-check", "immutable", "intercepting-filter",
        "lockable-object", "map-reduce", "master-worker", "microservices-aggregrator",
        "microservices-api-gateway", "microservices-client-side-ui-composition",
        "microservices-distributed-tracing", "microservices-idempotent-consumer",
        "microservices-log-aggregation", "microservices-self-registration",
        "model-view-controller", "model-view-intent", "model-view-presenter",
        "model-view-viewmodel", "monad", "money", "monitor", "monostate", "multiton",
        "mute-idiom", "naked-objects", "notification", "object-pool", "page-controller",
        "page-object", "parameter-object", "partial-response", "pipeline", "poison-pill",
        "polling-publisher", "presentation-model", "private-class-data", "promise",
        "queue-based-load-leveling", "rate-limiting", "reactor", "registry",
        "resource-acquisition-is-initialization", "retry", "separated-interface",
        "serialized-lob", "server-session", "service-locator", "service-to-worker",
        "session-facade", "sharding", "single-table-inheritance", "spatial-partition",
        "step-builder", "strangler", "subclass-sandbox", "table-inheritance", "table-module",
        "templateview", "throttling", "tolerant-reader", "trampoline", "update-method",
        "value-object", "version-number", "view-helper", "virtual-proxy"
    ]
}

def find_pattern_category(pattern_name):
    """Find which category a pattern belongs to"""
    for category, patterns in CATEGORIES.items():
        if pattern_name in patterns:
            return category
    return "additional"

def restructure_design_patterns():
    base_dir = "design-patterns"
    
    # Create category directories
    for category in CATEGORIES.keys():
        os.makedirs(os.path.join(base_dir, category), exist_ok=True)
    
    # Move pattern directories to their category folders
    moved_count = 0
    for category, patterns in CATEGORIES.items():
        for pattern in patterns:
            # Check various possible locations for the pattern
            possible_paths = [
                os.path.join(base_dir, pattern),
                os.path.join(base_dir, pattern.replace("-", "_")),
                os.path.join(base_dir, category, pattern),
            ]
            
            for src_path in possible_paths:
                if os.path.exists(src_path) and os.path.isdir(src_path):
                    dest_path = os.path.join(base_dir, category, pattern)
                    if src_path != dest_path:
                        print(f"Moving {src_path} -> {dest_path}")
                        shutil.move(src_path, dest_path)
                        moved_count += 1
                    break
    
    print(f"\nTotal patterns moved: {moved_count}")
    print(f"Total categories: {len(CATEGORIES)}")
    print(f"Total patterns: {sum(len(p) for p in CATEGORIES.values())}")

if __name__ == "__main__":
    restructure_design_patterns()