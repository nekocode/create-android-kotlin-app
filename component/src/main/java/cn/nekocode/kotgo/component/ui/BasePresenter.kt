package cn.nekocode.kotgo.component.ui

/**
 * Created by nekocode on 2015/11/20.
 */
open class BasePresenter(): WithLifecycleFragment() {
    val fragAct: FragmentActivity?
        get() = activity as FragmentActivity?

    protected fun getParent(): Any? {
        var view: Any? = null

        if(parentFragment != null) {
            view = parentFragment
        } else if(activity != null) {
            view = activity
        }

        return view
    }
}