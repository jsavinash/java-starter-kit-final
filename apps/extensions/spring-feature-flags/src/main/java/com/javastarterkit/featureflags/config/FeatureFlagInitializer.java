// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.featureflags.config;

import com.javastarterkit.featureflags.model.FeatureFlag;
import com.javastarterkit.featureflags.model.RolloutStrategy;
import com.javastarterkit.featureflags.repository.FeatureFlagRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Initializes default feature flags from configuration properties on startup.
 */
@Configuration
@EnableConfigurationProperties(FeatureFlagProperties.class)
public class FeatureFlagInitializer {

    /**
     * Create a {@link CommandLineRunner} that seeds default flags from configuration.
     *
     * @param properties the feature flag properties
     * @param repository the feature flag repository
     * @return a {@link CommandLineRunner} instance
     */
    @Bean
    public CommandLineRunner initializeFeatureFlags(
            FeatureFlagProperties properties, FeatureFlagRepository repository) {
        return args -> {
            if (properties.getDefaults() == null) {
                return;
            }
            properties.getDefaults().forEach((name, definition) -> {
                FeatureFlag flag = new FeatureFlag(name);
                flag.setEnabled(definition.isEnabled());
                flag.setDescription(definition.getDescription());
                flag.setRolloutPercentage(definition.getRolloutPercentage());
                flag.setRolloutStrategy(parseStrategy(definition.getStrategy()));
                repository.save(flag);
            });
        };
    }

    private RolloutStrategy parseStrategy(String strategy) {
        if (strategy == null || strategy.isBlank()) {
            return RolloutStrategy.GLOBAL;
        }
        try {
            return RolloutStrategy.valueOf(strategy.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return RolloutStrategy.GLOBAL;
        }
    }
}
