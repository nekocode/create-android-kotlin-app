# README/[中文文档](/README_CN.md)

[![Apache 2.0 License](https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0.html) [![Release](https://jitpack.io/v/nekocode/kotgo.svg)](https://jitpack.io/#nekocode/kotgo) [![Join the chat at https://gitter.im/nekocode/kotgo](https://badges.gitter.im/nekocode/kotgo.svg)](https://gitter.im/nekocode/kotgo?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

## Create Template Project
You can create a new Kotgo template project fast by using the following command. Just paste and execute it at a terminal prompt. Have fun!
```bash
python -c "$(curl -fsSL https://raw.githubusercontent.com/nekocode/kotgo/master/project_creator.py)"
```
Of course, you can also download the python script to your local disk to run it. It depends on the `requests` lib.

## Description
**Kotgo** is an android development framework using **MVP** architecture. It is built with pure **Kotlin**.  
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
├─ ui
│  └─ screen_one
│     ├─ Presenter.kt
│     └─ Activity.kt
│
└─ App.kt
```

### Layer
- **Data Layer:** It likes the traditional **Model Layer**, includes `dto`(Data Transfer Object), `service`, `model`, `exception` or other element directories. It uses the dtos or other meta object to interact with the Presenter Layer.
- **View Layer:** Including `activity`, `adapter`, `fragment`, `view`. Only concerned with the user interaction, as well as view manipulation (animation, interface input, output and update, etc.).
- **Presenter Layer:** Control logic layer. Contains the logic to respond to the events, updates the model (both the business logic and the application data), and alters the state of the view.

### Kotlin
- **kotlin version: `1.0.1`**

### Libraries
- anko: **`0.8.3`**
- rxkotlin: **`0.40.1`**
- retrofit: **`2.0.0-beta4`**
- picasso: **`2.5.2`**
- hawk: **`1.22`**
- stetho: **`1.3.1`**

### Screenshots
Thanks to **[gank.io](http://gank.io/)**. The sample App fetchs beautiful girl photos' information from it.  
![](art/screenshot.png)

Another better sample: **[Check this](https://github.com/nekocode/murmur)**

## Use Component Library
You can only use the kotgo's component library with gradle. It provides many useful tools to help you to build a MVP project fast and simply. Just add the JitPack repository to your root build.gradle:
```gradle
repositories {
    maven { url "https://jitpack.io" }
}
```

And then add the dependency to your sub build.gradle:
```gradle
dependencies {
    compile 'com.github.nekocode:kotgo:<lastest-version>'
}
```

### More Flexible RxLifecycle!! 
It helps you to bind the RxJava subscriptions into the lifecycle of Activity and Fragment. It will terminate the RxJava subscription when the activity or fragment is destorying or detaching. And the most importent thing is that it can be used anywhere (such in Prensenter), It's more flexible then the [RxLifecycle](https://github.com/trello/RxLifecycle).
```kotlin
MeiziModel.getMeizis(50, 1).onUI().bindLifecycle(view).subscribe {
    view.refreshMeizis(it)
}
```

### Powerful RxBus!!
It simulates the Event Bus by RxJava. It uses many syntax sugar of Kotlin to make subscribing the Bus's events easier. And it is also binded into the lifecyle of Activity and Fragment automatically, you can just subscribe events in the `bus` block without worrying about any accidents! 
```kotlin
bus {
    subscribe(String::class.java) {
        showToast(it)
    }
    
    subscribe(Int::class.java) {
        showToast(it.toString())
    }
}
```

### The Prsenter Inherited From Fragment!!
**[Using the Fragment to implement Presenter maybe is one of the best ways!](http://zhuanlan.zhihu.com/p/20656755?refer=kotandroid)** Now we can move the logic which depended on Activity's lifecycle to Presenter. And we can also use `setRetainInstance(true)` to solve the screen rotation problem.

It look like this:
```kotlin
class MeiziPresenter(): BasePresenter(), Contract.Presenter {
    var view: Contract.View? = null

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        view = getParent() as Contract.View
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MeiziModel.getMeizis(50, 1).onUI().bindLifecycle(this).subscribe {
            view?.refreshMeizis(it)
        }
    }
}
```


### Simple Dependency Injection
Look at [these code](https://github.com/nekocode/kotgo/blob/master/sample%2Fsrc%2Fmain%2Fjava%2Fcn%2Fnekocode%2Fkotgo%2Fsample%2FApp.kt#L22-34).

We can inherit the Dependency class for storing some denpendencies providing.
```kotlin
object TestDep : Dependency() {
    override fun createDependencies() {
        bindSingleton<Int> {
            args ->
            args[0] as Int
        }
    }
}
```
And then use the following code to inject dependency.
```kotlin
val int = TestDep.inject<Int>
```

### SingleFragmentActivity
It helps you create an Activity with one single Toolbar and one single Fragment fast. You just need to inherit the `toolbarLayoutId` and `fragmentClass` properties. And set the `toolbarLayoutId` to null if you don't need Toolbar.
```kotlin
class MainActivity: SingleFragmentActivity<MainFragment>() {
    override var toolbarLayoutId: Int? = R.layout.toolbar
    override val fragmentClass = MainFragment::class.java
}
```


### Others
It also contains some other useful tools and extensions (such as [KotterKnife](https://github.com/JakeWharton/kotterknife)). Check the [util package](component/src/main/java/cn/nekocode/kotgo/component/util) for more details.
