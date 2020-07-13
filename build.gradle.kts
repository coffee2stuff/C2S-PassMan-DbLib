plugins {
    java
    kotlin("jvm") version "1.3.72"
    id("org.jetbrains.dokka") version "0.10.1"
}

group = "com.peteralexbizjak"
version = "0.0.2"

repositories {
    mavenCentral()
    jcenter()
}

tasks {
    val dokka by getting(org.jetbrains.dokka.gradle.DokkaTask::class) {
        outputFormat = "html"
        outputDirectory = "$buildDir/dokka"
        configuration {
            includeNonPublic = true
            platform = "JVM"
        }
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(group = "com.fasterxml.jackson.core", name = "jackson-databind", version = "2.0.1")
    implementation(group = "com.fasterxml.jackson.module", name = "jackson-module-kotlin", version = "2.11.0")
    implementation(group = "org.mongodb", name = "mongodb-driver-sync", version = "4.1.0-beta2")
    implementation("org.koin:koin-core:2.1.6")
    implementation(group = "org.slf4j", name = "slf4j-log4j12", version = "1.7.30")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}
