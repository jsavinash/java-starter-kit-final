// ============================================================================
// Spring Rewrite — Example Application
// ============================================================================
// Demonstrates application modernization concepts:
// - Migration template for legacy applications
// - Java 25 language features
// - Spring Boot 4.0 best practices
// - Code modernization patterns
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")


description = "Spring Rewrite Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.rewrite.RewriteApplication"
}