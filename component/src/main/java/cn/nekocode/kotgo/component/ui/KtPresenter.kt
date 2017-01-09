package cn.nekocode.kotgo.component.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.nekocode.kotgo.component.rx.WithLifecycleFragment

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
abstract class KtPresenter<in V>() : WithLifecycleFragment() {

    /**
     * Stack operations
     */

    fun <T : KtFragment> push(fragmentTag: String, classType: Class<T>, args: Bundle? = null) {
        val parentActivity = activity
        if (parentActivity is KtFragmentActivity) {
            val stack = parentActivity.stack

            stack.push(fragmentTag, classType, args)
        }
    }

    fun <T : KtFragment> pushForResult(requestCode: Int, fragmentTag: String, classType: Class<T>, args: Bundle? = null) {
        val parentActivity = activity
        if (parentActivity is KtFragmentActivity) {
            val stack = parentActivity.stack
            val tag = parentFragment?.tag ?: return

            stack.push(fragmentTag, classType, args, tag, requestCode)
        }
    }

    override fun startActivityForResult(intent: Intent?, requestCode: Int, options: Bundle?) {
        val parentActivity = activity
        if (parentActivity is KtFragmentActivity) {
            val stack = parentActivity.stack
            val tag = parentFragment?.tag ?: return

            stack.addRequestToRecord(tag, requestCode)
            parentActivity.startActivityForResult(intent, requestCode, options)
        }
    }

    fun popThis(checkEmpty: Boolean = true) {
        val parentActivity = activity
        if (parentActivity is KtFragmentActivity) {
            val stack = parentActivity.stack
            val tag = parentFragment?.tag ?: return

            if (checkEmpty && stack.size() <= 1) {
                // Finish the activity when no or only one fragment in the stack
                parentActivity.finish()

            } else {
                // Pop fragment
                stack.pop(tag)
            }
        }
    }


    /**
     * Lifecycle methods
     */

    final override fun onCreateView(inflater: LayoutInflater?,
                                    container: ViewGroup?, savedInstanceState: Bundle?): View? {

        super.onCreateView(inflater, container, savedInstanceState)
        onViewCreated((parentFragment ?: activity) as V?, savedInstanceState)
        return null
    }

    abstract fun onViewCreated(view: V?, savedInstanceState: Bundle?)

    final override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    }

    fun setResult(resultCode: Int, data: Intent? = null) {
        val parentFragment = parentFragment
        if (parentFragment != null && parentFragment is KtFragment) {
            parentFragment.setResult(resultCode, data)
            return
        }

        val parentActivity = activity
        if (parentActivity != null && parentActivity is KtActivity) {
            parentActivity.setResult(resultCode, data)
            return
        }
    }

    open fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }
}