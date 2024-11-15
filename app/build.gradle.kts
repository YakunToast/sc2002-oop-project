/*
 * Modified to build a JAR file.
 *
 * This generated file contains a sample Java application project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.10.2/userguide/building_java_projects.html in the Gradle documentation.
 */

plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application

    // Apply the Java Library plugin to build a JAR file (replaces 'application' plugin).
    `java-library`
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Use JUnit Jupiter for testing.
    testImplementation(libs.junit.jupiter)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // This dependency is used by the library.
    implementation(libs.guava)

    // Apache Commons CSV for CSV file handling
    implementation("org.apache.commons:commons-csv:1.9.0")
    
    // BCrypt for password hashing
    implementation("org.mindrot:jbcrypt:0.4")

    // XLSX Reading
    implementation("org.apache.poi:poi:5.3.0")
    implementation("org.apache.poi:poi-ooxml:5.3.0")

    // Log4j2 core dependency
    implementation("org.apache.logging.log4j:log4j-core:2.20.0")

    // Log4j2 API dependency
    implementation("org.apache.logging.log4j:log4j-api:2.20.0")
    
    // For testing
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    // Define the main class for the application.
    mainClass = "sc2002.HMSApp"
}

tasks.withType<Jar>() {

    manifest {
        attributes["Main-Class"] = "sc2002.HMSApp"
    }
    
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}
