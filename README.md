# README/[中文文档](/README_CN.md)

[![Release](https://jitpack.io/v/nekocode/kotlin_android_base_framework.svg)](https://jitpack.io/nekocode/kotlin_android_base_framework) [![Join the chat at https://gitter.im/nekocode/kotlin_android_base_framework](https://badges.gitter.im/nekocode/kotlin_android_base_framework.svg)](https://gitter.im/nekocode/kotlin_android_base_framework?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

## Create Project
To create a new android application project using the kotgo template, change the `NewProjectName` and `com.package.path` args to you want in the following command, paste and excute at a Terminal prompt.
```bash
python -c "$(curl -fsSL https://raw.githubusercontent.com/nekocode/kotgo/master/project_creator.py)" NewProjectName com.package.path
```

## Description
**Kotgo** is an android development framework using **MVP** architecture. It built with both **kotlin** and java.
![](art/layer.png)

### Related articles
- [**Zhihu Column**](http://zhuanlan.zhihu.com/kotandroid) 

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
You can only use the kotgo's component library with gradle. Just add the JitPack repository to your project root build.gradle:
```gradle
repositories {
    maven { url "https://jitpack.io" }
}
```

And then add the dependency to build.gradle in your app or module directory:
```gradle
dependencies {
    compile 'com.github.nekocode:kotgo:0.2.1'
}
```

### Some Features
#### SingleFragmentActivity
It helps you fast create an activity with only one single fragment. And it extends from the BaseActivity, you can use the safe message handling functions from it.
```kotlin
class TestActivity : SingleFragmentActivity() {
    override val toolbarLayoutId = R.layout.toolbar
    override var toolbarHeight = 50

    override val fragmentClass = TestFragment::class.java
    override val fragmentBundle by lazy {
        intent.extras
    }

    override fun afterCreate() {
        toolbar.title = "This is a test"
        runDelayed({
            showToast("Hello")
        }, 2000)
    }

    override fun handler(msg: Message) {
    }
}
```

#### Base Presenter
It helps you to binding the activity and fragment lifecycle to RxJava. It terminates all the rx subscriptions when destoring or detaching.
```kotlin
class TestFragment : Fragment(), WeatherPresenter.ViewInterface {
    val weatherPresenter = WeatherPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weatherPresenter.onCreate(arguments)
    }

    override fun onDetach() {
        super.onDetach()
        weatherPresenter.onDetach()
    }
}
```

#### Others
It contains the [KotterKnife](https://github.com/JakeWharton/kotterknife) and some extensions using the kotlin syntactic sugar. Check the `Sugar.kt` for more details.

