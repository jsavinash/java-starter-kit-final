// ============================================================================
// Spring Batch — Example Application
// ============================================================================
// Demonstrates Spring Batch concepts:
// - Batch job configuration
// - Chunk-oriented processing
// - ItemReader, ItemProcessor, ItemWriter
// - JobRepository and JobLauncher
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Batch Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Batch
    implementation(libs.findLibrary("spring-boot-starter-batch").get())

    // H2 Database for batch metadata
    implementation(libs.findLibrary("h2").get())

    // JDBC for batch operations
    implementation(libs.findLibrary("spring-boot-starter-jdbc").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.batch.BatchApplication"
}