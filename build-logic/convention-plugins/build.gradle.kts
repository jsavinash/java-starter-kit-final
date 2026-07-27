// ============================================================================
// Build Logic - Convention Plugins
// ============================================================================
// This module defines convention (precompiled script) plugins that are
// applied to subprojects in the monorepo to ensure consistent builds.
// ============================================================================

plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit.buildlogic"
version = "1.0.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.toVersion(libs.findVersion("java-language").get().displayName)
    targetCompatibility = JavaVersion.toVersion(libs.findVersion("java-language").get().displayName)
}

dependencies {
    // Kotlin stdlib for Gradle DSL
    implementation(libs.findLibrary("kotlin-stdlib").get())

    // External plugins that convention plugins apply programmatically
    // must be on the convention plugin's classpath
    implementation(plugin(libs, "spotless"))
    implementation(plugin(libs, "spring-boot"))
    implementation(plugin(libs, "spring-dependency-management"))
    implementation(plugin(libs, "jib"))
    implementation(plugin(libs, "git-properties"))
    implementation(plugin(libs, "owasp-dependency-check"))
    implementation(plugin(libs, "spotbugs"))
    implementation(plugin(libs, "sonarqube"))
}

fun plugin(libs: VersionCatalog, alias: String): String {
    val plugin = libs.findPlugin(alias).get().get()
    val id = plugin.pluginId
    val version = libs.findVersion(
        when (alias) {
            "spring-boot" -> "spring-boot"
            "spring-dependency-management" -> "spring-dependency-management"
            "spotless" -> "spotless"
            "spotbugs" -> "spotbugs-gradle-plugin"
            else -> alias
        }
    ).get().displayName
    return "$id:$id.gradle.plugin:$version"
}

gradlePlugin {
    plugins {
        register("javaBaseConventions") {
            id = "com.javastarterkit.buildlogic.java-base"
            displayName = "Java Base Conventions"
            description = "Configures Java language version, encoding, and standard settings"
            implementationClass = "com.javastarterkit.buildlogic.JavaBaseConventionPlugin"
        }
        register("springBootApplicationConventions") {
            id = "com.javastarterkit.buildlogic.spring-boot-application"
            displayName = "Spring Boot Application Conventions"
            description = "Configures Spring Boot application with dependency management, testing, and code quality"
            implementationClass = "com.javastarterkit.buildlogic.SpringBootApplicationConventionPlugin"
        }
        register("springBootLibraryConventions") {
            id = "com.javastarterkit.buildlogic.spring-boot-library"
            displayName = "Spring Boot Library Conventions"
            description = "Configures a Spring Boot library module with dependency management and testing"
            implementationClass = "com.javastarterkit.buildlogic.SpringBootLibraryConventionPlugin"
        }
        register("testingConventions") {
            id = "com.javastarterkit.buildlogic.testing"
            displayName = "Testing Conventions"
            description = "Configures JUnit Jupiter, AssertJ, Mockito for all test configurations"
            implementationClass = "com.javastarterkit.buildlogic.TestingConventionPlugin"
        }
        register("codeQualityConventions") {
            id = "com.javastarterkit.buildlogic.code-quality"
            displayName = "Code Quality Conventions"
            description = "Configures Spotless code formatting and static analysis"
            implementationClass = "com.javastarterkit.buildlogic.CodeQualityConventionPlugin"
        }
    }
}