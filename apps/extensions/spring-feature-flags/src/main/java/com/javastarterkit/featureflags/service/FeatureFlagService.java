// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.featureflags.service;

import com.javastarterkit.featureflags.model.FeatureFlag;
import com.javastarterkit.featureflags.model.RolloutStrategy;
import com.javastarterkit.featureflags.repository.FeatureFlagRepository;
import java.util.Collection;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Service for evaluating and managing feature flags.
 */
@Service
public class FeatureFlagService {

    private final FeatureFlagRepository repository;

    public FeatureFlagService(FeatureFlagRepository repository) {
        this.repository = repository;
    }

    /**
     * Check if a feature flag is enabled globally.
     *
     * @param flagName the feature flag name
     * @return {@code true} if enabled and rollout passes
     */
    public boolean isEnabled(String flagName) {
        return evaluate(flagName, null);
    }

    /**
     * Check if a feature flag is enabled for a specific user.
     *
     * @param flagName the feature flag name
     * @param userId the user identifier (email, UUID, etc.)
     * @return {@code true} if enabled for this user
     */
    public boolean isEnabledForUser(String flagName, String userId) {
        return evaluate(flagName, userId);
    }

    /**
     * Find a feature flag by name.
     *
     * @param name the flag name
     * @return an {@link Optional} containing the flag, or empty if not found
     */
    public Optional<FeatureFlag> findByName(String name) {
        return repository.findByName(name);
    }

    /**
     * Find all registered feature flags.
     *
     * @return collection of all flags
     */
    public Collection<FeatureFlag> findAll() {
        return repository.findAll();
    }

    /**
     * Save or update a feature flag.
     *
     * @param flag the flag to save
     * @return the saved flag
     */
    public FeatureFlag save(FeatureFlag flag) {
        return repository.save(flag);
    }

    /**
     * Delete a feature flag by name.
     *
     * @param name the flag name
     */
    public void deleteByName(String name) {
        repository.deleteByName(name);
    }

    private boolean evaluate(String flagName, String userId) {
        Optional<FeatureFlag> flagOpt = repository.findByName(flagName);
        if (flagOpt.isEmpty()) {
            return false;
        }
        FeatureFlag flag = flagOpt.get();
        if (!flag.isEnabled()) {
            return false;
        }
        return evaluateRollout(flag, userId);
    }

    private boolean evaluateRollout(FeatureFlag flag, String userId) {
        RolloutStrategy strategy = flag.getRolloutStrategy();
        if (strategy == null || strategy == RolloutStrategy.GLOBAL) {
            return true;
        }
        if (strategy == RolloutStrategy.PERCENTAGE) {
            return evaluatePercentageRollout(flag.getName(), userId, flag.getRolloutPercentage());
        }
        // USER_IDS and USER_ATTRIBUTES strategies would require additional context
        // and are intentionally left as extension points.
        return false;
    }

    private boolean evaluatePercentageRollout(String flagName, String userId, int percentage) {
        if (percentage <= 0) {
            return false;
        }
        if (percentage >= 100) {
            return true;
        }
        String identifier = (userId != null) ? userId : "anonymous";
        int hash = Math.abs((flagName + ":" + identifier).hashCode());
        int bucket = hash % 100;
        return bucket < percentage;
    }
}
