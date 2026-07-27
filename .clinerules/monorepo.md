You are an expert Java/Gradle build engineer. Analyze and refactor the attached project using dependencies mentioned in .sdkmanrc file. Ensure all modules and settings strictly adhere to these toolchain versions.

Execute the following tasks systematically:
1. Remove unnecessary (code, dependencies)
-  Analysis project structure and remove unnecessary dependencies from (libs.versions.toml).

1. Centralize Dependencies (libs.versions.toml)
- Audit all `build.gradle.kts` files.
- Move any hardcoded dependencies or version strings into the `libs.versions.toml` file.
- Update all project dependencies to their latest stable versions that are fully compatible with dependencies mentioned in .sdkmanrc file.
- Reference these dependencies in `build.gradle.kts` using the `libs` type-safe accessors only.

2. Enforce Custom Plugins
- Review the project's custom java platform and Gradle plugins (e.g., in `build-logic` or an included build, `platforms` ).
- Ensure the project utilizes these custom plugins first for code quality, publishing, or common configurations before applying standard convention plugins.

3. Codebase and Structural Analysis
- Analyze the project layout for structural inconsistencies (e.g., misplaced source folders, incorrect package declarations, or missing standard directories).
- Fix these inconsistencies without altering core business logic or breaking module boundaries.

4. Test Suite, Quality Checks, and Build Fixes
- Run and fix all test cases to ensure they pass under Java 25.
- Address compilation errors, deprecation warnings, and static analysis/linter flags (e.g., Checkstyle, Spotless, or Sonar).
- Ensure a clean execution of `./gradlew clean build`.

5. Documentation Update
- Update all relevant Markdown (`.md`) files, including the README.
- Document the upgraded tech stack mentioned in .sdkmanrc file, local setup prerequisites, and any newly introduced build commands or plugin behaviors.

Provide a summary of the changes made, including a list of updated dependencies and structural corrections.
