package cn.nekocode.baseframework.utils

import android.app.Fragment
import android.content.Context
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewManager
import android.widget.Toast
import cn.nekocode.baseframework.App
import org.jetbrains.anko.button
import org.jetbrains.anko.custom.addView
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


@suppress("NOTHING_TO_INLINE")
public inline fun ViewManager.toolbar(): Toolbar = toolbar({})
public inline fun ViewManager.toolbar(inlineOptions(InlineOption.ONLY_LOCAL_RETURN) init: Toolbar.() -> Unit): Toolbar = addView<Toolbar> {
    ctx ->
    val view = Toolbar(ctx)
    view.init()
    view
}