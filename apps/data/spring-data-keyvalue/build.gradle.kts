// ============================================================================
// Spring Data KeyValue — Example Application
// ============================================================================
// Demonstrates Spring Data KeyValue concepts:
// - Key-value store abstraction
// - Simple map-based persistence
// - KeyValue operations
// - Embedded map support
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

description = "Spring Data KeyValue Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Data KeyValue
    implementation(libs.findLibrary("spring-data-keyvalue").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.keyvalue.KeyValueApplication"
}
