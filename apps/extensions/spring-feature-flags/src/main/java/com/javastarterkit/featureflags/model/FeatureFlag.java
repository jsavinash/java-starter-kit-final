// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.featureflags.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a feature flag with rollout capabilities.
 */
public class FeatureFlag {

    private final String name;

    private boolean enabled;

    private RolloutStrategy rolloutStrategy;

    private int rolloutPercentage;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    /**
     * Create a new {@link FeatureFlag} with default settings.
     *
     * @param name the feature flag name (must not be blank)
     */
    public FeatureFlag(String name) {
        this(name, false, RolloutStrategy.GLOBAL, 100);
    }

    /**
     * Create a new {@link FeatureFlag}.
     *
     * @param name the feature flag name (must not be blank)
     * @param enabled whether the flag is enabled
     * @param rolloutStrategy the rollout strategy
     * @param rolloutPercentage the percentage of users to include (0-100)
     */
    public FeatureFlag(String name, boolean enabled, RolloutStrategy rolloutStrategy, int rolloutPercentage) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Feature flag name must not be blank");
        }
        this.name = name;
        this.enabled = enabled;
        this.rolloutStrategy = rolloutStrategy;
        this.rolloutPercentage = validatePercentage(rolloutPercentage);
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    private static int validatePercentage(int percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("Rollout percentage must be between 0 and 100");
        }
        return percentage;
    }

    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        this.updatedAt = LocalDateTime.now();
    }

    public RolloutStrategy getRolloutStrategy() {
        return rolloutStrategy;
    }

    public void setRolloutStrategy(RolloutStrategy rolloutStrategy) {
        this.rolloutStrategy = rolloutStrategy;
        this.updatedAt = LocalDateTime.now();
    }

    public int getRolloutPercentage() {
        return rolloutPercentage;
    }

    public void setRolloutPercentage(int rolloutPercentage) {
        this.rolloutPercentage = validatePercentage(rolloutPercentage);
        this.updatedAt = LocalDateTime.now();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FeatureFlag that = (FeatureFlag) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "FeatureFlag{"
                + "name='" + name + '\''
                + ", enabled=" + enabled
                + ", rolloutStrategy=" + rolloutStrategy
                + ", rolloutPercentage=" + rolloutPercentage
                + '}';
    }
}
