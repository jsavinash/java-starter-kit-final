// ============================================================================
// Spring Cloud OpenFeign — Example Application
// ============================================================================
// Demonstrates Spring Cloud OpenFeign concepts:
// - Declarative HTTP client
// - Feign client interfaces
// - Load balancing with Ribbon
// - Error handling and fallbacks
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Cloud OpenFeign Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Cloud OpenFeign
    implementation(libs.findLibrary("spring-cloud-starter-openfeign").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.openfeign.OpenFeignApplication"
}