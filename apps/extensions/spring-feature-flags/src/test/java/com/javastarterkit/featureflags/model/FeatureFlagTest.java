// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.featureflags.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.Test;

class FeatureFlagTest {

    @Test
    void createWithNameInitializesDefaults() {
        FeatureFlag flag = new FeatureFlag("my-flag");
        assertThat(flag.getName()).isEqualTo("my-flag");
        assertThat(flag.isEnabled()).isFalse();
        assertThat(flag.getRolloutPercentage()).isEqualTo(100);
        assertThat(flag.getRolloutStrategy()).isEqualTo(RolloutStrategy.GLOBAL);
        assertThat(flag.getCreatedAt()).isNotNull();
        assertThat(flag.getUpdatedAt()).isNotNull();
    }

    @Test
    void createWithParametersSetsAllFields() {
        FeatureFlag flag = new FeatureFlag("flag", true, RolloutStrategy.PERCENTAGE, 50);
        assertThat(flag.isEnabled()).isTrue();
        assertThat(flag.getRolloutStrategy()).isEqualTo(RolloutStrategy.PERCENTAGE);
        assertThat(flag.getRolloutPercentage()).isEqualTo(50);
    }

    @Test
    void createWithBlankNameThrowsException() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new FeatureFlag(""))
                .withMessage("Feature flag name must not be blank");
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new FeatureFlag(null))
                .withMessage("Feature flag name must not be blank");
    }

    @Test
    void setRolloutPercentageValidatesRange() {
        FeatureFlag flag = new FeatureFlag("flag");
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> flag.setRolloutPercentage(-1))
                .withMessage("Rollout percentage must be between 0 and 100");
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> flag.setRolloutPercentage(101))
                .withMessage("Rollout percentage must be between 0 and 100");
        flag.setRolloutPercentage(0);
        assertThat(flag.getRolloutPercentage()).isZero();
        flag.setRolloutPercentage(100);
        assertThat(flag.getRolloutPercentage()).isEqualTo(100);
    }

    @Test
    void equalsIsBasedOnName() {
        FeatureFlag flag1 = new FeatureFlag("flag", true, RolloutStrategy.GLOBAL, 100);
        FeatureFlag flag2 = new FeatureFlag("flag", false, RolloutStrategy.PERCENTAGE, 50);
        assertThat(flag1).isEqualTo(flag2);
        assertThat(flag1.hashCode()).isEqualTo(flag2.hashCode());
    }

    @Test
    void toStringContainsAllFields() {
        FeatureFlag flag = new FeatureFlag("my-flag", true, RolloutStrategy.PERCENTAGE, 75);
        String toString = flag.toString();
        assertThat(toString).contains("my-flag");
        assertThat(toString).contains("true");
        assertThat(toString).contains("PERCENTAGE");
        assertThat(toString).contains("75");
    }
}
