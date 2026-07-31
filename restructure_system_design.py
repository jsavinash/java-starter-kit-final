#!/usr/bin/env python3
"""
Restructure design-patterns folder to match user's requested structure:
1. System Design Theory (41 topics)
2. System Design Pattern (multiple subcategories)
"""
import os
import shutil

# Define the new structure based on user's requirements
NEW_STRUCTURE = {
    "system-design-theory": [
        "ip", "osi-model", "tcp-udp", "dns", "load-balancing", "clustering",
        "caching", "cdn", "proxy", "availability", "scalability", "storage",
        "databases-dbms", "sql-databases", "nosql-databases", "sql-vs-nosql",
        "database-replication", "indexes", "normalization-denormalization",
        "acid-base", "cap-theorem", "pacelc-theorem", "transactions",
        "distributed-transactions", "sharding", "consistent-hashing",
        "database-federation", "n-tier-architecture", "message-brokers",
        "message-queues", "publish-subscribe", "enterprise-service-bus",
        "monoliths-microservices", "event-driven-architecture",
        "event-sourcing", "cqrs", "api-gateway", "rest-graphql-grpc",
        "long-polling-websockets-sse", "geohashing-quadtrees",
        "circuit-breaker", "rate-limiting", "service-discovery",
        "sla-slo-sli", "disaster-recovery", "vms-containers",
        "oauth2-oidc", "sso", "ssl-tls-mtls", "consistency-patterns",
        "performance-throughput", "availability-consistency"
    ],
    "system-design-pattern": {
        "solid-principles": [
            "single-responsibility-principle", "liskov-substitution-principle",
            "dependency-inversion-principle", "interface-segregation-principle",
            "open-close-principle"
        ],
        "structural": [
            "adapter", "bridge", "business-delegate", "component",
            "composite-entity", "composite-view", "composite",
            "data-access-object", "data-transfer-object", "converter",
            "curiously-recurring-template-pattern", "decorator",
            "domain-model", "dynamic-proxy", "extension-objects",
            "facade", "flyweight", "marker-interface", "parameter-object",
            "private-class-data", "proxy", "role-object", "separated-interface",
            "servant", "service-locator", "spatial-partition", "special-case",
            "strangler", "twin", "value-object", "virtual-proxy", "sidecar"
        ],
        "creational": [
            "abstract-factory", "builder", "dependency-injection",
            "factory-kit", "factory-method", "factory", "monostate",
            "multiton", "object-pool", "prototype", "registry",
            "singleton", "step-builder", "type-object"
        ],
        "concurrency": [
            "active-object", "async-method-invocation", "balking",
            "double-checked-locking", "event-based-asynchronous",
            "event-queue", "fan-out-fan-in", "guarded-suspension",
            "half-sync-half-async", "leader-election", "leader-followers",
            "lockable-object", "master-worker", "monitor", "poison-pill",
            "producer-consumer", "promise", "reactor"
        ],
        "behavioral": [
            "acyclic-visitor", "bytecode", "chain-of-responsibility",
            "client-session", "collecting-parameter", "command",
            "commander", "context-object", "data-mapper", "delegation",
            "dirty-flag", "double-buffer", "double-dispatch",
            "execute-around", "feature-toggle", "filterer", "fluent-interface",
            "game-loop", "health-check", "identity-map", "interpreter",
            "iterator", "mediator", "notification", "memento", "mute-idiom",
            "null-object", "observer", "partial-response", "pipeline",
            "property", "specification", "state", "strategy",
            "subclass-sandbox", "template-method", "update-method", "visitor"
        ],
        "integration": [
            "ambassador", "anti-corruption-layer", "gateway"
        ],
        "microservices": [
            "api-gateway", "service-discovery", "service-registry",
            "config-server", "log-aggregation", "database-per-service",
            "service-mesh", "observability", "health-monitoring",
            "monitoring", "master-service-decomposition"
        ],
        "testing": [
            "arrange-act-assert", "object-mother", "page-object"
        ],
        "performance-optimization": [
            "caching", "data-locality", "lazy-loading"
        ],
        "functional": [
            "callback", "collection-pipeline", "combinator", "currying",
            "function-composition", "monad", "trampoline"
        ],
        "resilience": [
            "circuit-breaker", "queue-based-load-leveling", "retry",
            "saga", "tolerant-reader", "rate-limiting", "bulkheads",
            "fallbacks", "timeouts", "graceful-degradation"
        ],
        "architectural": [
            "cqrs", "event-driven-architecture", "event-sourcing",
            "flux", "front-controller", "hexagonal-architecture",
            "intercepting-filter", "layered-architecture",
            "microservices-aggregator", "model-view-controller",
            "model-view-intent", "model-view-presenter",
            "model-view-viewmodel", "naked-objects", "page-controller",
            "presentation-model", "service-layer", "service-to-worker",
            "backend-for-frontend", "composable-architecture"
        ],
        "messaging": [
            "data-bus", "event-aggregator"
        ],
        "data-access": [
            "metadata-mapping", "optimistic-offline-lock", "repository",
            "serialized-entity", "serialized-lob", "sharding",
            "single-table-inheritance", "table-module", "transaction-script",
            "unit-of-work", "version-number"
        ],
        "resource-management": [
            "resource-acquisition-is-initialization", "server-session",
            "throttling"
        ]
    }
}

def create_new_structure():
    base_dir = "design-patterns"
    
    # Create new directory structure
    print("Creating new folder structure...")
    
    # Create main category folders
    os.makedirs(os.path.join(base_dir, "system-design-theory"), exist_ok=True)
    os.makedirs(os.path.join(base_dir, "system-design-pattern"), exist_ok=True)
    
    # Create subcategory folders under system-design-pattern
    for subcategory in NEW_STRUCTURE["system-design-pattern"].keys():
        os.makedirs(os.path.join(base_dir, "system-design-pattern", subcategory), exist_ok=True)
    
    # Create topic folders under system-design-theory
    for topic in NEW_STRUCTURE["system-design-theory"]:
        os.makedirs(os.path.join(base_dir, "system-design-theory", topic), exist_ok=True)
    
    # Create topic folders under each subcategory
    for subcategory, topics in NEW_STRUCTURE["system-design-pattern"].items():
        for topic in topics:
            os.makedirs(os.path.join(base_dir, "system-design-pattern", subcategory, topic), exist_ok=True)
    
    print("Folder structure created successfully!")
    
    # Count totals
    theory_count = len(NEW_STRUCTURE["system-design-theory"])
    pattern_count = sum(len(topics) for topics in NEW_STRUCTURE["system-design-pattern"].values())
    
    print(f"\nSystem Design Theory topics: {theory_count}")
    print(f"System Design Pattern topics: {pattern_count}")
    print(f"Total topics: {theory_count + pattern_count}")

if __name__ == "__main__":
    create_new_structure()