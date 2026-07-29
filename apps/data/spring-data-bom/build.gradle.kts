// ============================================================================
// Spring Data BOM — Example Application
// ============================================================================
// Demonstrates Spring Data BOM concepts:
// - Bill of Materials for dependency management
// - Version alignment across Spring Data modules
// - Maven BOM import in Gradle
// - Consistent versioning strategy
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Data BOM Example"

dependencies {
    // Import Spring Data BOM for consistent versions
    implementation(platform(libs.findLibrary("spring-data-bom").get()))

    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Data JPA
    implementation(libs.findLibrary("spring-boot-starter-data-jpa").get())

    // H2 Database
    implementation(libs.findLibrary("h2").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.bom.BomApplication"
}
