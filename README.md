# README

This is an android application template project built with kotlin language and some useful libraries. It provides a creator script to quickly create an project from template.

## Creating project

Make sure you have installed Python 3 and [requests](https://pypi.org/project/requests/) library before proceeding. And then paste the following command at a terminal, replace the `PROJECT_NAME` and `APP_PACKAGE_NAME` and execute it:

```sh
python3 -c \
"$(curl -fsSL https://raw.githubusercontent.com/nekocode/create-android-kotlin-app/master/create-android-kotlin-app.py)" \
PROJECT_NAME \
APP_PACKAGE_NAME
```

## What is included

The template project imports the [Meepo](https://github.com/nekocode/Meepo) library to create activity & broadcast routers. And use kotlin language sugars to make their usages simpler. For example:

```kotlin
// Navigate to a new activity
activityRouter.gotoXxxActivity(this)

// Broadcast something
broadcastRouter.tellSomeSth(this)

// Register broadcast receiver
registerLocalReceiver({ _, intent ->
    val action = (intent ?: return@registerLocalReceiver).action
            ?: return@registerLocalReceiver
    when (action) {
        BroadcastRouter.Action1 -> {
            // Do sth
        }
    }
}, BroadcastRouter.Action1, BroadcastRouter.Action2)
```

Sending a network request in this project is also simple:

```kotlin
gankIoService.picApi.getMeiziPics(1, 0)
    // ...
```

In addition, it creates some extention methods for [AutoDispose](https://github.com/uber/AutoDispose) so that you can automatically dispose your rx stream more conveniently:

```kotlin
// ... Rx stream
        .autoDispose()
        .subscribe()
```

For more details, you can look at the code of template directly.
