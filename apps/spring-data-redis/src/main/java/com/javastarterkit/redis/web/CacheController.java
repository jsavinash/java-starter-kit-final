package com.javastarterkit.redis.web;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javastarterkit.redis.service.CacheService;

@RestController
@RequestMapping("/api/cache")
public class CacheController {

    private final CacheService cacheService;

    public CacheController(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @PostMapping("/{key}")
    public ResponseEntity<Void> put(
            @PathVariable String key,
            @RequestBody Map<String, String> request) {
        long ttl = request.containsKey("ttl") ? Long.parseLong(request.get("ttl")) : 3600;
        cacheService.put(key, request.get("value"), ttl);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{key}")
    public ResponseEntity<String> get(@PathVariable String key) {
        return cacheService.get(key)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{key}")
    public ResponseEntity<Void> delete(@PathVariable String key) {
        cacheService.delete(key);
        return ResponseEntity.noContent().build();
    }
}