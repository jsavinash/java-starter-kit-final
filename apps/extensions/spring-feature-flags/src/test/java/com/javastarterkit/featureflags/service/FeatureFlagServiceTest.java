// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.featureflags.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.javastarterkit.featureflags.model.FeatureFlag;
import com.javastarterkit.featureflags.model.RolloutStrategy;
import com.javastarterkit.featureflags.repository.InMemoryFeatureFlagRepository;
import org.junit.jupiter.api.Test;

class FeatureFlagServiceTest {

    private final InMemoryFeatureFlagRepository repository = new InMemoryFeatureFlagRepository();

    private final FeatureFlagService service = new FeatureFlagService(repository);

    @Test
    void isEnabledWhenFlagDoesNotExistReturnsFalse() {
        assertThat(service.isEnabled("nonexistent")).isFalse();
    }

    @Test
    void isEnabledWhenFlagDisabledReturnsFalse() {
        repository.save(new FeatureFlag("flag1", false, RolloutStrategy.GLOBAL, 100));
        assertThat(service.isEnabled("flag1")).isFalse();
    }

    @Test
    void isEnabledWhenFlagEnabledAndGlobalReturnsTrue() {
        repository.save(new FeatureFlag("flag1", true, RolloutStrategy.GLOBAL, 100));
        assertThat(service.isEnabled("flag1")).isTrue();
    }

    @Test
    void isEnabledForUserWithPercentageRolloutReturnsConsistentResult() {
        repository.save(new FeatureFlag("flag1", true, RolloutStrategy.PERCENTAGE, 50));
        String userId = "user@example.com";
        boolean firstCall = service.isEnabledForUser("flag1", userId);
        boolean secondCall = service.isEnabledForUser("flag1", userId);
        assertThat(firstCall).isEqualTo(secondCall);
    }

    @Test
    void isEnabledForUserWithPercentageZeroReturnsFalse() {
        repository.save(new FeatureFlag("flag1", true, RolloutStrategy.PERCENTAGE, 0));
        assertThat(service.isEnabledForUser("flag1", "any-user")).isFalse();
    }

    @Test
    void isEnabledForUserWithPercentage100ReturnsTrue() {
        repository.save(new FeatureFlag("flag1", true, RolloutStrategy.PERCENTAGE, 100));
        assertThat(service.isEnabledForUser("flag1", "any-user")).isTrue();
    }

    @Test
    void isEnabledForUserWithUserIdStrategyReturnsFalseWhenNotWhitelisted() {
        repository.save(new FeatureFlag("flag1", true, RolloutStrategy.USER_IDS, 100));
        assertThat(service.isEnabledForUser("flag1", "unknown-user")).isFalse();
    }

    @Test
    void savePersistsFlagAndFindByNameReturnsIt() {
        FeatureFlag flag = new FeatureFlag("new-flag", true, RolloutStrategy.GLOBAL, 100);
        service.save(flag);
        assertThat(service.findByName("new-flag")).contains(flag);
    }

    @Test
    void deleteByNameRemovesFlag() {
        repository.save(new FeatureFlag("flag1", true, RolloutStrategy.GLOBAL, 100));
        service.deleteByName("flag1");
        assertThat(service.findByName("flag1")).isEmpty();
    }
}
