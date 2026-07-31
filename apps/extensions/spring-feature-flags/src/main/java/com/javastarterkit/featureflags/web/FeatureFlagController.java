// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.featureflags.web;

import com.javastarterkit.featureflags.model.FeatureFlag;
import com.javastarterkit.featureflags.service.FeatureFlagService;
import java.util.Collection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing feature flags.
 */
@RestController
@RequestMapping("/api/feature-flags")
public class FeatureFlagController {

    private final FeatureFlagService featureFlagService;

    public FeatureFlagController(FeatureFlagService featureFlagService) {
        this.featureFlagService = featureFlagService;
    }

    /**
     * List all feature flags.
     *
     * @return collection of all flags
     */
    @GetMapping
    public Collection<FeatureFlag> listFlags() {
        return featureFlagService.findAll();
    }

    /**
     * Get a single feature flag by name.
     *
     * @param name the flag name
     * @return the flag, or 404 if not found
     */
    @GetMapping("/{name}")
    public ResponseEntity<FeatureFlag> getFlag(@PathVariable String name) {
        return featureFlagService
                .findByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Check if a flag is enabled.
     *
     * @param name the flag name
     * @param userId optional user identifier for targeted rollout
     * @return a response containing the enabled state
     */
    @GetMapping("/{name}/enabled")
    public ResponseEntity<EnabledResponse> isEnabled(
            @PathVariable String name, @RequestParam(required = false) String userId) {
        boolean enabled = (userId != null)
                ? featureFlagService.isEnabledForUser(name, userId)
                : featureFlagService.isEnabled(name);
        return ResponseEntity.ok(new EnabledResponse(name, enabled));
    }

    /**
     * Create or update a feature flag.
     *
     * @param flag the flag to save
     * @return the saved flag
     */
    @PostMapping
    public ResponseEntity<FeatureFlag> saveFlag(@RequestBody FeatureFlag flag) {
        FeatureFlag saved = featureFlagService.save(flag);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * Delete a feature flag by name.
     *
     * @param name the flag name
     * @return 204 No Content on success
     */
    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteFlag(@PathVariable String name) {
        featureFlagService.deleteByName(name);
        return ResponseEntity.noContent().build();
    }

    /**
     * Response DTO for the enabled check endpoint.
     */
    public static class EnabledResponse {

        private final String name;

        private final boolean enabled;

        public EnabledResponse(String name, boolean enabled) {
            this.name = name;
            this.enabled = enabled;
        }

        public String getName() {
            return name;
        }

        public boolean isEnabled() {
            return enabled;
        }
    }
}
