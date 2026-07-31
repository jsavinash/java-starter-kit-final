// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.featureflags.web;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javastarterkit.featureflags.model.FeatureFlag;
import com.javastarterkit.featureflags.model.RolloutStrategy;
import com.javastarterkit.featureflags.repository.FeatureFlagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class FeatureFlagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FeatureFlagRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // Clear repository before each test to ensure isolation
        repository.findAll().forEach(flag -> repository.deleteByName(flag.getName()));
    }

    @Test
    void listFlagsReturnsEmptyArrayWhenNoFlags() throws Exception {
        mockMvc.perform(get("/api/feature-flags")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void saveFlagCreatesFlag() throws Exception {
        FeatureFlag flag = new FeatureFlag("test-flag", true, RolloutStrategy.GLOBAL, 100);
        mockMvc.perform(post("/api/feature-flags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(flag)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("test-flag")))
                .andExpect(jsonPath("$.enabled", is(true)));
    }

    @Test
    void getFlagReturns404WhenNotFound() throws Exception {
        mockMvc.perform(get("/api/feature-flags/nonexistent")).andExpect(status().isNotFound());
    }

    @Test
    void getFlagReturnsFlagWhenFound() throws Exception {
        repository.save(new FeatureFlag("found-flag", true, RolloutStrategy.GLOBAL, 100));
        mockMvc.perform(get("/api/feature-flags/found-flag"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("found-flag")));
    }

    @Test
    void isEnabledReturnsFalseForNonexistentFlag() throws Exception {
        mockMvc.perform(get("/api/feature-flags/nonexistent/enabled"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("nonexistent")))
                .andExpect(jsonPath("$.enabled", is(false)));
    }

    @Test
    void isEnabledReturnsTrueForEnabledGlobalFlag() throws Exception {
        repository.save(new FeatureFlag("active-flag", true, RolloutStrategy.GLOBAL, 100));
        mockMvc.perform(get("/api/feature-flags/active-flag/enabled"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("active-flag")))
                .andExpect(jsonPath("$.enabled", is(true)));
    }

    @Test
    void deleteFlagReturns204() throws Exception {
        repository.save(new FeatureFlag("delete-me", true, RolloutStrategy.GLOBAL, 100));
        mockMvc.perform(delete("/api/feature-flags/delete-me")).andExpect(status().isNoContent());
    }
}
