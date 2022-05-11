import net.kyori.indra.IndraExtension
import org.cadixdev.gradle.licenser.header.HeaderStyle

plugins {
    `java-library`
    id("net.kyori.indra")
    id("org.cadixdev.licenser")
}

configure<IndraExtension> {
    javaVersions {
        minimumToolchain(17)
        target(17)
    }
}

repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    maven("https://papermc.io/repo/repository/maven-public/")
}

license {
    header(rootProject.file("HEADER"))
}