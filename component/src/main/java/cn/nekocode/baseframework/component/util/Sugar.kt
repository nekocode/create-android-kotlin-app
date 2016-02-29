package cn.nekocode.baseframework.component.util

import android.app.Fragment
import android.app.FragmentManager
import android.app.FragmentTransaction
import android.content.Context
import android.widget.Toast
import rx.Observable
import rx.android.schedulers.AndroidSchedulers

/**
 * Created by nekocode on 2015/8/13.
 */

fun <T> Observable<T>.onUI(): Observable<T> {
    return observeOn(AndroidSchedulers.mainThread())
}

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