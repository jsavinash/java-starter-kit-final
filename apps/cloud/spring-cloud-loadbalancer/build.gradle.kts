// ============================================================================
// Spring Cloud LoadBalancer — Example Application
// ============================================================================
// Demonstrates Spring Cloud LoadBalancer concepts:
// - Client-side load balancing
// - Service instance selection strategies
// - Reactive and non-reactive support
// - Custom load balancing rules
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

description = "Spring Cloud LoadBalancer Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // LoadBalancer starter
    implementation(libs.findLibrary("spring-cloud-starter-loadbalancer").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.loadbalancer.LoadBalancerApplication"
}
