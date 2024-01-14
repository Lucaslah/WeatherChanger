architectury {
    platformSetupLoomIde()
    fabric()
}

loom {
    accessWidenerPath = project(":common").loom.accessWidenerPath
}

val common: Configuration by configurations.creating
val shadowCommon: Configuration by configurations.creating

configurations {
    compileOnly.configure { extendsFrom(common) }
    runtimeOnly.configure { extendsFrom(common) }
}

dependencies {
    modImplementation("net.fabricmc:fabric-loader:${rootProject.property("fabric.loader_version")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${rootProject.property("fabric.version")}")

    common(project(":common", "namedElements")) {
        isTransitive = false
    }

    shadowCommon(project(":common", "transformProductionFabric")){
        isTransitive = false
    }
}

tasks {
    processResources {
        inputs.property("version", project.version)

        filesMatching("fabric.mod.json") {
            expand(mapOf(
                "version" to project.version,
                "mod_id" to rootProject.property("mod_id"),
                "minecraft_version" to rootProject.property("minecraft_version"),
                "minecraft_base_version" to rootProject.property("minecraft_base"),
                "fabric_loader_version" to rootProject.property("fabric.loader_version")
            ))
        }
    }

    remapJar {
        injectAccessWidener.set(true)
    }

    jar {
        from("../LICENSE.md")
        from("../assets/logo.png") {
            rename { "assets/weatherchanger/icon.png" }
        }

        dependsOn(":common:transformProductionFabric")

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
