# README/[中文文档](/README_CN.md)

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

