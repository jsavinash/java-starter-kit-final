// ============================================================================
// Spring Retry — Example Application
// ============================================================================
// Demonstrates Spring Retry concepts:
// - @Retryable annotation for automatic retries
// - @Recover for fallback execution
// - Backoff policies (exponential, random)
// - Retry template and listeners
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Retry Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Retry
    implementation(libs.findLibrary("spring-retry").get())

    // Spring AOP
    implementation(libs.findLibrary("spring-boot-starter-aop").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.retry.RetryApplication"
}
