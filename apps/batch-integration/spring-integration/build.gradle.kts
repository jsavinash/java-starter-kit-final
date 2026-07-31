// ============================================================================
// Spring Integration — Example Application
// ============================================================================
// Demonstrates Spring Integration concepts:
// - Message channels and endpoints
// - Message transformation
// - Service Activators
// - Gateway pattern
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

description = "Spring Integration Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Integration
    implementation(libs.findLibrary("spring-boot-starter-integration").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.integration.IntegrationApplication"
}
