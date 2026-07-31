// ============================================================================
// Spring Boot DevTools — Example Application
// ============================================================================
// Demonstrates Spring Boot DevTools concepts:
// - Automatic restart on classpath changes
// - LiveReload integration
// - Development-time optimizations
// - Properties defaults for development
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

description = "Spring Boot DevTools Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Boot DevTools (optional)
    developmentOnly(libs.findLibrary("spring-boot-devtools").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.devtools.DevtoolsApplication"
}
