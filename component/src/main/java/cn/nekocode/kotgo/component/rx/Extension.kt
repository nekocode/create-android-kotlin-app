package cn.nekocode.kotgo.component.rx

import rx.Observable
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers

/**
 * Created by nekocode on 2016/3/14.
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

fun <T> onUI(uiAction: (T) -> Unit): ((Observable<T>) -> Subscription) {
    return { observable -> observable.onUI(uiAction) }
}

fun <T> onUI(subscriber: Subscriber<T>): ((Observable<T>) -> Subscription) {
    return { observable -> observable.onUI(subscriber) }
}

fun <T> Observable<T>.bindAction(uiAction: ((Observable<T>) -> Subscription)) = uiAction(this)