package cn.nekocode.kotgo.component.presentation

import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.subjects.BehaviorSubject
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Created by nekocode on 2015/11/20.
 * Inspired by: https://github.com/trello/RxLifecycle
 */
open class Presenter {
    enum class Event {
        // Activity Events
        CREATE,
        START,
        RESUME,
        PAUSE,
        STOP,
        DESTROY,

        // Fragment Events
        ATTACH,
        CREATE_VIEW,
        DESTROY_VIEW,
        DETACH
    }

    private val eventBehavior: BehaviorSubject<Event> = BehaviorSubject.create()

    final fun onDestory() {
        eventBehavior.onNext(Event.DESTROY)
    }

    final fun onDetach() {
        eventBehavior.onNext(Event.DETACH)
    }

    private class CheckLifeCycleTransformer<T>(val eventBehavior: BehaviorSubject<Event>):
            Observable.Transformer<T, T> {

        override fun call(observable: Observable<T>): Observable<T> {
            return observable.takeUntil(
                    eventBehavior.skipWhile {
                        it != Event.DESTROY && it != Event.DETACH
                    }
            )
        }
    }

    fun <T> Observable<T>.on(presenter: Presenter):
            Observable<T> {

        return observeOn(AndroidSchedulers.mainThread())
                .compose(CheckLifeCycleTransformer<T>(presenter.eventBehavior))
    }

    class bindLifeCycle<E: Presenter>(private val presenter: E): ReadOnlyProperty<InLifeCyclePresenters, E> {
        override fun getValue(thisRef: InLifeCyclePresenters, property: KProperty<*>): E {
            thisRef.presenters.add(presenter)
            return presenter
        }
    }
}