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

plugins {
    id("com.gradle.enterprise") version("3.15.1")
}

gradleEnterprise {
    if (System.getenv("CI") != null) {
        buildScan {
            publishAlways()
            termsOfServiceUrl = "https://gradle.com/terms-of-service"
            termsOfServiceAgree = "yes"
        }
    }
}

include("common")
include("fabric")
include("forge")
