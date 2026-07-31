// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.featureflags.repository;

import com.javastarterkit.featureflags.model.FeatureFlag;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

/**
 * In-memory repository for feature flags. Suitable for development and testing.
 */
@Repository
public class InMemoryFeatureFlagRepository implements FeatureFlagRepository {

    private final ConcurrentHashMap<String, FeatureFlag> flags = new ConcurrentHashMap<>();

    @Override
    public Optional<FeatureFlag> findByName(String name) {
        validateName(name);
        return Optional.ofNullable(this.flags.get(name));
    }

    @Override
    public Collection<FeatureFlag> findAll() {
        return Collections.unmodifiableCollection(this.flags.values());
    }

    @Override
    public FeatureFlag save(FeatureFlag flag) {
        Objects.requireNonNull(flag, "Feature flag must not be null");
        validateName(flag.getName());
        this.flags.put(flag.getName(), flag);
        return flag;
    }

    @Override
    public void deleteByName(String name) {
        validateName(name);
        this.flags.remove(name);
    }

    @Override
    public boolean existsByName(String name) {
        validateName(name);
        return this.flags.containsKey(name);
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Feature flag name must not be blank");
        }
    }
}
