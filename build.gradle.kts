plugins {
    id("architectury-plugin") version "3.4-SNAPSHOT"
    id ("dev.architectury.loom") version "1.3-SNAPSHOT" apply false
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
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")
    apply(plugin = "architectury-plugin")

    version = properties["mod_version"].toString()
    group = properties["maven_group"].toString()

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release.set(17)
    }

    tasks.withType<Jar> {
        archiveBaseName.set(properties["archives_base_name"].toString())
    }
}