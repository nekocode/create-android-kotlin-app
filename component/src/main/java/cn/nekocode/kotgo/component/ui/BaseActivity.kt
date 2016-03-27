package cn.nekocode.kotgo.component.ui

import android.app.Fragment
import android.app.FragmentTransaction
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity
import cn.nekocode.kotgo.component.rx.RxLifecycle
import java.lang.ref.WeakReference

abstract class BaseActivity: AppCompatActivity(), RxLifecycle.Impl {
    companion object {
        class GlobalHandler: Handler {
            private val mOuter: WeakReference<BaseActivity>

            constructor(activity: BaseActivity) {
                mOuter = WeakReference(activity)
            }

            override fun handleMessage(msg: Message) {
                mOuter.get()?.apply {
                    if (msg.what == -101 && msg.arg1 == -102 && msg.arg2 == -103) {
                        val runnable = (msg.obj as WeakReference<() -> Unit>).get()
                        runnable?.invoke()
                        return
                    }

                    this.handler(msg)
                }
            }
        }
    }

    override final val lifecycle = RxLifecycle()
    val handler: GlobalHandler by lazy {
        GlobalHandler(this)
    }

    fun msg(message: Message) {
        Message().apply {
            copyFrom(message)
            handler.sendMessage(this)
        }
    }

    fun msgDelayed(message: Message, delayMillis: Long) {
        Message().apply {
            copyFrom(message)
            handler.sendMessageDelayed(this, delayMillis)
        }
    }

    fun runDelayed(delayMillis: Long, runnable: ()->Unit) {
        val msg = Message()
        msg.what = -101
        msg.arg1 = -102
        msg.arg2 = -103
        msg.obj = WeakReference<() -> Unit>(runnable)
        handler.sendMessageDelayed(msg, delayMillis)
    }

    @CallSuper
    override fun onDestroy() {
        lifecycle.onDestory()
        super.onDestroy()
    }

    open fun handler(msg: Message) {

    }

    final protected fun <T: Fragment> FragmentTransaction
            .checkAndAddFragment(containerId: Int, tag: String, fragmentClass: Class<T>): T? =
            checkAndAddFragment(containerId, tag, fragmentClass, null)

    final protected fun <T: Fragment> FragmentTransaction
            .checkAndAddFragment(containerId: Int, tag: String, fragmentClass: Class<T>, arguments: Bundle?): T? {

        var fragment = fragmentManager.findFragmentByTag(tag) as T?
        if (fragment?.isDetached ?: true) {
            fragment = Fragment.instantiate(this@BaseActivity, fragmentClass.canonicalName, arguments) as T

            this.add(containerId, fragment, tag)
        }

        return fragment
    }
}
