import dependencies.Dep
import dependencies.Versions

plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdkVersion(Versions.compileSdk)
    defaultConfig {
        targetSdkVersion(Versions.targetSdk)
        minSdkVersion(Versions.minSdk)
        consumerProguardFiles("proguard-rules.pro")
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dep.Kotlin.stdlib)

    // Network
    api(Dep.OkHttp.okhttp)
    api(Dep.Retrofit.retrofit)
    api(Dep.Retrofit.gsonConverter)
    api(Dep.Retrofit.rxAdapter)
    api(Dep.Gson.gson)

    // ReactiveX
    implementation(Dep.Rx.java)
    implementation(Dep.Rx.android)

    // Test
    testImplementation(Dep.Test.junit)
}
