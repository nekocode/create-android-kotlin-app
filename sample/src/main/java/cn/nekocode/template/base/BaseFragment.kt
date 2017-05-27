package cn.nekocode.template.base

import android.app.Fragment
import android.app.FragmentManager
import android.app.FragmentTransaction
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.annotation.CallSuper
import com.trello.rxlifecycle2.components.RxFragment

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
abstract class BaseFragment : RxFragment(), IContextProvider {

    abstract fun onCreatePresenter(presenterFactory: PresenterFactory)

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onCreatePresenter(PresenterFactory())
    }

    override fun getContext(): Context =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) super.getContext() else activity

    fun <T : Fragment> addOrGetFragment(
            fragmentManager: FragmentManager, trans: FragmentTransaction, containerId: Int,
            tag: String, fragmentClass: Class<T>, args: Bundle? = null): T {

        var fragment = fragmentManager.findFragmentByTag(tag) as T?
        if (fragment == null || fragment.isDetached) {
            fragment = Fragment.instantiate(context, fragmentClass.canonicalName, args) as T

            trans.add(containerId, fragment, tag)
        }

        return fragment
    }

    inner class PresenterFactory {

        fun <T : BasePresenter<*>> createOrGet(
                fragmentManager: FragmentManager, trans: FragmentTransaction,
                presenterClass: Class<T>, args: Bundle? = null): T {

            val _args = if (arguments != null) Bundle(arguments) else Bundle()
            if (args != null) _args.putAll(args)
            val presenter = addOrGetFragment(
                    fragmentManager, trans, 0, presenterClass.canonicalName, presenterClass, _args)
            presenter.setView(this@BaseFragment)

            return presenter
        }
    }
}