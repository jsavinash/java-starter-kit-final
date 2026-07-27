// ============================================================================
// Web Application — Spring Boot Example
// ============================================================================
// Demonstrates how to use the convention plugins and platform BOM
// defined in the monorepo's composite builds.
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Example Spring Boot Web Application"

dependencies {
    // Web starter — version managed by the Spring Boot BOM from the platform
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Spring Boot test starter (includes JUnit Jupiter + Mockito from convention)
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

// Override the main class if the convention's guess is incorrect
tasks.named("bootJar") {
    // The convention plugin sets archiveFileName = "${project.name}.jar"
    // and the main class can be set via project property or explicitly:
    // mainClass.set("com.javastarterkit.webapp.WebApplication")
}

// Configure Spring Boot extension
springBoot {
    mainClass = "com.javastarterkit.webapp.WebApplication"
}
