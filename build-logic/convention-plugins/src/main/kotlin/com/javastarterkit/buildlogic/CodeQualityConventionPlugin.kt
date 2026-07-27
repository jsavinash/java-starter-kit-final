package com.javastarterkit.buildlogic

import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.quality.Checkstyle
import org.gradle.api.plugins.quality.CheckstyleExtension
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.tasks.JacocoReport

/**
 * Code Quality Convention Plugin
 *
 * Configures comprehensive code quality tooling for all subprojects:
 * - Spotless for code formatting
 * - Checkstyle for code style enforcement
 * - SpotBugs for static bytecode analysis
 * - JaCoCo for code coverage
 * - OWASP Dependency Check for security vulnerability scanning
 * - SonarQube for centralized quality dashboard integration
 */
class CodeQualityConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.configureSpotless()
        project.configureCheckstyle()
        project.configureSpotBugs()
        project.configureJaCoCo()
        project.configureOwaspDependencyCheck()
        project.configureSonarQube()
    }

    private fun Project.configureSpotless() {
        project.plugins.apply("com.diffplug.spotless")

        project.extensions.configure(SpotlessExtension::class.java) {
            java {
                target("src/**/*.java")
                targetExclude("**/build/**", "**/generated/**")
                palantirJavaFormat()
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

        project.tasks.matching { it.name == "check" }.configureEach {
            dependsOn("spotlessCheck")
        }
    }

    private fun Project.configureCheckstyle() {
        plugins.apply("checkstyle")

        val libs = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")
        val checkstyleVersion = libs.findVersion("checkstyle").get().displayName

        val checkstyleExt = extensions.findByType(CheckstyleExtension::class.java)
        checkstyleExt?.apply {
            toolVersion = checkstyleVersion
            isIgnoreFailures = false
            configFile = project.rootProject.file("gradle/checkstyle/checkstyle.xml")
        }
    }

    private fun Project.configureSpotBugs() {
        plugins.apply("com.github.spotbugs")

        val libs = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")
        dependencies.add("compileOnly", libs.findLibrary("spotbugs-annotations").get())
    }

    private fun Project.configureJaCoCo() {
        plugins.apply("jacoco")

        val libs = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")
        val jacocoVersion = libs.findVersion("jacoco").get().displayName

        val jacocoExt = extensions.findByType(JacocoPluginExtension::class.java)
        jacocoExt?.apply {
            toolVersion = jacocoVersion
        }
    }

    private fun Project.configureOwaspDependencyCheck() {
        plugins.apply("org.owasp.dependencycheck")

        val extension = project.extensions.findByType(
            org.owasp.dependencycheck.gradle.extension.DependencyCheckExtension::class.java
        )
        extension?.apply {
            setProperty("failBuildOnCVSS", 8.0f)
            setProperty("formats", listOf("HTML", "JSON"))
        }
    }

    private fun Project.configureSonarQube() {
        plugins.apply("org.sonarqube")
    }
}