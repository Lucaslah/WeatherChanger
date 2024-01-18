architectury {
    common(rootProject.property("enabled_platforms").toString().split(","))
}

loom {
    accessWidenerPath = file("src/main/resources/weatherchanger.accesswidener")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.10.1")
    modImplementation("net.fabricmc:fabric-loader:${rootProject.property("fabric.loader_version")}")
}
