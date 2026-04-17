plugins {
    java
    id("net.minecraftforge.gradle") version "[7.0.17,8)"
}

val mixinConfig = "weatherchanger.mixins.json"
val mixinRefMap = layout.buildDirectory.file("tmp/mixin/compileJava/weatherchanger.refmap.json").get().asFile
val forgeVersion = rootProject.property("forge.version").toString()
val minecraftExtension = extensions.getByName("minecraft") as groovy.lang.GroovyObject
val forgeDependency = minecraftExtension.invokeMethod("dependency", "net.minecraftforge:forge:$forgeVersion")

base {
    archivesName.set("${rootProject.property("archives_base_name")}-forge")
}

sourceSets {
    named("main") {
        java.srcDir("../common/src/main/java")
        resources.srcDir("../common/src/main/resources")
    }
}

extensions.configure<Any>("minecraft") {
    withGroovyBuilder {
        "runs" {
            "configureEach" {
                setProperty("workingDir", layout.projectDirectory.dir("run").asFile)
                "systemProperty"("eventbus.api.strictRuntimeChecks", "true")
                "args"("--mixin.config", mixinConfig)
            }

            "register"("client")
            "register"("server") {
                "args"("--nogui")
            }
        }
    }
}

repositories {
    minecraftExtension.invokeMethod("mavenizer", this)
    mavenCentral()
    maven("https://repo.spongepowered.org/repository/maven-public/")
}

dependencies {
    add("implementation", forgeDependency)
    implementation("com.google.code.gson:gson:2.10.1")
    add("annotationProcessor", "org.spongepowered:mixin:${rootProject.property("mixin_version")}:processor")
}

tasks.named<org.gradle.api.tasks.compile.JavaCompile>("compileJava") {
    doFirst {
        mixinRefMap.parentFile.mkdirs()
        mixinRefMap.delete()

        options.compilerArgs.removeAll { it.startsWith("-AoutRefMapFile=") || it == "-ApluginVersion=0.7" }
        options.compilerArgs.addAll(
            listOf(
                "-AoutRefMapFile=${mixinRefMap.canonicalPath}",
                "-ApluginVersion=0.7"
            )
        )
    }
}

tasks.named<org.gradle.language.jvm.tasks.ProcessResources>("processResources") {
    val sharedIcon = file("../assets/logo.png")
    val properties = mapOf(
        "version" to project.version,
        "mod_id" to rootProject.property("mod_id"),
        "minecraft_version" to rootProject.property("minecraft_version"),
        "loader_version" to forgeVersion.substringAfter('-').substringBefore('.'),
        "forge_version" to forgeVersion.substringAfter('-'),
        "shared_icon" to sharedIcon.path
    )

    inputs.properties(properties)

    from(sharedIcon) {
        into("")
        rename { "icon.png" }
    }

    filesMatching("META-INF/mods.toml") {
        expand(properties)
    }
}

tasks.named<org.gradle.jvm.tasks.Jar>("jar") {
    from("../LICENSE.md")
    from(mixinRefMap)

    manifest {
        attributes(
            mapOf(
                "Implementation-Version" to project.version,
                "MixinConfigs" to mixinConfig
            )
        )
    }
}

java {
    withSourcesJar()
    toolchain.languageVersion.set(org.gradle.jvm.toolchain.JavaLanguageVersion.of(rootProject.property("java_version").toString().toInt()))
}

publishing {
    publications {
        create<org.gradle.api.publish.maven.MavenPublication>("mavenJava") {
            artifact(tasks.named("jar"))
        }
    }
}