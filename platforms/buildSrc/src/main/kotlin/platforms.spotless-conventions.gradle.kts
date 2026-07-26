// ============================================================================
// platforms.spotless-conventions - Spotless code formatting for Java projects
// Applies consistent formatting rules across all platforms.
//
// NOTE: This plugin is applied from platforms/build.gradle.kts subprojects {}
// block because precompiled script plugins cannot resolve third-party DSL types.
// The spotless { } configuration is defined directly in platforms/build.gradle.kts.
// ============================================================================

// Marker convention plugin — Spotless config is applied via platforms/build.gradle.kts
// This file exists so that subprojects can declare: apply(plugin = "platforms.spotless-conventions")
// if needed, but the actual Spotless plugin and its configuration are applied
// centrally in the root platforms/build.gradle.kts subprojects {} block.