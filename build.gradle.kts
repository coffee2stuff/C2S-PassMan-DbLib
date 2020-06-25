plugins {
    java
    kotlin("jvm") version "1.3.72"
}

group = "com.peteralexbizjak"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(group = "com.fasterxml.jackson.core", name = "jackson-databind", version = "2.0.1")
    implementation(group = "com.fasterxml.jackson.module", name = "jackson-module-kotlin", version = "2.11.0")
    implementation(group = "org.mongodb", name = "mongodb-driver-reactivestreams", version = "4.1.0-beta2")
    implementation(group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-reactive", version = "1.3.7")
    implementation(group = "org.slf4j", name = "slf4j-log4j12", version = "1.7.30")
    testImplementation("junit", "junit", "4.12")
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