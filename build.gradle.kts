import io.github.themrmilchmann.gradle.publish.curseforge.ChangelogFormat
import io.github.themrmilchmann.gradle.publish.curseforge.GameVersion
import io.github.themrmilchmann.gradle.publish.curseforge.ReleaseType
import net.fabricmc.loom.task.RemapJarTask

plugins {
    id("architectury-plugin") version "3.4-SNAPSHOT"
    id ("dev.architectury.loom") version "1.3-SNAPSHOT" apply false
    id("com.modrinth.minotaur") version "2.+"
    id("io.github.themrmilchmann.curseforge-publish") version "0.6.1"
}

architectury {
    injectInjectables = false
    minecraft = properties["minecraft_version"].toString()
}

subprojects {
    apply(plugin = "dev.architectury.loom")

    dependencies {
        "minecraft"("com.mojang:minecraft:${properties["minecraft_version"]}")
        "mappings"("net.fabricmc:yarn:${rootProject.properties["yarn_mappings"]}:v2")
    }

    tasks.named("remapJar", RemapJarTask::class) {
        archiveBaseName.set(properties["archives_base_name"].toString() + archiveBaseName)
    }
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")
    apply(plugin = "architectury-plugin")

    version = if (System.getenv("CI_DEV_BUILD")?.toBoolean() == true) {
        System.getenv("BUILD_NUMBER") ?: properties["mod_version"].toString()
    } else {
        properties["mod_version"].toString()
    }

    group = properties["maven_group"].toString()

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release.set(17)
    }

    tasks.withType<Jar> {
        archiveBaseName.set(properties["archives_base_name"].toString())
    }
}

modrinth {
    token.set(System.getenv("MODRINTH_TOKEN"))
    projectId.set("nhSHTGyl")
    versionNumber.set(properties["mod_version"].toString())
    versionType.set(properties["release_channel"].toString())
    uploadFile.set("build/libs/weather-changer-1.0.0.jar")
    gameVersions.addAll("1.20", "1.20.1", "1.20.2", "1.20.3", "1.20.4")

    loaders.add("fabric")
    loaders.add("forge")

    dependencies {
        required.project("fabric-api")
    }

    syncBodyFrom = rootProject.file("README.md").readText()
}

curseforge {
    publications {
        register("curseForge") {
            projectId = "682513"

            gameVersions.add(GameVersion("minecraft-1-20", "1.20"))
            gameVersions.add(GameVersion("minecraft-1-20", "1.20.1"))
            gameVersions.add(GameVersion("minecraft-1-20", "1.20.2"))
            gameVersions.add(GameVersion("minecraft-1-20", "1.20.3"))
            gameVersions.add(GameVersion("minecraft-1-20", "1.20.4"))

            artifacts.register("main") {
                displayName = "Weather Changer"
                // TODO: read from file
                releaseType = ReleaseType.BETA

                changelog {
                    // TODO: get from git diff
                    format = ChangelogFormat.MARKDOWN
                    from(file("CHANGELOG.md"))
                }
            }
        }
    }
}