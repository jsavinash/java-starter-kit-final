// ============================================================================
// Spring Data Redis — Example Application
// ============================================================================
// Demonstrates Spring Data Redis concepts:
// - Key-value data access with RedisTemplate
// - Redis repositories
// - Caching with @Cacheable
// - Pub/Sub messaging
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

description = "Spring Data Redis Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Data Redis
    implementation(libs.findLibrary("spring-boot-starter-data-redis").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.redis.RedisApplication"
}
