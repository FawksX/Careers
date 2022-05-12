plugins {
    id("careers.base-conventions")
}

dependencies {

    implementation(libs.stdlib)

    compileOnly(libs.adventureApi)
    compileOnly(libs.adventureMiniMessage)
}