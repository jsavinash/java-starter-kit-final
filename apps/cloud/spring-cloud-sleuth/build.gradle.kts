// ============================================================================
// Spring Cloud Sleuth — Example Application
// ============================================================================
// Demonstrates Spring Cloud Sleuth concepts:
// - Distributed tracing
// - Correlation IDs and span management
// - Integration with Zipkin/Jaeger
// - Log correlation
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Cloud Sleuth Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Cloud Sleuth
    implementation(libs.findLibrary("spring-cloud-starter-sleuth").get())

    // Zipkin integration
    implementation(libs.findLibrary("spring-cloud-sleuth-zipkin").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.sleuth.SleuthApplication"
}