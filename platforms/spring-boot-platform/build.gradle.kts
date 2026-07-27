// ============================================================================
// Spring Boot Platform - Java Platform (BOM)
// ============================================================================
// This platform defines a BOM (Bill of Materials) for Spring Boot and its
// ecosystem dependencies. It is consumed as a composite build by the root
// project, ensuring all subprojects use consistent Spring Boot versions.
// ============================================================================

plugins {
    `java-platform`
    `maven-publish`
}

// Consume the version catalog from the root project
// The root project's libs.versions.toml is automatically available
// when this is included as a composite build
val libs = the<VersionCatalogsExtension>().named("libs")

javaPlatform {
    allowDependencies()
}

dependencies {
    // Import the Spring Boot BOM as a platform dependency
    // This pins all Spring Boot managed dependencies
    api(platform(springBootBom(libs)))

    // Pin additional Spring ecosystem dependencies not covered by Spring Boot BOM
    constraints {
        // Spring Cloud (if needed in future)
        // api("org.springframework.cloud:spring-cloud-dependencies:${libs.findVersion("spring-cloud").get().displayName}")

        // Example: pin a specific version of a library used alongside Spring Boot
        api(libs.findLibrary("jackson-core").get())
        api(libs.findLibrary("jackson-databind").get())
        api(libs.findLibrary("logback-classic").get())
        api(libs.findLibrary("slf4j-api").get())
        api(libs.findLibrary("assertj-core").get())
        api(libs.findLibrary("mockito-core").get())
        api(libs.findLibrary("mockito-junit-jupiter").get())
        api(libs.findLibrary("junit-jupiter-api").get())
        api(libs.findLibrary("junit-jupiter-engine").get())
        api(libs.findLibrary("junit-jupiter-params").get())
        api(libs.findLibrary("junit-platform-launcher").get())
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJavaPlatform") {
            from(components["javaPlatform"])
            groupId = rootProject.group.toString()
            artifactId = "spring-boot-platform"
            version = rootProject.version.toString()
        }
    }
}

// Helper function to resolve the Spring Boot BOM coordinates from the version catalog
fun springBootBom(libs: VersionCatalog): String {
    val springBootVersion = libs.findVersion("spring-boot").get().displayName
    return "org.springframework.boot:spring-boot-dependencies:$springBootVersion"
}