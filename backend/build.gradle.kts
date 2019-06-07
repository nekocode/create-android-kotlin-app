import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
}

androidExtensions {
    isExperimental = true
    features = setOf("parcelize")
}

android {
    compileSdkVersion(28)
    defaultConfig {
        targetSdkVersion(28)
        minSdkVersion(21)
        consumerProguardFiles("proguard-rules.pro")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    testImplementation("junit:junit:4.12")

    // Kotlin
    implementation(kotlin("stdlib-jdk7", KotlinCompilerVersion.VERSION))

    // Network
    api("com.squareup.okhttp3:okhttp:3.14.1")
    api("com.google.code.gson:gson:2.8.4")
    implementation("com.squareup.retrofit2:retrofit:2.5.0")
    implementation("com.squareup.retrofit2:converter-gson:2.5.0")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.5.0")

    // ReactiveX
    api("io.reactivex.rxjava2:rxjava:2.2.6")
    api("io.reactivex.rxjava2:rxandroid:2.1.1")
}
