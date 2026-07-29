// ============================================================================
// Spring AOT — Example Application
// ============================================================================
// Demonstrates Spring AOT (Ahead-of-Time) concepts:
// - Native image compilation
// - Build-time optimizations
// - Reflexion and resource processing
// - GraalVM integration
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring AOT Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring AOT
    implementation(libs.findLibrary("spring-aot").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.aot.AotApplication"
}
