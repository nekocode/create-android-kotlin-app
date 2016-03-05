# README/[中文文档](/README_CN.md)

[![Apache 2.0 License](https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0.html) [![Release](https://jitpack.io/v/nekocode/kotgo.svg)](https://jitpack.io/#nekocode/kotgo) [![Join the chat at https://gitter.im/nekocode/kotgo](https://badges.gitter.im/nekocode/kotgo.svg)](https://gitter.im/nekocode/kotgo?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

## Create Project
You can fast create a new kotgo template project by using the following command. Just paste and excute it at a Terminal prompt. Have fun!
```bash
python -c "$(curl -fsSL https://raw.githubusercontent.com/nekocode/kotgo/master/project_creator.py)"
```
Of course, you can also download the python script to your local disk to run it.

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
│  └─ screen_one
│     ├─ Presenter.kt
│     └─ Activity.kt
│
├─ App.kt
└─ Config.kt
```

### Layer
- **Data Layer:** It likes the traditional **Model Layer**, includes `dto`(Data Transfer Object), `service`, `model`, `exception` or other element directories. It uses the dtos or other meta object to interact with the Presenter Layer.
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
- otto: **`1.3.8`**

### Screenshots
Thanks to **[gank.io](http://gank.io/)**. The sample App fetchs beautiful girl photos' information from it.  
![](art/screenshot.png)

## Use Component Library
You can only use the kotgo's component library with gradle. Just add the JitPack repository to your project root build.gradle:
```gradle
repositories {
    maven { url "https://jitpack.io" }
}
```

And then add the dependency to build.gradle in your app or module directory:
```gradle
dependencies {
    compile 'com.github.nekocode:kotgo:lastest-version'
}
```

### Some Features
##### SingleFragmentActivity
It helps you fast create an activity with only one single fragment. And it extends from the BaseActivity, you can also use the safe message handling functions from the BaseActivity.
```kotlin
class MainActivity: SingleFragmentActivity() {
    override val toolbarLayoutId = R.layout.toolbar

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

##### Base Presenter
It helps you to binding the activity and fragment lifecycle to RxJava. It terminates all the rx subscriptions when the page is destoring or detaching.
```kotlin
class MainFragment: BaseFragment(), MeiziPresenter.ViewInterface {
    override val layoutId: Int = R.layout.fragment_main
    val meiziPresenter by CheckLifeCycle(MeiziPresenter(this))
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        meiziPresenter.getMeizis()
    }
}
```

##### Others
It also contains the [KotterKnife](https://github.com/JakeWharton/kotterknife) and some tools and extensions using the kotlin syntactic sugar. Check the `component` for more details.
