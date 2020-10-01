# Simple Android Kotlin Project

This is an android application template project built with kotlin language and some useful libraries. It provides a creator script to quickly create an project from template.

## Creating project

Make sure you have installed Python 3 and [requests](https://pypi.org/project/requests/) library before proceeding. And then paste the following command at a terminal, replace the `PROJECT_NAME` and `APP_PACKAGE_NAME` and execute it:

```sh
python3 -c \
"$(curl -fsSL https://raw.githubusercontent.com/nekocode/create-android-kotlin-app/master/create-android-kotlin-app.py)" \
PROJECT_NAME \
APP_PACKAGE_NAME
```

## What is included in project

This template project includes some of the latest features in Android development:

- Uses kotlin completely (includes gradle build scripts)
- Uses AndroidX & Android Architecture Components (includes Navigation, ViewModel, LiveData)
- Uses some powerful generic libraries like RxKotlin, Dagger2, etc
- Includes some useful features & extensions:
  - Provides convenient way to inject dependencies to Activity, Fragment & ViewModel
  - Provides extension methods `autoDisposable()` for auto disposing rx streams in Activity, Fragment & ViewModel

For more details, you can check the source code directly.
