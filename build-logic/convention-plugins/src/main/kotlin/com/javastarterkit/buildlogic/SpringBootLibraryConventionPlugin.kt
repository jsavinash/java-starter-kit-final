package com.javastarterkit.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension

/**
 * Spring Boot Library Convention Plugin
 *
 * Configures a Spring Boot library module (non-executable JAR) with:
 * - Spring Boot dependency management (without bootJar task)
 * - Spring Boot platform BOM from the composite build
 * - Testing and code quality conventions
 *
 * Use this plugin for internal library modules that provide Spring Boot
 * auto-configuration, shared services, or domain logic.
 */
class SpringBootLibraryConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        // Apply foundational plugins
        project.plugins.apply("com.javastarterkit.buildlogic.java-base")
        project.plugins.apply("com.javastarterkit.buildlogic.testing")
        project.plugins.apply("com.javastarterkit.buildlogic.code-quality")

        // Apply Spring dependency management only (not the boot plugin for libraries)
        project.plugins.apply("io.spring.dependency-management")

        project.configureDependencies()
    }

    private fun Project.configureDependencies() {
        val deps = dependencies

        // Import the Spring Boot platform BOM from the composite build as API (transitive)
        deps.add("api", deps.platform("com.javastarterkit:spring-boot-platform:1.0.0-SNAPSHOT"))

        // Core Spring Boot dependencies
        deps.add("implementation", "org.springframework.boot:spring-boot-starter")
        deps.add("implementation", "org.springframework.boot:spring-boot-autoconfigure")

        // Configuration processor
        deps.add("annotationProcessor", "org.springframework.boot:spring-boot-configuration-processor")
    }
}