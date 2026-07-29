// ============================================================================
// Spring Vault — Example Application
// ============================================================================
// Demonstrates Spring Vault concepts:
// - Secrets management with HashiCorp Vault
// - Key-Value secrets retrieval
// - VaultTemplate usage
// - @VaultPropertySource
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Vault Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Vault
    implementation(libs.findLibrary("spring-boot-starter-vault").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.vault.VaultApplication"
}
