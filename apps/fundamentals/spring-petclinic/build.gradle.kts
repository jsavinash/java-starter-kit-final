// ============================================================================
// Spring PetClinic — Example Application
// ============================================================================
// Demonstrates Spring PetClinic concepts:
// - Classic reference application
// - Spring Boot best practices
// - Thymeleaf templating
// - Data access with JPA
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

description = "Spring PetClinic Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Thymeleaf for views
    implementation(libs.findLibrary("spring-boot-starter-thymeleaf").get())

    // Spring Data JPA
    implementation(libs.findLibrary("spring-boot-starter-data-jpa").get())

    // H2 Database
    implementation(libs.findLibrary("h2").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.petclinic.PetClinicApplication"
}
