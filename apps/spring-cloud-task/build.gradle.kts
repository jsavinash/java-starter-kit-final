// ============================================================================
// Spring Cloud Task — Example Application
// ============================================================================
// Demonstrates Spring Cloud Task concepts:
// - Short-lived microservices
// - Task lifecycle management
// - Task execution and monitoring
// - Integration with Spring Batch
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Cloud Task Example"

dependencies {
    // Spring Cloud Task
    implementation(libs.findLibrary("spring-cloud-starter-task").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.task.TaskApplication"
}