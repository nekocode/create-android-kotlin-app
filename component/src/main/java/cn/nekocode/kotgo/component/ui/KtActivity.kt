package cn.nekocode.kotgo.component.ui

import android.app.Fragment
import android.app.FragmentTransaction
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity
import cn.nekocode.kotgo.component.rx.RxLifecycle

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
abstract class KtActivity : WithLifecycleActivity() {

    abstract fun onCreatePresenter(presenterFactory: PresenterFactory)

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val trans = fragmentManager.beginTransaction()
        onCreatePresenter(PresenterFactory(trans))
        trans.commit()
    }

    inner class PresenterFactory(val trans: FragmentTransaction) {
        fun <T : KtPresenter<*>> createOrGet(presenterClass: Class<T>, args: Bundle? = null): T =
                addOrGetFragment(trans, 0, presenterClass.canonicalName, presenterClass, args)
    }

    fun <T : Fragment> addOrGetFragment(
            trans: FragmentTransaction, containerId: Int, tag: String,
            fragmentClass: Class<T>, args: Bundle? = null): T {

        val _args = if (intent.extras != null) Bundle(intent.extras) else Bundle()
        if (args != null) _args.putAll(args)

        val className = fragmentClass.canonicalName
        var fragment = supportFragmentManager.findFragmentByTag(tag) as T?
        if (fragment?.isDetached ?: true) {
            fragment = Fragment.instantiate(this@KtActivity, className, _args) as T

            trans.add(containerId, fragment, tag)
        }

        return fragment!!
    }
}
