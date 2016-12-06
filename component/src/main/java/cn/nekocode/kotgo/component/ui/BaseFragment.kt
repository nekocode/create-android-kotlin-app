package cn.nekocode.kotgo.component.ui

import android.app.Fragment
import android.app.FragmentTransaction
import android.content.Intent
import android.os.Bundle
import android.support.annotation.CallSuper
import android.view.View
import cn.nekocode.kotgo.component.ui.stack.RequestData

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
abstract class BaseFragment : WithLifecycleFragment() {
    companion object {
        const val KEY_SAVED_REQUEST_INFO = "KEY_SAVED_REQUEST_INFO"
    }

    /**
     * Lifecycle methods
     */

    internal var requestData: RequestData? = null

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            requestData = savedInstanceState.getParcelable(KEY_SAVED_REQUEST_INFO)
        }
    }

    abstract fun onCreatePresenter(presenterFactory: PresenterFactory)

    @CallSuper
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val trans = childFragmentManager.beginTransaction()
        onCreatePresenter(PresenterFactory(trans))
        trans.commit()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        requestData?.apply { outState?.putParcelable(KEY_SAVED_REQUEST_INFO, this) }
        super.onSaveInstanceState(outState)
    }

    open fun onBackPressed(): Boolean {
        return false
    }

    open fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

    fun setResult(resultCode: Int, data: Intent? = null) {
        requestData?.apply {
            this.resultCode = resultCode
            this.resultData = data
        }
    }

    inner class PresenterFactory(val trans: FragmentTransaction) {
        fun <T : BasePresenter<*>> createOrGet(presenterClass: Class<T>, args: Bundle? = null): T =
                addOrGetFragment(trans, 0, presenterClass.canonicalName, presenterClass, args)
    }

    fun <T : Fragment> addOrGetFragment(
            trans: FragmentTransaction, containerId: Int,
            tag: String, fragmentClass: Class<T>, args: Bundle? = null): T {

        val className = fragmentClass.canonicalName
        var fragment = childFragmentManager.findFragmentByTag(tag) as T?
        if (fragment?.isDetached ?: true) {
            fragment = Fragment.instantiate(activity, className, args) as T

            trans.add(containerId, fragment, tag)
        }

        return fragment!!
    }
}