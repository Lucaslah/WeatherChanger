plugins {
    base
}

val javaVersion = providers.gradleProperty("java_version").get().toInt()

subprojects {
    apply(plugin = "java")

    extensions.configure<org.gradle.api.plugins.JavaPluginExtension> {
        toolchain.languageVersion.set(org.gradle.jvm.toolchain.JavaLanguageVersion.of(javaVersion))
    }

    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        options.release.set(javaVersion)
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

tasks.named("build") {
    dependsOn(":fabric:build", ":forge:build")
}
