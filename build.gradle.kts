import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.21"
    java
    application
}

group = "tursom"
version = "0.1"

repositories {
    mavenCentral()
    maven { setUrl("https://jitpack.io") }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
//    compile("com.github.shaunxiao:kotlinGameEngine:v0.0.4")
    file("/usr/lib/jvm/java-8-openjdk/lib/jfxrt.jar")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "cn.tursom.MainKt"
}