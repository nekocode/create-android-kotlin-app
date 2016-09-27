package cn.nekocode.kotgo.component.ui

import android.app.Fragment
import android.app.FragmentTransaction
import android.content.Intent
import android.os.Bundle
import android.support.annotation.CallSuper
import android.view.View

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
abstract class BaseFragment : WithLifecycleFragment() {
    companion object {
        const val KEY_SAVE_REQUEST_INFO = "__REQUEST_IFNO__"
    }

    private val stackActivity: FragmentActivity?
        get() = if (activity is FragmentActivity) activity as FragmentActivity else null

    /**
     * Stack operations
     */

    fun <T : BaseFragment> push(tag: String, classType: Class<T>, args: Bundle? = null) {
        stackActivity?.push(tag, classType, args)
    }

    fun <T : BaseFragment> pushForResult(requestCode: Int, fragmentTag: String,
                                         classType: Class<T>, args: Bundle? = null) {

        stackActivity?.pushForResult(this, requestCode, fragmentTag, classType, args)
    }

    override final fun startActivityForResult(intent: Intent?, requestCode: Int, options: Bundle?) {
        stackActivity?.startActivityForResult(this, intent, requestCode, options)
    }

    fun popAll() = stackActivity?.popAll()
    fun popUntil(tag: String) = stackActivity?.popUntil(tag)
    fun popTop() = stackActivity?.popTop()


    /**
     * Lifecycle methods
     */

    internal var requestInfo: RequestInfo? = null

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            requestInfo = savedInstanceState.getParcelable(KEY_SAVE_REQUEST_INFO)
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
        requestInfo?.apply { outState?.putParcelable(KEY_SAVE_REQUEST_INFO, this) }
        super.onSaveInstanceState(outState)
    }

    open fun onBackPressed(): Boolean {
        return false
    }

    open fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

    fun setResult(resultCode: Int, data: Intent? = null) {
        requestInfo?.apply {
            this.resultCode = resultCode
            this.resultData = data
        }
    }

    inner class PresenterFactory(val trans: FragmentTransaction) {
        fun <T : BasePresenter> create(presenterClass: Class<T>, args: Bundle? = null): T =
                checkAndAddFragment(trans, 0, presenterClass.canonicalName, presenterClass, args)
    }

    fun <T : Fragment> checkAndAddFragment(
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