package cn.nekocode.baseframework.presenter.component

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

    final fun create() {
        eventBehavior.onNext(Event.CREATE)
    }

    final fun start() {
        eventBehavior.onNext(Event.START)
    }

    final fun resume() {
        eventBehavior.onNext(Event.RESUME)
    }

    final fun pause() {
        eventBehavior.onNext(Event.PAUSE)
    }

    final fun stop() {
        eventBehavior.onNext(Event.STOP)
    }

    final fun destory() {
        eventBehavior.onNext(Event.DESTROY)
    }

    final fun attach() {
        eventBehavior.onNext(Event.ATTACH)
    }

    final fun createView() {
        eventBehavior.onNext(Event.CREATE_VIEW)
    }

    final fun destoryView() {
        eventBehavior.onNext(Event.DESTROY_VIEW)
    }

    final fun detach() {
        eventBehavior.onNext(Event.DETACH)
    }
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

