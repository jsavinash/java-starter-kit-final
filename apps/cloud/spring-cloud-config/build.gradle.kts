// ============================================================================
// Spring Cloud Config — Example Application
// ============================================================================
// Demonstrates Spring Cloud Config concepts:
// - Externalized configuration management
// - Git-backed configuration repository
// - Config Server and Config Client
// - Environment-specific configuration
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Cloud Config Example"

dependencies {
    // Web starter (config server requires web)
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Note: spring-cloud-config-server requires Spring Cloud BOM
    // Add when Spring Cloud dependencies are configured

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.configserver.ConfigServerApplication"
}