package cn.nekocode.kotgo.component.rx

import rx.Observable
import rx.subjects.PublishSubject
import rx.subjects.SerializedSubject
import rx.subjects.Subject

/**
 * @author nekocode (nekocode.cn@gmail.com)
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
