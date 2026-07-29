// ============================================================================
// Spring Rewrite — Example Application
// ============================================================================
// Demonstrates Spring Rewrite concepts:
// - Application refactoring tooling
// - Automatic code migrations
// - Dependency upgrades
// - Code modernization patterns
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Rewrite Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Rewrite
    implementation(libs.findLibrary("spring-rewrite").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.rewrite.RewriteApplication"
}