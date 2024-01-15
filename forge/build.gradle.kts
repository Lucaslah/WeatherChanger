architectury {
    platformSetupLoomIde()
    forge()
}

loom {
    accessWidenerPath.set(project(":common").loom.accessWidenerPath)

    forge.apply {
        convertAccessWideners.set(true)
        extraAccessWideners.add(loom.accessWidenerPath.get().asFile.name)

        mixinConfig("weatherchanger.mixins.json")
    }
}

val common: Configuration by configurations.creating
val shadowCommon: Configuration by configurations.creating

configurations {
    compileOnly.configure { extendsFrom(common) }
    runtimeOnly.configure { extendsFrom(common) }
}

dependencies {
    forge("net.minecraftforge:forge:${rootProject.property("forge.version")}")

    common(project(":common", "namedElements")) {
        isTransitive = false
    }

    shadowCommon(project(":common", "transformProductionForge")) {
        isTransitive = false
    }
}

tasks {
    processResources {
        inputs.property("group", rootProject.property("maven_group"))
        inputs.property("version", project.version)

        filesMatching("META-INF/mods.toml") {
            expand(mapOf(
                "group" to rootProject.property("maven_group"),
                "version" to project.version,

                "mod_id" to rootProject.property("mod_id"),
                "minecraft_version" to rootProject.property("minecraft_version")
            ))
        }
    }

    remapJar {
        injectAccessWidener.set(true)
    }

    jar {
        from("../LICENSE.md")
        from("../assets/logo.png") {
            rename { "icon.png" }
        }

        dependsOn(":common:transformProductionForge")

        from({
            shadowCommon.filter { it.name.endsWith("jar") }.map { zipTree(it) }
        })
    }
}

java {
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }

    // See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
    repositories {
        // Add repositories to publish to here.
        // Notice: This block does NOT have the same function as the block in the top level.
        // The repositories here will be used for publishing your artifact, not for
        // retrieving dependencies.
    }
}
