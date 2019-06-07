import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("androidx.navigation.safeargs")
}

androidExtensions {
    isExperimental = true
    features = setOf("views", "parcelize")
}

android {
    compileSdkVersion(28)
    defaultConfig {
        targetSdkVersion(28)
        minSdkVersion(21)
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":backend"))

    // Kotlin
    implementation(kotlin("stdlib-jdk7", KotlinCompilerVersion.VERSION))

    // Androidx
    implementation("androidx.appcompat:appcompat:1.1.0-alpha05")
    implementation("androidx.core:core-ktx:1.0.2")
    implementation("androidx.localbroadcastmanager:localbroadcastmanager:1.1.0-alpha01")
    implementation("androidx.recyclerview:recyclerview:1.1.0-alpha05")
    implementation("androidx.constraintlayout:constraintlayout:2.0.0-beta1")

    // Navigation
    implementation("android.arch.navigation:navigation-fragment-ktx:1.0.0")
    implementation("android.arch.navigation:navigation-ui-ktx:1.0.0")

    // ReactiveX
    implementation("io.reactivex.rxjava2:rxjava:2.2.6")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation("com.uber.autodispose:autodispose:0.8.0")
    implementation("com.uber.autodispose:autodispose-android:0.8.0")
    implementation("com.uber.autodispose:autodispose-android-archcomponents:0.8.0")

    // Others
    implementation("com.squareup.picasso:picasso:2.5.2")
    implementation("com.github.nekocode:Meepo:0.3")
    implementation("com.evernote:android-state:1.3.1")
    kapt("com.evernote:android-state-processor:1.3.1")

    // For debugging
    debugImplementation("com.facebook.flipper:flipper:0.21.1")
    debugImplementation("com.facebook.soloader:soloader:0.6.0")
    releaseImplementation("com.facebook.flipper:flipper-noop:0.21.1")
}
