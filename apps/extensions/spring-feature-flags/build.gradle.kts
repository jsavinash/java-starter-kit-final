// ============================================================================
// Spring Feature Flags — Example Application
// ============================================================================
// Demonstrates Spring Boot auto-configuration concepts:
// - Custom auto-configuration with @ConditionalOnProperty
// - @ConfigurationProperties for externalized configuration
// - Actuator endpoint for runtime flag management
// - Health indicator integration
// - Percentage-based rollout strategies
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<org.gradle.api.artifacts.VersionCatalogsExtension>().named("libs")

description = "Spring Feature Flags Example"

dependencies {
    // Web starter for REST endpoint demonstration
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Validation for request DTOs
    implementation(libs.findLibrary("spring-boot-starter-validation").get())

    // Actuator for health indicator support
    implementation(libs.findLibrary("spring-boot-starter-actuator").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.featureflags.FeatureFlagsApplication"
}
