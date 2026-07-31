// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.featureflags.model;

/**
 * Enumeration of rollout strategies for feature flags.
 */
public enum RolloutStrategy {

    /**
     * Flag is either fully on or off for all users (binary flag).
     */
    GLOBAL,

    /**
     * Flag applies to a percentage of users based on consistent hashing.
     */
    PERCENTAGE,

    /**
     * Flag applies to specific user identifiers (whitelist).
     */
    USER_IDS,

    /**
     * Flag applies to specific user attributes (e.g., role, region).
     */
    USER_ATTRIBUTES
}
