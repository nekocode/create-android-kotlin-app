# README

[![Apache 2.0 License](https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0.html) [![Release](https://jitpack.io/v/nekocode/kotgo.svg)](https://jitpack.io/#nekocode/kotgo) [![Join the chat at https://gitter.im/nekocode/kotgo](https://badges.gitter.im/nekocode/kotgo.svg)](https://gitter.im/nekocode/kotgo?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

## 快速创建模板项目
你可以使用下面的命令快速创建一个 Kotgo 模板项目。只需要粘贴到命令行中执行就可以了。
```bash
python -c "$(curl -fsSL https://raw.githubusercontent.com/nekocode/kotgo/master/project_creator.py)"
```
当然，你也可以将这个 Python 脚本下载到你本地运行。它依赖 `requests` 库。

## 相关文章
- [**『Android 还可以这样开发』 - 知乎专栏**](http://zhuanlan.zhihu.com/kotandroid)  

### 为什么使用 Kotlin
- [**#前言#**](http://zhuanlan.zhihu.com/kotandroid/20313799)
- [**#Kotlin# 你好怪兽**](http://zhuanlan.zhihu.com/kotandroid/20314409)
- [**#Kotlin# Activity 之朝花夕拾**](http://zhuanlan.zhihu.com/kotandroid/20349241)

### 为什么使用 MVP
- [**#Android# MVP 的尝试**](http://zhuanlan.zhihu.com/kotandroid/20358928)

### 为什么使用 Rx
- [**#Android# Everything is a stream**](http://zhuanlan.zhihu.com/kotandroid/20498267)
- [**#Kotlin# 小心 Rx 的生命周期**](http://zhuanlan.zhihu.com/kotandroid/20514727)


## 描述
Kotgo 是一个采用 **MVP** 模式进行设计的 Android 应用框架。它使用纯的 **Kotlin** 进行构建。  
![](art/layer.png)

### 包结构
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

### 依赖库
- anko: **`0.9`**
- rxkotlin: **`0.60.0`**
- retrofit: **`2.1.0`**
- picasso: **`2.5.2`**
- hawk: **`2.0.0-Alpha`**
- paperparcel: **`1.0.0`**

### 截图
感谢 **[gank.io](http://gank.io/)**。Sample App 是在它上面获取照片的。

![](art/screenshot.png)

另外一个更完善的例子：**[查看这里](https://github.com/nekocode/murmur)**

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
MeiziRepo.getMeizis(50, 1).bindLifecycle(view).onUI {
    view.refreshMeizis(it)
}
```

### 强大的 RxBus！！
它使用 RxJava 来模拟事件总线，它通过一系列 Kotlin 的语法糖，将订阅 EventBus 变得十分简洁，并且自动绑定了 Avtivity 或者 Fragment 的生命周期，你无需担心任何产生泄漏！只需要像下面一样在任意地方发送，并在实现了 `RxLifecycle.Impl` 的类中订阅事件就行了。  
```kotlin
RxBus.send("Success")
RxBus.subscribe(String::class.java) { showToast(it) }
```

### 继承自 Fragment 的 Presenter！！
**[使用 Fragment 来实现 Presenter 是最好的方式之一！](http://zhuanlan.zhihu.com/p/20656755?refer=kotandroid)** 我们现在能将原先在 Activity 中的各种依赖 Activity 生命周期的逻辑迁移到 Presenter 中了。

它看起来是这样的：
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

### 支持单个 Activity 多个 Fragment 架构！！
你可以借助 Component 库提供的 `FragmentActivity` 来构建只有单个 Activity 的应用，它非常适合页面层次结构不深的应用。

FragmentActivity 提供了以下的一些方法来帮助你管理 Fragment 栈。
```kotlin
push()
pushForResult()
popAll()
popUntil()
popTop()
startActivityForResult()
```

我们还处理了更多的细节，详情可以查看 `FragmentActivity.kt`。