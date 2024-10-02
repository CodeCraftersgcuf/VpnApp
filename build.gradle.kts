// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    kotlin("jvm")  // Add the Kotlin plugin
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))  // Include the Kotlin standard library
}

kotlin {
    jvmToolchain(8)  // Specify Java 8 toolchain
}
