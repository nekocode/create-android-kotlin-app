import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
}

android {
    compileSdkVersion(29)
    defaultConfig {
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
    testImplementation("junit:junit:4.13.1")

    // Kotlin
    implementation(kotlin("stdlib-jdk8", KotlinCompilerVersion.VERSION))

    // Network
    api("com.squareup.okhttp3:okhttp:4.9.1")
    api("com.google.code.gson:gson:2.8.6")
    api("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:adapter-rxjava3:2.9.0")
    testImplementation("com.squareup.okhttp3:logging-interceptor:4.9.1")

    // ReactiveX
    api("io.reactivex.rxjava3:rxkotlin:3.0.1")
    api("io.reactivex.rxjava3:rxandroid:3.0.0")

    // Dependency injection
    implementation("com.google.dagger:dagger:2.31.2")
    kapt("com.google.dagger:dagger-compiler:2.31.2")
    kaptTest("com.google.dagger:dagger-compiler:2.31.2")

    // Testing
    testImplementation(kotlin("test-junit", KotlinCompilerVersion.VERSION))
}

tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class.java).all {
    kotlinOptions.jvmTarget = "1.8"
}
