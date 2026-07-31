// ============================================================================
// Spring Modulith — Example Application
// ============================================================================
// Demonstrates Spring Modulith concepts:
// - Modular application architecture
// - Module boundaries and visibility
// - Event-driven module communication
// - Architectural verification
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

description = "Spring Modulith Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Modulith
    implementation(libs.findLibrary("spring-modulith-core").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.modulith.ModulithApplication"
}
