// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.featureflags.actuator;

import static org.assertj.core.api.Assertions.assertThat;

import com.javastarterkit.featureflags.model.FeatureFlag;
import com.javastarterkit.featureflags.model.RolloutStrategy;
import com.javastarterkit.featureflags.repository.InMemoryFeatureFlagRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.Status;

class FeatureFlagHealthIndicatorTest {

    @Test
    void healthReturnsUpWithMessageWhenNoFlags() {
        InMemoryFeatureFlagRepository repository = new InMemoryFeatureFlagRepository();
        FeatureFlagHealthIndicator indicator = new FeatureFlagHealthIndicator(repository);
        Health health = indicator.health();
        assertThat(health.getStatus()).isEqualTo(Status.UP);
        assertThat(health.getDetails()).containsKey("message");
    }

    @Test
    void healthReturnsUpWithCountsWhenFlagsPresent() {
        InMemoryFeatureFlagRepository repository = new InMemoryFeatureFlagRepository();
        repository.save(new FeatureFlag("flag1", true, RolloutStrategy.GLOBAL, 100));
        repository.save(new FeatureFlag("flag2", false, RolloutStrategy.GLOBAL, 100));
        FeatureFlagHealthIndicator indicator = new FeatureFlagHealthIndicator(repository);
        Health health = indicator.health();
        assertThat(health.getStatus()).isEqualTo(Status.UP);
        assertThat(health.getDetails()).containsEntry("total", 2);
        assertThat(health.getDetails()).containsEntry("enabled", 1L);
        assertThat(health.getDetails()).containsEntry("disabled", 1L);
    }
}
