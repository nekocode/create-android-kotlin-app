package cn.nekocode.baseframework.utils

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.support.v4.app.DialogFragment
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewManager
import android.widget.LinearLayout
import android.widget.Toast
import cn.nekocode.baseframework.App
import org.jetbrains.anko._LinearLayout
import org.jetbrains.anko.button
import org.jetbrains.anko.custom.addView
import org.jetbrains.anko.verticalLayout
import rx.Observable

/**
 * Created by nekocode on 2015/8/13.
 */

public fun <T> rx.Observable<T>.ui(): Observable<T> {
    return observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
}

public fun showToast(any: Any) {
    when(any) {
        is Int ->
            Toast.makeText(App.instance, any, Toast.LENGTH_SHORT).show()
        is String ->
            Toast.makeText(App.instance, any, Toast.LENGTH_SHORT).show()
    }
}

public val View.context: Context
    get() = getContext()