package com.javastarterkit.buildlogic

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.testing.Test
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.jvm.toolchain.JvmVendorSpec

/**
 * Java Base Convention Plugin
 *
 * Configures common Java settings for all subprojects:
 * - Java toolchain (language version 25, any vendor)
 * - UTF-8 encoding
 * - Compiler warnings
 * - JUnit Platform test configuration
 * - Standard source/target compatibility
 */
class JavaBaseConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.configureJava()
        project.configureCompileTasks()
        project.configureTestTasks()
    }

    private fun Project.configureJava() {
        plugins.apply("java-library")

        val java = extensions.getByType(JavaPluginExtension::class.java)
        val libs = extensions.getByType(VersionCatalogsExtension::class.java)
            .named("libs")

        // Configure toolchain
        java.toolchain {
            languageVersion.set(JavaLanguageVersion.of(
                libs.findVersion("java-language").get().displayName
            ))
            // Match the installed Amazon Corretto JDK (as defined in .sdkmanrc)
            vendor.set(JvmVendorSpec.matching("amazon"))
        }

        // Source and target compatibility
        java.sourceCompatibility = JavaVersion.toVersion(
            libs.findVersion("java-language").get().displayName
        )
        java.targetCompatibility = JavaVersion.toVersion(
            libs.findVersion("java-language").get().displayName
        )
    }

    private fun Project.configureCompileTasks() {
        tasks.withType(JavaCompile::class.java).configureEach {
            options.encoding = "UTF-8"
            options.compilerArgs.addAll(
                listOf(
                    "-Xlint:all",
                    "-Xlint:-processing",
                    "-Xlint:-serial",
                    "-Xlint:-path",
                    "-parameters"
                )
            )
        }
    }

    private fun Project.configureTestTasks() {
        tasks.withType(Test::class.java).configureEach {
            useJUnitPlatform()
            testLogging {
                events("passed", "skipped", "failed")
                showStandardStreams = true
                showExceptions = true
                showCauses = true
                showStackTraces = true
            }
            minHeapSize = "512m"
            maxHeapSize = "2g"

            // Java 25+ JVM compatibility settings
            jvmArgs(
                "-XX:+EnableDynamicAgentLoading",  // Allow Mockito inline mocking agent
                "-Xshare:off"                       // Disable CDS to avoid bootstrap classpath warning
            )
        }
    }
}