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
        applicationId = "cn.nekocode.gank"
        minSdkVersion(16)
        targetSdkVersion(28)
        versionCode = 1
        versionName = "1.0"

        val SCHEME = "gank"

        buildConfigField("String", "SCHEME", "\"${SCHEME}\"")

        manifestPlaceholders = mapOf(
            "APPLICATION_ID" to applicationId,
            "SCHEME" to SCHEME
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
    val SUPPORT_VERSION = "28.0.0"
    implementation("com.android.support:appcompat-v7:$SUPPORT_VERSION")
    implementation("com.android.support:recyclerview-v7:$SUPPORT_VERSION")
    implementation("com.android.support:support-annotations:$SUPPORT_VERSION")
    implementation("com.android.support.constraint:constraint-layout:1.1.3")

    // ReactiveX
    implementation("io.reactivex.rxjava2:rxjava:2.2.2")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.0")
    val AUTO_DISPOSE_VERSION = "0.8.0"
    implementation("com.uber.autodispose:autodispose:$AUTO_DISPOSE_VERSION")
    implementation("com.uber.autodispose:autodispose-android:$AUTO_DISPOSE_VERSION")
    implementation("com.uber.autodispose:autodispose-android-archcomponents:$AUTO_DISPOSE_VERSION")

    // Others
    implementation("com.squareup.picasso:picasso:2.5.2")
    implementation("com.github.nekocode:Meepo:0.3")
    val STATE_VERSION = "1.3.1"
    implementation("com.evernote:android-state:$STATE_VERSION")
    kapt("com.evernote:android-state-processor:$STATE_VERSION")
}
