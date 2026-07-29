// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.redis.repository;

import com.javastarterkit.redis.entity.CacheEntry;
import java.util.List;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@EnableRedisRepositories
public interface CacheEntryRepository extends CrudRepository<CacheEntry, String> {

    List<CacheEntry> findByValue(String value);
}
