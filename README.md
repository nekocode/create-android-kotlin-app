# README

This project shows how to build an android application with kotlin and some useful libraries. It imports the [Meepo](https://github.com/nekocode/Meepo) library to create activity & broadcast routers. And use kotlin language sugars to make their usages simpler. For example:

```kotlin
// Goto a new activity
activityRouter().gotoXxxActivity(this)

// Broadcast a action
broadcastRouter().tellSomeSth(this)

// Receiver a broadcast
registerLocalReceiver({ _, intent ->
    val action = (intent ?: return@registerLocalReceiver).action
            ?: return@registerLocalReceiver
    when (action) {
        "ACTION_XXX" -> {
            // Do sth
        }
    }
}, "ACTION_XXX", "ACTION_XXX2")
```

It also imports the [Retrofit](https://github.com/square/retrofit) library to make network requests:

```kotlin
gankIoService().picApi.getMeiziPics(1, 0)
    // ...
```

And it splits models' definition, file and network operations and other classes unrelated to ui into a submodule. And make them easier to test. Such as:

```kotlin
class GankIoServiceTest {
    private val gankIoService = GankIoService(OkHttpClient(), Gson())

    @Test
    fun getMeiziPics() {
        gankIoService.picApi.getMeiziPics(10, 0)
                .test()
                .assertNoErrors()
                .assertValue { response ->
                    !response.error && response.results.size == 10
                }
    }
}
```

It also use language sugars to make [AutoDispose](https://github.com/uber/AutoDispose)'s usage more convenient:

```kotlin
// ... Rx stream
        .autoDispose()
        .subscribe()
```

For more details, you can look at the code directly.
