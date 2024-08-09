import net.fabricmc.loom.task.RemapJarTask
import net.fabricmc.loom.task.RemapSourcesJarTask

plugins {
    id("architectury-plugin") version "3.4.+"
    id ("dev.architectury.loom") version "1.6.+" apply false
}

architectury {
    injectInjectables = false
    minecraft = properties["minecraft_version"].toString()
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "architectury-plugin")
    apply(plugin = "dev.architectury.loom")

    dependencies {
        "minecraft"("com.mojang:minecraft:${properties["minecraft_version"]}")
        "mappings"("net.fabricmc:yarn:${rootProject.properties["yarn_mappings"]}:v2")
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release.set(17)
    }

    tasks.withType<Jar> {
        archiveBaseName.set(properties["archives_base_name"].toString() + "-${project.name}")
    }

    tasks.withType<RemapJarTask> {
        archiveBaseName.set(properties["archives_base_name"].toString() + "-${project.name}")
    }

    tasks.withType<RemapSourcesJarTask> {
        archiveBaseName.set(properties["archives_base_name"].toString() + "-${project.name}")
    }
}

allprojects {
    apply(plugin = "maven-publish")

    version = if (System.getenv("CI_DEV_BUILD")?.toBoolean() == true) {
        System.getenv("BUILD_NUMBER") ?: properties["mod_version"].toString()
    } else {
        properties["mod_version"].toString()
    }

    group = properties["maven_group"].toString()
}
