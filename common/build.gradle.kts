plugins {
    id("careers.base-conventions")
}

dependencies {

    implementation(project(":careers-api"))

    implementation(libs.stdlib)

    implementation(libs.cloudCore)
    implementation(libs.cloudAnnotations)
    implementation(libs.cloudExtras)
    implementation(libs.configurate)

    // Provided in Sponge and Paper
    compileOnly(libs.adventureApi)
    compileOnly(libs.adventureMiniMessage)
    compileOnly(libs.slf4j)
    compileOnly(libs.guava)

}