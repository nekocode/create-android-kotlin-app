package cn.nekocode.baseframework.presentation

import rx.Observable
import rx.subjects.BehaviorSubject

/**
 * Created by nekocode on 2015/11/20.
 * inspired by: https://github.com/trello/RxLifecycle
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

    val eventBehavior: BehaviorSubject<Event> = BehaviorSubject.create()

    open fun onCreate() {
        eventBehavior.onNext(Event.CREATE)
    }

    open fun onStart() {
        eventBehavior.onNext(Event.START)
    }

    open fun onResume() {
        eventBehavior.onNext(Event.RESUME)
    }

    open fun onPause() {
        eventBehavior.onNext(Event.PAUSE)
    }

    open fun onStop() {
        eventBehavior.onNext(Event.STOP)
    }

    open fun onDestory() {
        eventBehavior.onNext(Event.DESTROY)
    }

    open fun onAttach() {
        eventBehavior.onNext(Event.ATTACH)
    }

    open fun onCreateView() {
        eventBehavior.onNext(Event.CREATE_VIEW)
    }

    open fun onDestoryView() {
        eventBehavior.onNext(Event.DESTROY_VIEW)
    }

    open fun onDetach() {
        eventBehavior.onNext(Event.DETACH)
    }

    class NormalCheckLifeCycleTransformer<T>(val eventBehavior: BehaviorSubject<Presenter.Event>):
            Observable.Transformer<T, T> {

        override fun call(observable: Observable<T>): Observable<T> {
            return observable.takeUntil(
                    eventBehavior.skipWhile {
                        it != Presenter.Event.DESTROY && it != Presenter.Event.DETACH
                    }
            )
        }
    }

    fun <T> rx.Observable<T>.on(presenter: Presenter):
            Observable<T> {

        return observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .compose(NormalCheckLifeCycleTransformer<T>(presenter.eventBehavior))
    }
}