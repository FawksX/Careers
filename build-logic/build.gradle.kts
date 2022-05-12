plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation(libs.indraCommon)
    implementation(libs.indraPublishing)
    implementation(libs.licenser)
    implementation(libs.shadow)
    implementation(libs.kotlin)
}