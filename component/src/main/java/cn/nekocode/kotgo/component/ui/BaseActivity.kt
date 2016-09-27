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

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
abstract class BaseActivity : AppCompatActivity(), RxLifecycle.Impl {
    private class MessageHandler : Handler {
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

                this.handlerMessage(msg)
            }
        }
    }

    override final val lifecycle = RxLifecycle()
    private var handler: MessageHandler? = null

    abstract fun onCreatePresenter(presenterFactory: PresenterFactory)

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handler = MessageHandler(this)

        val trans = fragmentManager.beginTransaction()
        onCreatePresenter(PresenterFactory(trans))
        trans.commit()
    }

    fun sendMessage(message: Message) {
        handler?.sendMessage(Message.obtain(message))
    }

    fun sendMessageDelayed(message: Message, delayMillis: Long) {
        handler?.sendMessageDelayed(Message.obtain(message), delayMillis)
    }

    fun runDelayed(delayMillis: Long, runnable: () -> Unit) {
        handler?.sendMessageDelayed(
                Message.obtain(handler, -101, -102, -103, WeakReference<() -> Unit>(runnable)),
                delayMillis)
    }

    @CallSuper
    override fun onDestroy() {
        handler = null
        lifecycle.onDestory()
        super.onDestroy()
    }

    open fun handlerMessage(msg: Message) {

    }

    inner class PresenterFactory(val trans: FragmentTransaction) {
        fun <T : BasePresenter> create(presenterClass: Class<T>, args: Bundle? = null): T =
                checkAndAddFragment(trans, 0, presenterClass.canonicalName, presenterClass, args)
    }

    fun <T : Fragment> checkAndAddFragment(
            trans: FragmentTransaction, containerId: Int, tag: String,
            fragmentClass: Class<T>, args: Bundle? = null): T {

        val className = fragmentClass.canonicalName
        var fragment = supportFragmentManager.findFragmentByTag(tag) as T?
        if (fragment?.isDetached ?: true) {
            fragment = Fragment.instantiate(this@BaseActivity, className, args) as T

            trans.add(containerId, fragment, tag)
        }

        return fragment!!
    }
}
