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
    override final val lifecycle = RxLifecycle()

    abstract fun onCreatePresenter(presenterFactory: PresenterFactory)

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val trans = fragmentManager.beginTransaction()
        onCreatePresenter(PresenterFactory(trans))
        trans.commit()
    }

    @CallSuper
    override fun onDestroy() {
        lifecycle.onDestory()
        super.onDestroy()
    }

    inner class PresenterFactory(val trans: FragmentTransaction) {
        fun <T : BasePresenter<*>> createOrGet(presenterClass: Class<T>, args: Bundle? = null): T =
                addOrGetFragment(trans, 0, presenterClass.canonicalName, presenterClass, args)
    }

    fun <T : Fragment> addOrGetFragment(
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
