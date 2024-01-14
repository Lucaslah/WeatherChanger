rootProject.name = "WeatherChanger"

pluginManagement {
    repositories {
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }

        maven {
            name = "Forge"
            url = uri("https://maven.minecraftforge.net/")
        }

        maven {
            name = "Architectury"
            url = uri("https://maven.architectury.dev/")
        }

        mavenCentral()
        gradlePluginPortal()
    }
}

include("common")
include("fabric")