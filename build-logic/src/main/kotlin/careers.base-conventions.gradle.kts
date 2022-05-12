import net.kyori.indra.IndraExtension

plugins {
    `java-library`
    id("net.kyori.indra")
    id("org.cadixdev.licenser")
    kotlin("jvm")
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