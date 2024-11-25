/*
 * Modified to build a JAR file.
 *
 * This generated file contains a sample Java application project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.10.2/userguide/building_java_projects.html in the Gradle documentation.
 */

plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application

    // Java plugin
    java

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
    mainClass = "hms.App"
}

tasks.register<Jar>("buildJar") {
    archiveFileName.set("HMS.jar")
    destinationDirectory.set(layout.projectDirectory)  // It's neater to use 'set()' here

    manifest {
        attributes["Main-Class"] = "hms.App"
    }
    
    // To avoid the duplicate handling strategy error
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    // To add all of the dependencies otherwise a "NoClassDefFoundError" error
    from(sourceSets["main"].output)

    // Add dependencies
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })

    // Ensure Java compilation is done before building the JAR
    dependsOn("classes")
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()

    // Ensure that the test task uses the correct classpath
    classpath = sourceSets["test"].runtimeClasspath
}

// Task to generate Javadocs
tasks.register<Javadoc>("generateDocs") {
    source = sourceSets["main"].allJava
    classpath = sourceSets["main"].runtimeClasspath
    destinationDir = file("${layout.buildDirectory.get()}/docs/javadoc")
}
