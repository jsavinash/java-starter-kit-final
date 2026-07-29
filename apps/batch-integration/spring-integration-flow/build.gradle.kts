// ============================================================================
// Spring Integration Flow — Example Application
// ============================================================================
// Demonstrates Spring Integration Flow concepts:
// - Fluent API for building integration flows
// - Message channels and endpoints
// - Transformers and routers
// - Integration flow DSL
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Integration Flow Example"

dependencies {
    // Spring Integration
    implementation(libs.findLibrary("spring-boot-starter-integration").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.integrationflow.IntegrationFlowApplication"
}
