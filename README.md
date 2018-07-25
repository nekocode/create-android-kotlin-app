# README

This project shows how to build an android application with kotlin and some useful libraries. It intergates the [Meepo](https://github.com/nekocode/Meepo) library to create activity & broadcast routers. And use kotlin language sugars to make their usages simpler. For example:

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

It intergates the [Retrofit](https://github.com/square/retrofit) library to help making http requests:

```kotlin
gankIoService().picApi.getMeiziPics(1, 0)
    // ...
```

And it splits the backend logic (file and network operations that not relate to ui) into a submodule. And make them easier to test:

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

Please reference the code to see more details.
