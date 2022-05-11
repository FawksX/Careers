plugins {
    id("careers.root-conventions")
    kotlin("jvm") version "1.6.20"
    java
}

allprojects {
    group = "dev.fawks"
    version = "1.0-SNAPSHOT"

}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

repositories {
    mavenCentral()
}