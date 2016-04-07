# README

[![Apache 2.0 License](https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0.html) [![Release](https://jitpack.io/v/nekocode/kotgo.svg)](https://jitpack.io/#nekocode/kotgo) [![Join the chat at https://gitter.im/nekocode/kotgo](https://badges.gitter.im/nekocode/kotgo.svg)](https://gitter.im/nekocode/kotgo?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

## 快速创建项目
你可以使用下面的命令快速创建一个 Kotgo 模板项目。只需要粘贴到命令行中执行就可以了。
```bash
python -c "$(curl -fsSL https://raw.githubusercontent.com/nekocode/kotgo/master/project_creator.py)"
```
当然，你也可以将这个 Python 脚本下载到你本地运行。它依赖 `requests` 库。

## 相关文章
- [**『Android 还可以这样开发』 - 知乎专栏**](http://zhuanlan.zhihu.com/kotandroid)  

### 为什么使用 Kotlin
- [**#前言#**](http://zhuanlan.zhihu.com/kotandroid/20313799)
- [**#kotlin# 你好怪兽**](http://zhuanlan.zhihu.com/kotandroid/20314409)
- [**#kotlin# Activity 之朝花夕拾**](http://zhuanlan.zhihu.com/kotandroid/20349241)

### 为什么使用 MVP
- [**#android# MVP 的尝试**](http://zhuanlan.zhihu.com/kotandroid/20358928)

### 为什么使用 Rx
- [**#android# Everything is a stream**](http://zhuanlan.zhihu.com/kotandroid/20498267)
- [**#kotlin# 小心 Rx 的生命周期**](http://zhuanlan.zhihu.com/kotandroid/20514727)


## 描述
Kotgo 是一个采用 **MVP** 模式进行设计的 Android 应用框架。它使用纯的 **Kotlin** 进行构建。  
![](art/layer.png)

### 包结构
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

### 分层
- **Data Layer：**非传统意义的 **Model** 层，包含 `dto`（Data Transfer Object）、`service`、`model`、`exception` 等。其中 service 包含 `Net` 等不同服务，用于从不同途径获取数据。model 负责处理某个业务对象的业务逻辑，并通过 **dto 或基本类型** 与 Presenter 层进行交互（建议使用 RxJava）。
- **View Layer：**视图层，包括各种 `activity`，`adapter`，`fragment`，`view`。只关注与用户交互，以及视图操作（动画、界面输出、更新等）。
- **Presenter Layer：**控制逻辑层。是**「Model 与 View 层中间的交互控制层」**。

### Kotlin
- **kotlin version: `1.0.1`**

### 依赖库
- anko: **`0.8.3`**
- rxkotlin: **`0.40.1`**
- retrofit: **`2.0.0-beta4`**
- picasso: **`2.5.2`**
- hawk: **`1.22`**
- stetho: **`1.3.1`**

### 截图
感谢 **[gank.io](http://gank.io/)**。Sample App 是在它上面获取美女照片信息的。  
![](art/screenshot.png)


## 使用 Component Library
你可以仅仅使用 kotgo 的 component 库，它提供了很多能让你简单快速地构建一个 MVP 项目的有用的工具。在项目根目录的 build.gradle 添加以下内容：
```gradle
repositories {
    maven { url "https://jitpack.io" }
}
```

在你的子 build.gradle 添加以下依赖：
```gradle
dependencies {
    compile 'com.github.nekocode:kotgo:<lastest-version>'
}
```

### 更灵活的 RxLifecycle！！
它帮助你将 RxJava 的订阅绑定在 Avtivity 或者 Fragment 的生命周期上。它会在 Activity 或者 Fragment 进行销毁的时候终止订阅。更关键的是，他还能在任何地方使用（例如 Presenter 中），比 [RxLifecycle](https://github.com/trello/RxLifecycle) 更加灵活。  
```kotlin
MeiziModel.getMeizis(50, 1).onUI().bindLifecycle(view).subscribe {
    view.refreshMeizis(it)
}
```

### 强大的 RxBus！！
它使用 RxJava 来模拟事件总线，它通过一系列 Kotlin 的语法糖，将订阅 EventBus 变得十分简洁，并且自动绑定了 Avtivity 或者 Fragment 的生命周期，你无需担心任何意外！只需要像下面一样在 `bus` 中订阅事件就行了。  
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

### 不复杂的依赖注入（开发中）
查看 [这里的代码](https://github.com/nekocode/kotgo/blob/master/sample%2Fsrc%2Fmain%2Fjava%2Fcn%2Fnekocode%2Fkotgo%2Fsample%2FApp.kt#L22-34)。

### SingleFragmentActivity
它能帮助你快速创建一个包含单个 Toolbar 和单个 Fragment 的 Activity。你只需要继承 `toolbarLayoutId` 以及 `fragmentClass` 就行了。如果你不需要 Toolbar 的话设置 `toolbarLayoutId` 为 null。
```kotlin
class MainActivity: SingleFragmentActivity<MainFragment>() {
    override var toolbarLayoutId: Int? = R.layout.toolbar
    override val fragmentClass = MainFragment::class.java
}
```


### 其他
它还包括一些其他常用的工具和拓展（例如 [KotterKnife](https://github.com/JakeWharton/kotterknife)）。你可以通过查看 [util 包](component/src/main/java/cn/nekocode/kotgo/component/util) 获得更多的细节。
