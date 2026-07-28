// ============================================================================
// Spring Web Services — Example Application
// ============================================================================
// Demonstrates Spring Web Services concepts:
// - SOAP web services
// - WSDL contract
// - Endpoint configuration
// - XML marshalling
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Web Services Example"

dependencies {
    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Spring Web Services
    implementation(libs.findLibrary("spring-ws-core").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.ws.WebServicesApplication"
}