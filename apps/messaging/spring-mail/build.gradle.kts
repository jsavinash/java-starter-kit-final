// ============================================================================
// Spring Mail — Example Application
// ============================================================================
// Demonstrates Spring Mail concepts:
// - Email sending with JavaMailSender
// - MIME message construction
// - HTML email templates
// - Attachments and inline resources
// ============================================================================

plugins {
    id("com.javastarterkit.buildlogic.spring-boot-application")
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "com.javastarterkit"
version = "1.0.0-SNAPSHOT"

description = "Spring Mail Example"

dependencies {
    // Mail starter
    implementation(libs.findLibrary("spring-boot-starter-mail").get())

    // Web starter
    implementation(libs.findLibrary("spring-boot-starter-web").get())

    // Test starter
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
}

springBoot {
    mainClass = "com.javastarterkit.mail.MailApplication"
}
