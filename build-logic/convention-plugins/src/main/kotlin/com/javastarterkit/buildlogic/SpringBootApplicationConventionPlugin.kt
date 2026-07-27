package com.javastarterkit.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.tasks.JavaExec
import org.gradle.jvm.tasks.Jar

/**
 * Spring Boot Application Convention Plugin
 *
 * Configures a Spring Boot executable application module with:
 * - Spring Boot plugin and dependency management
 * - Spring Boot platform BOM from the composite build
 * - BootJar packaging configuration
 * - Application main class setup
 * - Devtools support
 * - Actuator support
 * - Testing and code quality conventions
 */
class SpringBootApplicationConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        // Apply foundational plugins
        project.plugins.apply("com.javastarterkit.buildlogic.java-base")
        project.plugins.apply("com.javastarterkit.buildlogic.testing")
        project.plugins.apply("com.javastarterkit.buildlogic.code-quality")

        // Apply Spring Boot plugins
        project.plugins.apply("org.springframework.boot")
        project.plugins.apply("io.spring.dependency-management")

        project.configureSpringBootApplication()
        project.configureDependencies()
    }

    private fun Project.configureSpringBootApplication() {
        // Configure BootJar task
        tasks.named("bootJar", Jar::class.java) {
            archiveFileName.set("${project.name}.jar")
        }

        // Configure bootRun with remote debugging
        tasks.named("bootRun", JavaExec::class.java) {
            jvmArgs("-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005")
        }
    }

    private fun Project.configureDependencies() {
        val libs = extensions.getByType(VersionCatalogsExtension::class.java)
            .named("libs")

        val deps = dependencies

        deps.add("implementation", deps.platform("com.javastarterkit:spring-boot:1.0.0-SNAPSHOT"))

        // Spring Boot starters — versions managed by the platform BOM
        deps.add("implementation", libs.findLibrary("spring-boot-starter").get())
        deps.add("implementation", libs.findLibrary("spring-boot-starter-actuator").get())

        // Configuration processor
        deps.add("annotationProcessor", libs.findLibrary("spring-boot-configuration-processor").get())

        // Devtools for development only
        deps.add("developmentOnly", libs.findLibrary("spring-boot-devtools").get())
    }
}