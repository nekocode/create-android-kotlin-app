![](art/logo.png)

### Description
这是一个采用 **MVP** 模式进行设计的 Android DEMO Application。它使用 **kotlin** 和 java 混合构建。
![](art/layer.png)

它的一个包结构（Package structure）如下：
```
com.nekocode.baseframework
├─ data
│  ├─ net
│  └─ disk
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

它包含的几个层次：
- **Data Layer：**非传统意义的 **Model** 层，包含 `bean` 和 `repo`。其中 repo 包含 `net repo` 和 `disk repo` 等仓库，用于从不同途径获取数据。
- **View Layer：**视图层，包括各种 `activity`，`adapter`，`fragment`，`view`。只关注与用户交互，以及视图操作（动画、输出、更新等）。
- **Presenter Layer：**程序逻辑层。将业务逻辑从 Activity、Fragment 中剥离并抽象成 `Presenter `。负责**「View 与 Model 层之间的逻辑交互」**。

---

### Related articles
http://zhuanlan.zhihu.com/kotandroid/20313799


### Kotlin
- **kotlin version: `1.0.0-beta-2423`**


### Libraries
- anko: **`0.7.2`**
- rxkotlin: **`0.24.100`**
- retrofit: **`2.0.0-beta1`**
- picasso: **`2.5.2`**
- hawk: **`1.20`**

