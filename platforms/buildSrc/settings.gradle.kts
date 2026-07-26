// ============================================================================
// buildSrc - Convention plugins for all platforms
// Consumes the same version catalog as the main platforms build.
// ============================================================================

dependencyResolutionManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "buildSrc"