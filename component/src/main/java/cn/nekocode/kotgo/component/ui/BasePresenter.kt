package cn.nekocode.kotgo.component.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by nekocode on 2015/11/20.
 */
abstract class BasePresenter() : WithLifecycleFragment() {
    val fragAct: FragmentActivity?
        get() = activity as FragmentActivity?

    protected fun getParent(): Any? {
        var view: Any? = null

        if (parentFragment != null) {
            view = parentFragment
        } else if (activity != null) {
            view = activity
        }

        return view
    }

    final override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        onVewCreated(savedInstanceState)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    abstract fun onVewCreated(savedInstanceState: Bundle?)

    final override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}