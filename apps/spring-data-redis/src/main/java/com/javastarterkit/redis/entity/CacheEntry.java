package com.javastarterkit.redis.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("CacheEntry")
public class CacheEntry implements Serializable {

    @Id
    private String key;

    private String value;

    private long ttlSeconds;

    public CacheEntry() {
    }

    public CacheEntry(String key, String value, long ttlSeconds) {
        this.key = key;
        this.value = value;
        this.ttlSeconds = ttlSeconds;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getTtlSeconds() {
        return ttlSeconds;
    }

    public void setTtlSeconds(long ttlSeconds) {
        this.ttlSeconds = ttlSeconds;
    }
}