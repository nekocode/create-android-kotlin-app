buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.1.2")
        classpath(kotlin("gradle-plugin", version = "1.4.30"))
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.3")
        classpath("com.akaita.android:easylauncher:1.3.1")
    }
}

allprojects {
    repositories {
        google()
        maven { url = uri("https://jitpack.io") }
        jcenter()
    }
}

tasks.register("clean", Delete::class.java, Action<Delete> {
    delete(rootProject.buildDir)
})
