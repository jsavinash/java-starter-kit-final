// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.featureflags.repository;

import com.javastarterkit.featureflags.model.FeatureFlag;
import java.util.Collection;
import java.util.Optional;

/**
 * Repository abstraction for feature flag management.
 */
public interface FeatureFlagRepository {

    /**
     * Find a feature flag by name.
     *
     * @param name the flag name (must not be blank)
     * @return an {@link Optional} containing the flag, or empty if not found
     */
    Optional<FeatureFlag> findByName(String name);

    /**
     * Find all registered feature flags.
     *
     * @return collection of all flags (never {@code null})
     */
    Collection<FeatureFlag> findAll();

    /**
     * Save or update a feature flag.
     *
     * @param flag the flag to save (must not be {@code null})
     * @return the saved flag
     */
    FeatureFlag save(FeatureFlag flag);

    /**
     * Delete a feature flag by name.
     *
     * @param name the flag name (must not be blank)
     */
    void deleteByName(String name);

    /**
     * Check if a feature flag exists.
     *
     * @param name the flag name (must not be blank)
     * @return {@code true} if the flag exists
     */
    boolean existsByName(String name);
}
