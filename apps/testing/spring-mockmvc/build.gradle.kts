// ============================================================================
// Spring MockMvc — Example Application
// ============================================================================
// Demonstrates Spring MockMvc testing concepts:
// - @WebMvcTest for controller layer testing
// - Request/response verification
// - JSON serialization testing
// - Security context mocking
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

description = "Spring MockMvc Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.mockmvc.MockMvcApplication"
}
