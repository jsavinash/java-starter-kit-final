// ============================================================================
// Spring WebFlux — Example Application
// ============================================================================
// Demonstrates Spring WebFlux concepts:
// - Reactive programming with Project Reactor
// - Flux and Mono types
// - Reactive REST endpoints
// - WebClient usage
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

description = "Spring WebFlux Example"

dependencies {
    // Spring WebFlux (reactive web)
    implementation(libs.findLibrary("spring-boot-starter-webflux").get())

    // Reactive MongoDB
    implementation(libs.findLibrary("spring-boot-starter-data-mongodb-reactive").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.webflux.WebFluxApplication"
}
