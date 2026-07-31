// ============================================================================
// Spring Plugin — Example Application
// ============================================================================
// Demonstrates Spring Plugin concepts:
// - Plugin registry and discovery
// - Plugin lifecycle management
// - Extensible application architecture
// - Plugin context isolation
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

description = "Spring Plugin Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Plugin
    implementation(libs.findLibrary("spring-plugin").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.plugin.PluginApplication"
}
