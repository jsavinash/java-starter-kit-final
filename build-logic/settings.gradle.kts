// ============================================================================
// Java Starter Kit - Build Logic Composite Build Settings
// ============================================================================
// This composite build hosts convention plugins shared across the monorepo.
// ============================================================================

rootProject.name = "build-logic"

dependencyResolutionManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

include(":convention-plugins")