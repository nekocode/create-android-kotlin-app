package cn.nekocode.kotgo.component.presentation

import cn.nekocode.kotgo.component.util.LifecycleContainer
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.subjects.BehaviorSubject
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Created by nekocode on 2015/11/20.
 * Inspired by: https://github.com/trello/RxLifecycle
 */
open class Presenter(val lifeCycle: LifecycleContainer)