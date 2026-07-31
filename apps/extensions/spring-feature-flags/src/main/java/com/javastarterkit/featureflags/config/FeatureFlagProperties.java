// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.featureflags.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for feature flags.
 */
@ConfigurationProperties(prefix = "app.feature-flags")
public class FeatureFlagProperties {

    /**
     * Whether feature flag management is enabled.
     */
    private boolean enabled = true;

    /**
     * Default feature flags to register at startup.
     */
    private Map<String, FlagDefinition> defaults = new HashMap<>();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Map<String, FlagDefinition> getDefaults() {
        return defaults;
    }

    public void setDefaults(Map<String, FlagDefinition> defaults) {
        this.defaults = defaults;
    }

    /**
     * Definition for a single feature flag.
     */
    public static class FlagDefinition {

        /**
         * Whether the flag is enabled.
         */
        private boolean enabled = false;

        /**
         * Rollout percentage (0-100).
         */
        private int rolloutPercentage = 100;

        /**
         * Rollout strategy.
         */
        private String strategy = "GLOBAL";

        /**
         * Human-readable description.
         */
        private String description = "";

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public int getRolloutPercentage() {
            return rolloutPercentage;
        }

        public void setRolloutPercentage(int rolloutPercentage) {
            this.rolloutPercentage = rolloutPercentage;
        }

        public String getStrategy() {
            return strategy;
        }

        public void setStrategy(String strategy) {
            this.strategy = strategy;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
