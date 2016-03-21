package cn.nekocode.kotgo.component.rx

import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.subjects.PublishSubject
import rx.subjects.SerializedSubject
import rx.subjects.Subject

/**
 * Created by nekocode on 16/3/6.
 */
object RxBus {
    private val bus: Subject<Any, Any> = SerializedSubject(PublishSubject.create())

    fun send(o: Any) {
        bus.onNext(o)
    }

    fun toObserverable(): Observable<Any> {
        return bus
    }
}


class Bus(val impl: RxLifecycle.Impl) {
    fun <T: Any> subscribe(classType: Class<T>, subscriber: Subscriber<T>) {
        RxBus.toObserverable()
                .bindLifecycle(impl)
                .filterByType(classType)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber)
    }

    fun <T> subscribe(classType: Class<T>, onNext: (T)->Unit) {
        RxBus.toObserverable()
                .bindLifecycle(impl)
                .filterByType(classType)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext)
    }

    private fun <T> Observable<Any>.filterByType(classType: Class<T>): Observable<T> {
        return this.filter {
            if(!classType.isInstance(it)) {
                return@filter false
            }
            true
        } as Observable<T>
    }
}


fun RxLifecycle.Impl.bus(init: Bus.() -> Unit): Bus {
    val bus = Bus(this)     // create the receiver object
    bus.init()              // pass the receiver object to the lambda
    return bus
}
