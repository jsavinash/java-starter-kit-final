// ============================================================================
// Platforms - Root build file
// Applies shared conventions and lifecycle tasks across all sub-projects.
// ============================================================================

// ---------------------------------------------------------------------------
// ben-manes versions plugin for checking/updating dependency versions
// ---------------------------------------------------------------------------
plugins {
    alias(libs.plugins.ben.manes.versions)
}

// ---------------------------------------------------------------------------
// Dependency locking for reproducible builds
// Lock files are stored in gradle/dependency-locks/
// ---------------------------------------------------------------------------
dependencyLocking {
    lockAllConfigurations()
    lockMode = LockMode.LENIENT // Allow builds even without lock files initially
}

// ---------------------------------------------------------------------------
// Common configuration for all sub-projects
// ---------------------------------------------------------------------------
subprojects {
    apply(plugin = "platforms.java-conventions")
    apply(plugin = "com.diffplug.spotless")

    group = "com.starterkit"
    version = "1.0.0-SNAPSHOT"

    // Dependency locking for each sub-project
    dependencyLocking {
        lockAllConfigurations()
        lockMode = LockMode.LENIENT
    }

    // Configure Spotless code formatting for this subproject
    plugins.withId("com.diffplug.spotless") {
        extensions.configure<com.diffplug.gradle.spotless.SpotlessExtension> {
            java {
                // Basic Java formatting (compatible with all JDK versions)
                // Enforces consistent code style without external formatter dependencies
                importOrder("java", "javax", "org", "com", "com.starterkit", "")
                removeUnusedImports()
                trimTrailingWhitespace()
                endWithNewline()
                target("src/**/*.java")
            }
            kotlinGradle {
                ktlint()
                trimTrailingWhitespace()
                endWithNewline()
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Tasks for version management and dependency locking workflow
// ---------------------------------------------------------------------------

// Check for newer versions of all dependencies
tasks.register("checkLatestVersions") {
    group = "version management"
    description = "Check for newer versions of all dependencies (reports only, no changes)"
    dependsOn(tasks.named("dependencyUpdates"))
}

// Regenerate all dependency lock files
tasks.register("lockDependencies") {
    group = "version management"
    description = "Regenerate all dependency lock files for reproducible builds"
    dependsOn(subprojects.map { "${it.path}:dependencies" })
    doLast {
        logger.lifecycle("Dependency lock files regenerated. Commit the updated lock files and version catalog.")
    }
}

// Full workflow: check latest versions, then regenerate locks
tasks.register("useLatestVersionsAndLock") {
    group = "version management"
    description = "Full workflow: check latest versions and regenerate lock files"
    dependsOn(tasks.named("dependencyUpdates"))
    finalizedBy(tasks.named("lockDependencies"))
    doLast {
        logger.lifecycle("""
            ✅ useLatestVersionsAndLock complete!
            
            Next steps:
            1. Review the dependency update report
            2. Manually update desired versions in gradle/libs.versions.toml
            3. Run './gradlew :platforms:lockDependencies' to regenerate lock files
            4. Run './gradlew :platforms:build' to verify everything works
            5. Commit the changes
        """.trimIndent())
    }
}