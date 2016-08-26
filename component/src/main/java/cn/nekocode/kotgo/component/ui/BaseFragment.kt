package cn.nekocode.kotgo.component.ui

import android.app.Fragment
import android.app.FragmentTransaction
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by nekocode on 16/3/3.
 */
abstract class BaseFragment : WithLifecycleFragment() {
    companion object {
        const val KEY_SAVE_REQUEST_INFO = "__REQUEST_IFNO__"
    }

    private fun stackActivity() = if (activity is FragmentActivity) activity as FragmentActivity else null

    /**
     * Stack operations
     */

    fun <T : BaseFragment> push(tag: String, classType: Class<T>, args: Bundle? = null) {
        stackActivity()?.push(tag, classType, args)
    }

    fun <T : BaseFragment> pushSafety(tag: String, classType: Class<T>, args: Bundle? = null) {
        stackActivity()?.pushSafety(tag, classType, args)
    }

    fun <T : BaseFragment> pushForResult(requestCode: Int, fragmentTag: String, classType: Class<T>, args: Bundle? = null) {
        stackActivity()?.pushForResult(this, requestCode, fragmentTag, classType, args)
    }

    fun <T : BaseFragment> pushForResultSafety(requestCode: Int, fragmentTag: String, classType: Class<T>, args: Bundle? = null) {
        stackActivity()?.pushForResultSafety(this, requestCode, fragmentTag, classType, args)
    }

    override final fun startActivityForResult(intent: Intent?, requestCode: Int, options: Bundle?) {
        stackActivity()?.startActivityForResult(this, intent, requestCode, options)
    }

    fun popAll() = stackActivity()?.popAll()
    fun popUntil(tag: String) = stackActivity()?.popUntil(tag)
    fun popTop() = stackActivity()?.popTop()


    /**
     * Lifecycle methods
     */

    abstract val layoutId: Int
    var requestInfo: RequestInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            requestInfo = savedInstanceState.getParcelable(KEY_SAVE_REQUEST_INFO)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
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

    protected fun setResult(resultCode: Int, data: Intent? = null) {
        requestInfo?.apply {
            this.resultCode = resultCode
            this.resultData = data
        }
    }

    inline protected fun <reified T : BasePresenter> bindPresenter(args: Bundle? = null): T {
        val fragmentClass = T::class.java
        val trans = childFragmentManager.beginTransaction()
        val framgnet = checkAndAddFragment(trans, 0, fragmentClass.canonicalName, fragmentClass, args)
        trans.commit()
        return framgnet
    }

    protected fun <T : Fragment> checkAndAddFragment(
            trans: FragmentTransaction, containerId: Int, tag: String, fragmentClass: Class<T>, args: Bundle? = null): T {

        val className = fragmentClass.canonicalName

        var fragment = childFragmentManager.findFragmentByTag(tag) as T?
        if (fragment?.isDetached ?: true) {
            fragment = Fragment.instantiate(activity, className, args) as T

            trans.add(containerId, fragment, tag)
        }

        return fragment!!
    }
}