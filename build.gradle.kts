plugins {
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.serialization") version "1.9.24"
    alias(libs.plugins.ktor)
}

group = "gay.asoji"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://gitlab.com/api/v4/projects/26794598/packages/maven") // Aviation GitLab
}

dependencies {
    api(libs.slf4j.api)
    implementation("io.ktor:ktor-client-cio-jvm:2.3.11")
    testImplementation(kotlin("test"))
    implementation(kotlin("reflect"))
    implementation(libs.reflection)
    implementation(libs.bundles.jda)
    implementation(libs.bundles.ktor)
    implementation(libs.bundles.ktoml)
    implementation(libs.bundles.kotlinx)
    implementation(libs.bundles.logback)
    implementation(libs.kotlin.logging)
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("gay.asoji.goveelightsdiscordbot.MainKt")
}

ktor {
    fatJar {
        archiveFileName.set("GoveeLightsDiscordBot.jar")
    }
}