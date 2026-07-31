// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.featureflags.actuator;

import com.javastarterkit.featureflags.model.FeatureFlag;
import com.javastarterkit.featureflags.repository.FeatureFlagRepository;
import java.util.Collection;
import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * {@link HealthIndicator} for feature flags, reporting flag counts to the
 * Actuator health endpoint.
 */
@Component
public class FeatureFlagHealthIndicator implements HealthIndicator {

    private final FeatureFlagRepository repository;

    public FeatureFlagHealthIndicator(FeatureFlagRepository repository) {
        this.repository = repository;
    }

    @Override
    public Health health() {
        Collection<FeatureFlag> flags = repository.findAll();
        if (flags.isEmpty()) {
            return Health.up()
                    .withDetail("message", "No feature flags configured")
                    .build();
        }
        long enabledCount = flags.stream().filter(FeatureFlag::isEnabled).count();
        return Health.up()
                .withDetail("total", flags.size())
                .withDetail("enabled", enabledCount)
                .withDetail("disabled", flags.size() - enabledCount)
                .build();
    }
}
