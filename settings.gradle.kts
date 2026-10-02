rootProject.name = "WeatherChanger"

pluginManagement {
    plugins {
        id("net.fabricmc.fabric-loom") version providers.gradleProperty("fabric_loom_version").get()
        id("net.minecraftforge.gradle") version providers.gradleProperty("forge_gradle_version").get()
    }

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
            name = "Sponge"
            url = uri("https://repo.spongepowered.org/repository/maven-public/")
        }

        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()

        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }

        maven {
            name = "Forge"
            url = uri("https://maven.minecraftforge.net/")
        }

        maven {
            name = "Sponge"
            url = uri("https://repo.spongepowered.org/repository/maven-public/")
        }

        maven {
            name = "Mojang"
            url = uri("https://libraries.minecraft.net/")
        }
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

include("fabric")
include("forge")
