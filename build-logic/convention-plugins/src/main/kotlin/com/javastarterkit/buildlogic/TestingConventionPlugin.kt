package com.javastarterkit.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension

/**
 * Testing Convention Plugin
 *
 * Configures testing dependencies for all subprojects:
 * - JUnit Jupiter 5 (API, Engine, Params)
 * - AssertJ for fluent assertions
 * - Mockito for mocking (core + JUnit Jupiter integration)
 * - JUnit Platform Launcher for IDE/tooling support
 *
 * This plugin is automatically applied by the Spring Boot convention
 * plugins, but can also be applied standalone to non-Spring modules.
 */
class TestingConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.configureTestDependencies()
    }

    private fun Project.configureTestDependencies() {
        val libs = extensions.getByType(VersionCatalogsExtension::class.java)
            .named("libs")

        val deps = dependencies

        // JUnit Jupiter
        deps.add("testImplementation", libs.findLibrary("junit-jupiter-api").get())
        deps.add("testRuntimeOnly", libs.findLibrary("junit-jupiter-engine").get())
        deps.add("testImplementation", libs.findLibrary("junit-jupiter-params").get())

        // JUnit Platform Launcher (for tooling support)
        deps.add("testRuntimeOnly", libs.findLibrary("junit-platform-launcher").get())

        // AssertJ
        deps.add("testImplementation", libs.findLibrary("assertj-core").get())

        // Mockito
        deps.add("testImplementation", libs.findLibrary("mockito-core").get())
        deps.add("testImplementation", libs.findLibrary("mockito-junit-jupiter").get())
    }
}