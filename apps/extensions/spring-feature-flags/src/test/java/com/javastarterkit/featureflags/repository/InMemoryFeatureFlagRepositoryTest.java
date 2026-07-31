// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.featureflags.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.javastarterkit.featureflags.model.FeatureFlag;
import com.javastarterkit.featureflags.model.RolloutStrategy;
import java.util.Collection;
import org.junit.jupiter.api.Test;

class InMemoryFeatureFlagRepositoryTest {

    private final InMemoryFeatureFlagRepository repository = new InMemoryFeatureFlagRepository();

    @Test
    void saveAndFindByNameReturnsFlag() {
        FeatureFlag flag = new FeatureFlag("test-flag", true, RolloutStrategy.GLOBAL, 100);
        repository.save(flag);
        assertThat(repository.findByName("test-flag")).contains(flag);
    }

    @Test
    void findByNameReturnsEmptyWhenNotFound() {
        assertThat(repository.findByName("nonexistent")).isEmpty();
    }

    @Test
    void deleteRemovesFlag() {
        repository.save(new FeatureFlag("flag1", true, RolloutStrategy.GLOBAL, 100));
        repository.deleteByName("flag1");
        assertThat(repository.findByName("flag1")).isEmpty();
    }

    @Test
    void existsByNameReturnsTrueWhenPresent() {
        repository.save(new FeatureFlag("flag1", true, RolloutStrategy.GLOBAL, 100));
        assertThat(repository.existsByName("flag1")).isTrue();
    }

    @Test
    void existsByNameReturnsFalseWhenAbsent() {
        assertThat(repository.existsByName("missing")).isFalse();
    }

    @Test
    void findAllReturnsAllFlags() {
        repository.save(new FeatureFlag("flag1", true, RolloutStrategy.GLOBAL, 100));
        repository.save(new FeatureFlag("flag2", false, RolloutStrategy.GLOBAL, 100));
        Collection<FeatureFlag> all = repository.findAll();
        assertThat(all).hasSize(2);
    }

    @Test
    void saveWithNullFlagThrowsException() {
        assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> repository.save(null));
    }

    @Test
    void findByNameWithBlankNameThrowsException() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> repository.findByName(""));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> repository.findByName(null));
    }

    @Test
    void findAllAfterDeleteReturnsOnlyRemaining() {
        repository.save(new FeatureFlag("flag1", true, RolloutStrategy.GLOBAL, 100));
        repository.save(new FeatureFlag("flag2", false, RolloutStrategy.GLOBAL, 100));
        repository.deleteByName("flag1");
        Collection<FeatureFlag> all = repository.findAll();
        assertThat(all).hasSize(1);
        assertThat(all.iterator().next().getName()).isEqualTo("flag2");
    }
}
