# README

## Related articles
- [**『Android 还可以这样开发』 - 知乎专栏**](http://zhuanlan.zhihu.com/kotandroid)  
![](art/logo.png)

### Why kotlin
- [**#前言#**](http://zhuanlan.zhihu.com/kotandroid/20313799)
- [**#kotlin# 你好怪兽**](http://zhuanlan.zhihu.com/kotandroid/20314409)
- [**#kotlin# Activity 之朝花夕拾**](http://zhuanlan.zhihu.com/kotandroid/20349241)

### Why MVP
- [**#android# MVP 的尝试**](http://zhuanlan.zhihu.com/kotandroid/20358928)

### Why Rx
- [**#android# Everything is a stream**](http://zhuanlan.zhihu.com/kotandroid/20498267)
- [**#kotlin# 小心 Rx 的生命周期**](http://zhuanlan.zhihu.com/kotandroid/20514727)


## Description
这是一个采用 **MVP** 模式进行设计的 Android DEMO Application。它使用 **kotlin** 和 java 混合构建。
![](art/layer.png)

### Package structure
```
com.nekocode.baseframework
├─ data
│  ├─ dto
│  ├─ model
│  └─ service
│      ├─ Local.kt
│      └─ Net.kt
│ 
├─ presenter
│ 
├─ ui
│  ├─ activity
│  ├─ adapter
│  ├─ fragment
│  └─ view
│ 
├─ utils
│ 
├─ App.kt
└─ Config.kt
```

### Layer
- **Data Layer：**非传统意义的 **Model** 层，包含 `dto`（Data Transfer Object）、`service` 和 `model`。其中 service 包含 `Net` 和 `Local` 等不同服务，用于从不同途径获取数据。model 负责处理某个业务对象的业务逻辑，并通过 **dto 或基本类型** 与 Presenter 层进行交互。
- **View Layer：**视图层，包括各种 `activity`，`adapter`，`fragment`，`view`。只关注与用户交互，以及视图操作（动画、界面输出、更新等）。
- **Presenter Layer：**控制逻辑层。是**「Model 与 View 层中间的交互控制层」**。



### Kotlin
- **kotlin version: `1.0.0-beta-4584`**


### Libraries
- anko: **`0.7.2`**
- rxkotlin: **`0.30.1`**
- retrofit: **`2.0.0-beta3`**
- picasso: **`2.5.2`**
- hawk: **`1.20`**

