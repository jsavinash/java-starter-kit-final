package com.javastarterkit.redis.repository;

import java.util.List;

import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.javastarterkit.redis.entity.CacheEntry;

@Repository
@EnableRedisRepositories
public interface CacheEntryRepository extends CrudRepository<CacheEntry, String> {

    List<CacheEntry> findByValue(String value);
}