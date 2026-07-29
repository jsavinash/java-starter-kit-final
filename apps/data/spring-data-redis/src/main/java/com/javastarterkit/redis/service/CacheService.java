// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.redis.service;

import com.javastarterkit.redis.entity.CacheEntry;
import java.util.Collection;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public CacheService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void put(String key, String value, long ttlSeconds) {
        redisTemplate.opsForValue().set(key, value, ttlSeconds);
    }

    public Optional<String> get(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        return Optional.ofNullable((String) value);
    }

    public boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    public Collection<CacheEntry> getAllEntries() {
        return redisTemplate.opsForValue().multiGet(redisTemplate.keys("*"));
        // Note: This is simplified - in production use proper Redis operations
        return null;
    }
}
