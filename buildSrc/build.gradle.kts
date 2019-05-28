plugins {
    `kotlin-dsl`
    id("idea")
    id("de.fuerstenau.buildconfig").version("1.1.8")
}

val kotlinVersion = "1.3.31"
buildConfig {
    packageName = "dependencies"
    charset = "UTF-8"

    buildConfigField("String", "KOTLIN_VERSION", kotlinVersion)
}

repositories {
    google()
    jcenter()
}

dependencies {
    api("com.android.tools.build:gradle:3.4.1")
    api("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
}

