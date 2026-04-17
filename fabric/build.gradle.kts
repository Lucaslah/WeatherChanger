plugins {
    id("net.fabricmc.fabric-loom") version "1.15.5"
}

base {
    archivesName.set("${rootProject.property("archives_base_name")}-fabric")
}

sourceSets {
    named("main") {
        java.srcDir("../common/src/main/java")
        resources.srcDir("../common/src/main/resources")
    }
}

loom {
    accessWidenerPath = file("../common/src/main/resources/weatherchanger.accesswidener")

    mods {
        create(rootProject.property("mod_id").toString()) {
            sourceSet(sourceSets.main.get())
        }
    }
}

dependencies {
    minecraft("com.mojang:minecraft:${rootProject.property("minecraft_version")}")

    implementation("com.google.code.gson:gson:2.10.1")
    implementation("net.fabricmc:fabric-loader:${rootProject.property("fabric.loader_version")}")
    implementation("net.fabricmc.fabric-api:fabric-api:${rootProject.property("fabric.version")}")
    implementation("net.fabricmc.fabric-api:fabric-key-mapping-api-v1:2.0.4+e2bdee7847")
}

tasks {
    processResources {
        val properties = mapOf(
            "version" to project.version,
            "mod_id" to rootProject.property("mod_id"),
            "minecraft_version" to rootProject.property("minecraft_version"),
            "fabric_loader_version" to rootProject.property("fabric.loader_version"),
            "java_version" to rootProject.property("java_version")
        )

        inputs.properties(properties)

        filesMatching("fabric.mod.json") {
            expand(properties)
        }
    }

    jar {
        from("../LICENSE.md")
        from("../assets/logo.png") {
            rename { "assets/weatherchanger/icon.png" }
        }
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
