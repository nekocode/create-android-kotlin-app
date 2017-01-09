package cn.nekocode.kotgo.component.ui

import android.app.Fragment
import android.app.FragmentTransaction
import android.content.Intent
import android.os.Bundle
import android.support.annotation.CallSuper
import android.view.View
import cn.nekocode.kotgo.component.rx.WithLifecycleFragment
import cn.nekocode.kotgo.component.ui.stack.RequestData
import java.util.*

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
abstract class KtFragment : WithLifecycleFragment() {
    companion object {
        const val KEY_SAVED_REQUEST_INFO = "KEY_SAVED_REQUEST_INFO"
    }

    /**
     * Lifecycle methods
     */

    internal var requestData: RequestData? = null
    private var presenterTags = ArrayList<String>()

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

    @CallSuper
    open fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {
        for (tag in presenterTags) {
            val frg = childFragmentManager.findFragmentByTag(tag)

            if (frg != null && frg is KtPresenter<*>) {
                frg.onResult(requestCode, resultCode, data)
            }
        }
    }

    fun setResult(resultCode: Int, data: Intent? = null) {
        requestData?.apply {
            this.resultCode = resultCode
            this.resultData = data
        }
    }

    inner class PresenterFactory(val trans: FragmentTransaction) {

        fun <T : KtPresenter<*>> createOrGet(presenterClass: Class<T>, args: Bundle? = null): T {
            val tag = presenterClass.canonicalName
            presenterTags.add(tag)

            return addOrGetFragment(trans, 0, tag, presenterClass, args)
        }

    }

    fun <T : Fragment> addOrGetFragment(
            trans: FragmentTransaction, containerId: Int,
            tag: String, fragmentClass: Class<T>, args: Bundle? = null): T {

        val _args = if (arguments != null) Bundle(arguments) else Bundle()
        if (args != null) _args.putAll(args)

        val className = fragmentClass.canonicalName
        var fragment = childFragmentManager.findFragmentByTag(tag) as T?
        if (fragment == null || fragment.isDetached) {
            fragment = Fragment.instantiate(activity, className, _args) as T

            trans.add(containerId, fragment, tag)
        }

        return fragment
    }
}