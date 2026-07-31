// ============================================================================
// Spring Cloud Function — Example Application
// ============================================================================
// Demonstrates Spring Cloud Function concepts:
// - Function, Consumer, Supplier beans
// - Function composition and routing
// - Reactive function support
// - Cloud-native function deployment
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

description = "Spring Cloud Function Example"

dependencies {
    // Cloud Function starter
    implementation(libs.findLibrary("spring-cloud-function-context").get())

    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.cloudfunction.CloudFunctionApplication"
}
