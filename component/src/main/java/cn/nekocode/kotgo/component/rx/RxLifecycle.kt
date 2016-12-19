package cn.nekocode.kotgo.component.rx

import rx.Observable
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.subjects.BehaviorSubject

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class RxLifecycle {
    enum class Event {
        CREATE, ATTACH, CREATE_VIEW, RESTART, START, RESUME,
        PAUSE, STOP, DESTROY_VIEW, DETACH, DESTROY
    }

    val lifecycleBehavior: BehaviorSubject<Event> = BehaviorSubject.create()

    fun onCreate() { lifecycleBehavior.onNext(Event.CREATE) }
    fun onAttach() { lifecycleBehavior.onNext(Event.ATTACH) }
    fun onCreateView() { lifecycleBehavior.onNext(Event.CREATE_VIEW) }
    fun onRestart() { lifecycleBehavior.onNext(Event.RESTART) }
    fun onStart() { lifecycleBehavior.onNext(Event.START) }
    fun onResume() { lifecycleBehavior.onNext(Event.RESUME) }
    fun onPause() { lifecycleBehavior.onNext(Event.PAUSE) }
    fun onStop() { lifecycleBehavior.onNext(Event.STOP) }
    fun onDestroyView() { lifecycleBehavior.onNext(Event.DESTROY_VIEW) }
    fun onDetach() { lifecycleBehavior.onNext(Event.DETACH) }
    fun onDestroy() { lifecycleBehavior.onNext(Event.DESTROY) }

    interface Impl {
        val lifecycle: RxLifecycle

        fun RxBus.safetySubscribe(subscriber: Subscriber<Any>) {
            RxBus.toObserverable()
                    .safetySubscribe(subscriber)
        }

        fun RxBus.safetySubscribe(onNext: (Any) -> Unit, onError: (Throwable) -> Unit) {
            RxBus.toObserverable()
                    .safetySubscribe(onNext, onError)
        }

        fun <T> RxBus.safetySubscribe(classType: Class<T>, subscriber: Subscriber<T>) {
            RxBus.toObserverable(classType)
                    .safetySubscribe(subscriber)
        }

        fun <T> RxBus.safetySubscribe(classType: Class<T>,
                                      onNext: (T) -> Unit, onError: (Throwable) -> Unit) {
            RxBus.toObserverable(classType)
                    .safetySubscribe(onNext, onError)
        }

        fun <T> Observable<T>.safetySubscribe(subscriber: Subscriber<T>): Subscription {
            return compose(CheckUIDestroiedTransformer<T>(lifecycle.lifecycleBehavior))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber)
        }

        fun <T> Observable<T>.safetySubscribe(onNext: (T) -> Unit, onError: (Throwable) -> Unit): Subscription {
            return compose(CheckUIDestroiedTransformer<T>(lifecycle.lifecycleBehavior))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(onNext, onError)
        }

        fun <T> Observable<T>.safetySubscribe(onNext: (T) -> Unit, onError: (Throwable) -> Unit, onCompleted: () -> Unit): Subscription {
            return compose(CheckUIDestroiedTransformer<T>(lifecycle.lifecycleBehavior))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(onNext, onError, onCompleted)
        }
    }

    private class CheckUIDestroiedTransformer<T>(val eventBehavior: BehaviorSubject<RxLifecycle.Event>) :
            Observable.Transformer<T, T> {
        override fun call(observable: Observable<T>): Observable<T> {
            return observable.takeUntil(
                    eventBehavior.skipWhile {
                        it != RxLifecycle.Event.DESTROY_VIEW &&
                                it != RxLifecycle.Event.DESTROY &&
                                it != RxLifecycle.Event.DETACH
                    }
            )
        }
    }
}