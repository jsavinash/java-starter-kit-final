// ============================================================================
// Spring Cloud Gateway — Example Application
// ============================================================================
// Demonstrates Spring Cloud Gateway concepts:
// - API Gateway pattern
// - Route configuration
// - Filters and predicates
// - Load balancing
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

description = "Spring Cloud Gateway Example"

dependencies {
    // Spring Cloud Gateway
    implementation(libs.findLibrary("spring-cloud-starter-gateway").get())

    // Service Discovery (Eureka)
    implementation(libs.findLibrary("spring-cloud-starter-netflix-eureka-client").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.gateway.GatewayApplication"
}
