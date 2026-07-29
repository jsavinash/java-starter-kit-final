# Spring Data Redis — Key-Value Store Example

Demonstrates Spring Data Redis for key-value data access, caching, and pub/sub messaging.

## 🎯 Purpose

Shows how to use Redis as a data store, cache, and message broker with Spring Data Redis.

## 🧩 Key Dependencies

- `spring-boot-starter-web` — REST endpoints
- `spring-boot-starter-data-redis` — Spring Data Redis

## 🚀 How to Run

```bash
./gradlew :apps:data:spring-data-redis:bootRun
```

## 📚 Concepts Demonstrated

- **RedisTemplate** — Programmatic key-value operations
- **Redis Repositories** — `@RedisHash`, `CrudRepository` for Redis
- **@Cacheable** — Declarative caching with Redis backend
- **@CacheEvict** — Cache invalidation
- **@CachePut** — Cache update without eviction
- **Pub/Sub** — Redis message publishing and subscribing
- **Lettuce/Jedis** — Redis client connectors
- **Redis Serialization** — JSON and Java serialization
- **TTL Management** — Time-to-live for cached entries