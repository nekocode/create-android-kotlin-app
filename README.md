# README

[![Apache 2.0 License](https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0.html)

This project template makes it easy to get started with [**Kotlin**](https://kotlinlang.org) in Android development. It provides a python script that can generate a new Android project using Kotlin/MVP/ReactiveX. Just paste and execute the following command at a terminal prompt (it depends on the [`requests`](http://docs.python-requests.org/en/master/user/install/) lib).

```bash
python -c "$(curl -fsSL https://raw.githubusercontent.com/nekocode/Kotlin-Android-Template/master/project_generator.py)"
```

### Project Structure

This project demonstrates a basic [Model-View-Presenter](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter) (MVP) architecture. It uses headless Fragment to implement Presenter because the Fragment provides lifecycle callbacks and can be recreated automatically by the FragmentManager. And the project entirely separates the business logic code into a submodule so you can maintain and test them separately.

![](art/layer.png)

### Contribution

Feel free to contribute to this project by either raising issues or handing in pull requests.