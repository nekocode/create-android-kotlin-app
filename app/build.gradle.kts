import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(28)
    defaultConfig {
        targetSdkVersion(28)
        minSdkVersion(16)
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

    // Kotlin
    implementation(kotlin("stdlib-jdk7", KotlinCompilerVersion.VERSION))

    // Android support libraries
    implementation("com.android.support:appcompat-v7:28.0.0")
    implementation("com.android.support:recyclerview-v7:28.0.0")
    implementation("com.android.support:support-annotations:28.0.0")
    implementation("com.android.support.constraint:constraint-layout:1.1.3")

    // ReactiveX
    implementation("io.reactivex.rxjava2:rxjava:2.2.2")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.0")
    implementation("com.uber.autodispose:autodispose:0.8.0")
    implementation("com.uber.autodispose:autodispose-android:0.8.0")
    implementation("com.uber.autodispose:autodispose-android-archcomponents:0.8.0")

    // Others
    implementation("com.squareup.picasso:picasso:2.5.2")
    implementation("com.github.nekocode:Meepo:0.3")
    implementation("com.evernote:android-state:1.3.1")
    kapt("com.evernote:android-state-processor:1.3.1")
}
