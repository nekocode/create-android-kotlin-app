package cn.nekocode.kotgo.component.rx

import rx.Observable
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
fun <T> Observable<T>.onUI(): Observable<T> {
    return observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.onUI(uiAction: (T) -> Unit): Subscription {
    return observeOn(AndroidSchedulers.mainThread()).subscribe(uiAction)
}

fun <T> Observable<T>.onUI(subscriber: Subscriber<T>): Subscription {
    return observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber)
}