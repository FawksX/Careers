plugins {
    id("careers.platform-conventions")
    id("net.minecrell.plugin-yml.bukkit") version "0.5.1"
    id("io.papermc.paperweight.userdev") version "1.3.6" // not sure if we will need this?
}

dependencies {
    paperDevBundle("1.18.2-R0.1-SNAPSHOT")
    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")

    implementation(project(":careers-common"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.20")
}

tasks {
    shadowJar {

        listOf(
            "cloud.commandframework"
        ).forEach(::reloc)

    }
}

bukkit {
    main = "dev.fawks.careers.CareersPaperPlugin"
    name = "Careers"
    apiVersion = "1.18"
    description = "A better jobs plugin built for Modern Minecraft"
    authors = listOf("FawksX")
}