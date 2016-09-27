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
│  ├─ DO
│  ├─ repo
│  └─ service
│ 
├─ ui
│  └─ screen_one
│     ├─ Presenter.kt
│     └─ Activity.kt
│
└─ App.kt
```

### Kotlin
- **kotlin version: `1.0.4`**

### Dependencies
- anko: **`0.9`**
- rxkotlin: **`0.60.0`**
- retrofit: **`2.1.0`**
- picasso: **`2.5.2`**
- hawk: **`2.0.0-Alpha`**
- paperparcel: **`1.0.0`**

### Screenshots
Thanks to **[gank.io](http://gank.io/)**. The sample app fetchs photos from it.

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
MeiziRepo.getMeizis(50, 1).bindLifecycle(view).onUI {
    view.refreshMeizis(it)
}
```

### Powerful RxBus!!
It simulates the Event Bus by RxJava. It uses many syntax sugar of Kotlin to make subscribing the Bus's events easier. And it is also binded into the lifecyle of Activity and Fragment automatically. You can just send events everywhere. And then subscribe events in the class that implements from `RxLifecycle.Impl` without worrying about casuing any leaks!  
```kotlin
RxBus.send("Success")
RxBus.subscribe(String::class.java) { showToast(it) }
```

### The Prsenter Inherited From Fragment!!
**[Using the Fragment to implement Presenter maybe is one of the best ways!](http://zhuanlan.zhihu.com/p/20656755?refer=kotandroid)** Now we can move the logic which depended on Activity's lifecycle to Presenter.

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
        // Do something
    }
}
```

### Supporting for Single Activity Multiple Fragment Architecture!!
You can build applications that have only one activity with the help of the `FragmentActivity` provided by the Component Library.

It provides following functions for helping you manage the fragment stack.
```kotlin
push()
pushForResult()
popAll()
popUntil()
popTop()
startActivityForResult()
```

We also deal with more details, look at the `FragmentActivity.kt`.