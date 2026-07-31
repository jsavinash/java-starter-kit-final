// ============================================================================
// Spring Cloud Netflix Ribbon — Example Application
// ============================================================================
// Demonstrates client-side load balancing concepts:
// - Client-side load balancing (modern replacement for Ribbon)
// - Service instance selection
// - Health-check based routing
// ============================================================================
// Note: Ribbon is end-of-life. This module now uses Spring Cloud LoadBalancer
// as the modern replacement.
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

description = "Spring Cloud LoadBalancer Example (formerly Ribbon)"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Cloud LoadBalancer (modern Ribbon replacement)
    implementation(libs.findLibrary("spring-cloud-starter-loadbalancer").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.ribbon.RibbonApplication"
}
