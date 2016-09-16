package cn.nekocode.kotgo.component.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.CallSuper
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.frameLayout

abstract class FragmentActivity : BaseActivity() {
    private lateinit var stack: FragmentStack

    /**
     * Stack operations
     */

    fun <T : BaseFragment> push(tag: String, classType: Class<T>, args: Bundle? = null) {
        stack.push(tag, classType, args)
    }

    fun <T : BaseFragment> pushSafety(tag: String, classType: Class<T>, args: Bundle? = null) {
        stack.pushSafety(tag, classType, args)
    }

    fun <T : BaseFragment> pushForResult(fragment: BaseFragment, requestCode: Int, fragmentTag: String, classType: Class<T>, args: Bundle? = null) {
        stack.push(fragmentTag, classType, args, stack.getTag(fragment), requestCode)
    }

    fun <T : BaseFragment> pushForResultSafety(fragment: BaseFragment, requestCode: Int, fragmentTag: String, classType: Class<T>, args: Bundle? = null) {
        stack.pushSafety(fragmentTag, classType, args, stack.getTag(fragment), requestCode)
    }

    fun startActivityForResult(fragment: BaseFragment, intent: Intent?, requestCode: Int, options: Bundle? = null) {
        stack.addRequestToRecord(stack.getTag(fragment)!!, requestCode)
        super.startActivityForResult(intent, requestCode, options)
    }

    fun popAll() = stack.popAll()
    fun popUntil(tag: String) = stack.popUntil(tag)
    fun popTop() = stack.popTop()


    /**
     * Lifecycle methods
     */

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val containerId = onCreateContainer()
        stack = FragmentStack(this, fragmentManager, containerId)

        if (savedInstanceState != null) {
            stack.restoreStack(savedInstanceState)
        }

        afterOnCreate(savedInstanceState)
    }

    open fun onCreateContainer(): Int {
        val ID_FRAGMENT_CONTENT = 1000

        frameLayout {
            id = ID_FRAGMENT_CONTENT
            backgroundColor = Color.WHITE
        }

        return ID_FRAGMENT_CONTENT
    }

    @CallSuper
    override fun onSaveInstanceState(outState: Bundle?) {
        stack.saveStack(outState)
        super.onSaveInstanceState(outState)
    }

    @CallSuper
    override public fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val records = stack.requestRecords

        val reqs = records[requestCode]
        if (reqs != null) {
            if ((--records[requestCode]!!.reqCount) == 0) {
                records.remove(requestCode)
            }

            var found = false
            for (tag in reqs.tags) {
                val fragment = stack.get(tag)
                if (fragment is BaseFragment) {
                    fragment.onResult(requestCode, resultCode, data)
                    found = true
                }
            }

            if (!found) records.remove(requestCode)
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    @CallSuper
    override fun onBackPressed() {
        do {
            val topFragment = stack.getTopInStack()
            if (topFragment is BaseFragment) {
                if (topFragment.onBackPressed()) {
                    // If the fragment intercepted the event, don't pop this fragment
                    break
                }
            }

            val stackSize = stack.size()
            if (stackSize <= 1) {
                // Finish the activity when no or only one fragment in the stack
                finish()
                break
            }

            // Pop fragment
            stack.popTop()
        } while (false)
    }

    open fun afterOnCreate(savedInstanceState: Bundle?) {
    }

}
