# README/[中文文档](/README_CN.md)

[![Release](https://jitpack.io/v/nekocode/kotlin_android_base_framework.svg)](https://jitpack.io/nekocode/kotlin_android_base_framework) [![Join the chat at https://gitter.im/nekocode/kotlin_android_base_framework](https://badges.gitter.im/nekocode/kotlin_android_base_framework.svg)](https://gitter.im/nekocode/kotlin_android_base_framework?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

## Related articles
- [**Zhihu Column**](http://zhuanlan.zhihu.com/kotandroid)  
![](art/logo.png)

## Description
This is an android development framework using **MVP** architecture. It built with both **kotlin** and java.
![](art/layer.png)

### Package structure
```
com.nekocode.baseframework
├─ data
│  ├─ dto
│  ├─ exception
│  ├─ model
│  └─ service
│ 
├─ presentation
│ 
├─ utils
│ 
├─ view
│ 
├─ App.kt
└─ Config.kt
```

### Layer
- **Data Layer:** It likes the traditional **Model Layer**, includes `dto`(Data Transfer Object), `service`,`model`, `exception` or other element directories. It uses the dtos or other meta object to interact with the Presenter Layer.
- **View Layer:** Including `activity`, `adapter`, `fragment`, `view`. Only concerned with the user interaction, as well as view manipulation (animation, interface input, output and update, etc.).
- **Presenter Layer:** Control logic layer. Contains the logic to respond to the events, updates the model (both the business logic and the application data), and alters the state of the view.

### Kotlin
- **kotlin version: `1.0.0`**

### Libraries
- anko: **`0.8.2`**
- rxkotlin: **`0.40.1`**
- retrofit: **`2.0.0-beta4`**
- picasso: **`2.5.2`**
- hawk: **`1.20`**

## Using Component Library
Add the JitPack repository to your build.gradle:
```gradle
repositories {
    maven { url "https://jitpack.io" }
}
```

Add the dependency:
```gradle
dependencies {
    compile 'com.github.nekocode:kotlin_android_base_framework:v0.2'
}
```

Inject to the application:
```kotlin
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Component.inject(this)
    }

}
```

