package cn.nekocode.baseframework.utils

import android.app.Fragment
import android.app.FragmentManager
import android.app.FragmentTransaction
import android.content.Context
import android.view.View
import android.widget.Toast
import rx.Observable

/**
 * Created by nekocode on 2015/8/13.
 */

public fun <T> rx.Observable<T>.onUI(): Observable<T> {
    return observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
}


public fun Context.showToast(any: Any) {
    when(any) {
        is Int ->
            Toast.makeText(this, any, Toast.LENGTH_SHORT).show()
        is String ->
            Toast.makeText(this, any, Toast.LENGTH_SHORT).show()
    }
}

public fun Fragment.showToast(any: Any) {
    when(any) {
        is Int ->
            Toast.makeText(activity, any, Toast.LENGTH_SHORT).show()
        is String ->
            Toast.makeText(activity, any, Toast.LENGTH_SHORT).show()
    }
}


public val View.context: Context
    get() = getContext()


public fun <T: Fragment> FragmentTransaction.addFragment(containerId: Int, tag: String, fragmentClass: Class<T>, fragmentManager: FragmentManager): T? {
    var fragment = fragmentManager.findFragmentByTag(tag) as T?
    if (fragment?.isDetached ?: true) {
        fragment = fragmentClass.newInstance()

        this.add(containerId, fragment, tag).commit()
    }

    return fragment
}