package cn.nekocode.kotgo.component.rx

import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.subjects.BehaviorSubject

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class RxLifecycle {
    enum class Event {
        DESTROY
    }

    val eventBehavior: BehaviorSubject<Event> = BehaviorSubject.create()

    fun onDestroy() {
        eventBehavior.onNext(Event.DESTROY)
    }

    interface Impl {
        val lifecycle: RxLifecycle

        fun <T : Any> RxBus.subscribe(classType: Class<T>, subscriber: Subscriber<T>) {
            RxBus.toObserverable()
                    .bindLifecycle(this@Impl)
                    .filterByType(classType)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber)
        }

        fun <T> RxBus.subscribe(classType: Class<T>, onNext: (T) -> Unit) {
            RxBus.toObserverable()
                    .bindLifecycle(this@Impl)
                    .filterByType(classType)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(onNext)
        }

        private fun <T> Observable<Any>.filterByType(classType: Class<T>): Observable<T> {
            return this.filter { classType.isInstance(it) } as Observable<T>
        }
    }
}

class CheckLifeCycleTransformer<T>(val eventBehavior: BehaviorSubject<RxLifecycle.Event>) :
        Observable.Transformer<T, T> {
    override fun call(observable: Observable<T>): Observable<T> {
        return observable.takeUntil(
                eventBehavior.skipWhile {
                    it != RxLifecycle.Event.DESTROY
                }
        )
    }
}

fun <T> Observable<T>.bindLifecycle(impl: RxLifecycle.Impl):
        Observable<T> {
    return compose(CheckLifeCycleTransformer<T>(impl.lifecycle.eventBehavior))
}