package cn.nekocode.kotgo.component.util

import android.app.Fragment
import android.app.FragmentManager
import android.app.FragmentTransaction
import android.content.Context
import android.widget.Toast
import rx.Observable
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers

/**
 * Created by nekocode on 2015/8/13.
 */
fun Context.showToast(any: Any?) {
    when(any) {
        is Int ->
            Toast.makeText(this, any, Toast.LENGTH_SHORT).show()
        is String ->
            Toast.makeText(this, any, Toast.LENGTH_SHORT).show()
        null ->
            return
    }
}

fun <T: Fragment> FragmentTransaction.addFragment(containerId: Int, tag: String, fragmentClass: Class<T>, fragmentManager: FragmentManager): T? {
    var fragment = fragmentManager.findFragmentByTag(tag) as T?
    if (fragment?.isDetached ?: true) {
        fragment = fragmentClass.newInstance()

        this.add(containerId, fragment, tag).commit()
    }

    return fragment
}


fun <T> Observable<T>.onUI(): Observable<T> {
    return observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.onUI(onNext: (T)->Unit): Subscription {
    return observeOn(AndroidSchedulers.mainThread()).subscribe(onNext)
}

fun <T> Observable<T>.onUI(subscriber: Subscriber<T>): Subscription {
    return observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber)
}

fun <T> Observable<T>.onUIInLifecycle(lifecycle: LifecycleContainer, onNext: (T)->Unit): Subscription {
    return bindLifecycle(lifecycle).observeOn(AndroidSchedulers.mainThread()).subscribe(onNext)
}

fun <T> Observable<T>.onUIInLifecycle(lifecycle: LifecycleContainer, subscriber: Subscriber<T>): Subscription {
    return bindLifecycle(lifecycle).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber)
}