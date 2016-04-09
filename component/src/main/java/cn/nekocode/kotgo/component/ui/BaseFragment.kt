package cn.nekocode.kotgo.component.ui

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by nekocode on 16/3/3.
 */
abstract class BaseFragment: WithLifecycleFragment() {
    abstract val layoutId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    inline protected fun <reified T: BasePresenter> bindPresenter(args: Bundle? = null): T {
        val fragmentClass = T::class.java
        return checkAndAddFragment(0, fragmentClass.canonicalName, fragmentClass, args)
    }

    final protected fun <T: Fragment> checkAndAddFragment(
            containerId: Int, tag: String,fragmentClass: Class<T>, args: Bundle? = null): T {

        val trans = childFragmentManager.beginTransaction()
        val className = fragmentClass.canonicalName

        var fragment = childFragmentManager.findFragmentByTag(tag) as T?
        if (fragment?.isDetached ?: true) {
            fragment = Fragment.instantiate(activity, className, args) as T

            trans.add(containerId, fragment, tag)
        }

        trans.commit()

        return fragment!!
    }
}