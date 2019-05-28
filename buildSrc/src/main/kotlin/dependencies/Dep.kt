package dependencies

@Suppress("unused")
object Dep {
    object Test {
        const val junit = "junit:junit:4.12"
    }

    object Support {
        private const val version = "28.0.0"
        const val appcompat = "com.android.support:appcompat-v7:$version"
        const val recyclerview = "com.android.support:recyclerview-v7:$version"
        const val annotations = "com.android.support:support-annotations:$version"
        const val constraint = "com.android.support.constraint:constraint-layout:1.1.3"
    }

    object Kotlin {
        const val version = BuildConfig.KOTLIN_VERSION
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$version"
    }

    object Rx {
        const val java = "io.reactivex.rxjava2:rxjava:2.2.2"
        const val android = "io.reactivex.rxjava2:rxandroid:2.1.0"

        private const val disposeVersion = "0.8.0"
        const val dispose = "com.uber.autodispose:autodispose:$disposeVersion"
        const val disposeAndroid = "com.uber.autodispose:autodispose-android:$disposeVersion"
        const val disposeAndroidArch = "com.uber.autodispose:autodispose-android-archcomponents:$disposeVersion"
    }

    object Meepo {
        const val meepo = "com.github.nekocode:Meepo:0.3"
    }

    object AndroidState {
        private const val version = "1.3.1"
        const val state = "com.evernote:android-state:$version"
        const val processor = "com.evernote:android-state-processor:$version"
    }

    object Picasso {
        const val picasso = "com.squareup.picasso:picasso:2.5.2"
    }

    object OkHttp {
        const val okhttp = "com.squareup.okhttp3:okhttp:3.14.1"
    }

    object Retrofit {
        private const val version = "2.5.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val gsonConverter = "com.squareup.retrofit2:converter-gson:$version"
        const val rxAdapter = "com.squareup.retrofit2:adapter-rxjava2:$version"
    }

    object Gson {
        const val gson = "com.google.code.gson:gson:2.8.4"
    }
}