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
 * - Lombok annotation processing
 * - MapStruct code generation
 * - SpringDoc OpenAPI documentation
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

        // Apply Git properties for build metadata
        project.plugins.apply("com.gorylenko.gradle-git-properties")

        project.configureDependencies()
    }

    private fun Project.configureDependencies() {
        val libs = extensions.getByType(VersionCatalogsExtension::class.java)
            .named("libs")

        val deps = dependencies

        // Import the Spring Boot platform BOM from the composite build as API (transitive)
        deps.add("api", deps.platform("com.javastarterkit:spring-boot:1.0.0-SNAPSHOT"))

        // Core Spring Boot dependencies — versions managed by the platform BOM
        deps.add("implementation", libs.findLibrary("spring-boot-starter").get())
        deps.add("implementation", libs.findLibrary("spring-boot-autoconfigure").get())

        // Configuration processor
        deps.add("annotationProcessor", libs.findLibrary("spring-boot-configuration-processor").get())

        // ──────────────────────────────────────────────────────────────────────
        // Code Generation & Architecture Plugins
        // ──────────────────────────────────────────────────────────────────────

        // Lombok - Boilerplate code reduction
        deps.add("compileOnly", libs.findLibrary("lombok").get())
        deps.add("annotationProcessor", libs.findLibrary("lombok").get())
        deps.add("testCompileOnly", libs.findLibrary("lombok").get())
        deps.add("testAnnotationProcessor", libs.findLibrary("lombok").get())

        // MapStruct - Object mapping code generation
        deps.add("implementation", libs.findLibrary("mapstruct").get())
        deps.add("annotationProcessor", libs.findLibrary("mapstruct-processor").get())

        // SpringDoc OpenAPI - Code-first API documentation
        deps.add("implementation", libs.findLibrary("springdoc-openapi").get())
    }
}