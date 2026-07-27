package com.javastarterkit.buildlogic

import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Code Quality Convention Plugin
 *
 * Configures code quality tooling for all subprojects:
 * - Spotless for code formatting (Google Java Format)
 * - Consistent formatting rules
 * - License header enforcement
 *
 * This plugin is automatically applied by the Spring Boot convention
 * plugins, but can also be applied standalone.
 */
class CodeQualityConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.configureSpotless()
    }

    private fun Project.configureSpotless() {
        // Apply Spotless plugin
        project.plugins.apply("com.diffplug.spotless")

        // Configure Spotless
        project.extensions.configure(SpotlessExtension::class.java) {
            java {
                target("src/**/*.java")
                targetExclude("**/build/**", "**/generated/**")

                // Use Palantir Java format for consistent styling
                palantirJavaFormat()

                // Enforce license header
                licenseHeader("// Copyright © \$YEAR Java Starter Kit. All rights reserved.")
                trimTrailingWhitespace()
                endWithNewline()
            }

            kotlin {
                target("**/*.kt")
                targetExclude("**/build/**", "**/generated/**")
                ktlint()
                trimTrailingWhitespace()
                endWithNewline()
            }

            kotlinGradle {
                target("**/*.gradle.kts")
                targetExclude("**/build/**")
                ktlint()
                trimTrailingWhitespace()
                endWithNewline()
            }
        }

        // Wire spotlessCheck into the check lifecycle
        project.tasks.matching { it.name == "check" }.configureEach {
            this.dependsOn("spotlessCheck")
        }
    }
}