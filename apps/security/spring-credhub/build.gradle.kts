// ============================================================================
// Spring CredHub — Example Application
// ============================================================================
// Demonstrates Spring CredHub concepts:
// - Cloud Foundry CredHub integration
// - Credential management and retrieval
// - Secret rotation
// - Credential references
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring CredHub Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring CredHub
    implementation(libs.findLibrary("spring-credhub").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.credhub.CredHubApplication"
}