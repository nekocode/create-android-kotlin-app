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
    abstract val layoutId: Int
    var requestInfo: FragmentActivity.RequestInfo? = null
    val fragAct: FragmentActivity?
        get() = activity as FragmentActivity?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            requestInfo = savedInstanceState.getParcelable("__requestInfo")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        if (requestInfo != null)
            outState?.putParcelable("__requestInfo", requestInfo!!)

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

    final protected fun <T : Fragment> checkAndAddFragment(
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