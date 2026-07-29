// ============================================================================
// Spring Caching — Example Application
// ============================================================================
// Demonstrates Spring Cache abstraction concepts:
// - @Cacheable, @CacheEvict, @CachePut annotations
// - Cache configuration and providers
// - TTL and eviction policies
// - Multi-tier caching strategies
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Caching Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Cache starter
    implementation(libs.findLibrary("spring-boot-starter-cache").get())

    // Caffeine cache
    implementation(libs.findLibrary("caffeine").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.caching.CachingApplication"
}
