import dependencies.Dep
import dependencies.Versions

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(Versions.compileSdk)
    defaultConfig {
        targetSdkVersion(Versions.targetSdk)
        minSdkVersion(Versions.minSdk)
        applicationId = "cn.nekocode.gank"
        versionCode = 1
        versionName = "1.0"

        val scheme = "gank"

        buildConfigField("String", "SCHEME", "\"$scheme\"")

        manifestPlaceholders = mapOf(
            "APPLICATION_ID" to applicationId,
            "SCHEME" to scheme
        )
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":backend"))
    implementation(Dep.Kotlin.stdlib)

    // Android support libraries
    implementation(Dep.Support.appcompat)
    implementation(Dep.Support.recyclerview)
    implementation(Dep.Support.annotations)
    implementation(Dep.Support.constraint)

    // ReactiveX
    implementation(Dep.Rx.java)
    implementation(Dep.Rx.android)
    implementation(Dep.Rx.dispose)
    implementation(Dep.Rx.disposeAndroid)
    implementation(Dep.Rx.disposeAndroidArch)

    // Others
    implementation(Dep.Meepo.meepo)
    implementation(Dep.AndroidState.state)
    kapt(Dep.AndroidState.processor)
    implementation(Dep.Picasso.picasso)
}
