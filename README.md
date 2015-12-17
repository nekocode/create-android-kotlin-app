# README

## Related articles
- [**『Android 还可以这样开发』 - 知乎专栏**](http://zhuanlan.zhihu.com/kotandroid)  
![](art/logo.png)

## Description
这是一个采用 **MVP** 模式进行设计的 Android DEMO Application。它使用 **kotlin** 和 java 混合构建。
![](art/layer.png)

### Package structure
```
com.nekocode.baseframework
├─ data
│  ├─ net
│  └─ local
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
- **Data Layer：**非传统意义的 **Model** 层，包含 `bean` 和 `repo`。其中 repo 包含 `net repo` 和 `local repo` 等仓库，用于从不同途径获取数据。
- **View Layer：**视图层，包括各种 `activity`，`adapter`，`fragment`，`view`。只关注与用户交互，以及视图操作（动画、界面输出、更新等）。
- **Presenter Layer：**程序逻辑层。将逻辑从 Activity、Fragment 中剥离并抽象成 `Presenter `。负责**「View 与 Model 层之间的控制与交互」**。



### Kotlin
- **kotlin version: `1.0.0-beta-3595`**


### Libraries
- anko: **`0.7.2`**
- rxkotlin: **`0.30.1`**
- retrofit: **`2.0.0-beta1`**
- picasso: **`2.5.2`**
- hawk: **`1.20`**

