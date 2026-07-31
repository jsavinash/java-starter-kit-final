// ============================================================================
// Spring AOT — Example Application
// ============================================================================
// Demonstrates Spring AOT (Ahead-of-Time) concepts:
// - Native image compilation via Spring Boot's built-in AOT engine
// - Build-time optimizations (no experimental spring-aot library needed)
// - Reflection and resource processing for GraalVM native images
// - Spring Boot 4.x includes AOT engine natively
// ============================================================================
// NOTE: The experimental spring-aot library has been integrated into
// Spring Boot itself. AOT processing is now built-in and activated
// automatically when building native images with:
//   ./gradlew bootBuildImage
// or when using the Spring Boot AOT Maven/Gradle plugin.
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

description = "Spring AOT Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring AOT is built into Spring Boot 4.x natively.
    // No separate spring-aot dependency needed.
    // AOT processing is activated via:
    //   ./gradlew bootBuildImage (for GraalVM native images)
    // or by applying the org.graalvm.buildtools.native plugin.

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.aot.AotApplication"
}
