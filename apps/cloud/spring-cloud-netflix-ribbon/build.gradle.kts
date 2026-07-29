// ============================================================================
// Spring Cloud Netflix Ribbon — Example Application
// ============================================================================
// Demonstrates Spring Cloud Netflix Ribbon concepts:
// - Client-side load balancing
// - Service instance selection
// - Retry and failover
// - Integration with Feign
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Cloud Netflix Ribbon Example"

dependencies {
    // Spring Cloud OpenFeign (includes Ribbon)
    implementation(libs.findLibrary("spring-cloud-starter-openfeign").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.ribbon.RibbonApplication"
}