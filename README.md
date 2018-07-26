# README

This project shows how to build an android application with kotlin and some other useful libraries. It imports the [Meepo](https://github.com/nekocode/Meepo) library to create activity & broadcast routers. And use kotlin language sugars to make their usages simpler. For example:

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

Sending a network request is also simple:

```kotlin
gankIoService().picApi.getMeiziPics(1, 0)
    // ...
```

And it splits network operations into a submodule. And make tests for them. Such as:

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

In addition, it creates some extention methods for [AutoDispose](https://github.com/uber/AutoDispose) so that you can automatically dispose your rx stream more conveniently:

```kotlin
// ... Rx stream
        .autoDispose()
        .subscribe()
```

For more details, you can look at the code directly.
