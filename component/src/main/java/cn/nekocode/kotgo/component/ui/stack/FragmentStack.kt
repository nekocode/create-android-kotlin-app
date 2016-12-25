package cn.nekocode.kotgo.component.ui.stack

import android.app.Fragment
import android.app.FragmentManager
import android.os.Bundle
import android.util.SparseArray
import cn.nekocode.kotgo.component.ui.KtFragment
import cn.nekocode.kotgo.component.ui.KtFragmentActivity
import java.util.*

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class FragmentStack {
    companion object {
        private const val KEY_SAVED_RECORDS = "KEY_SAVED_RECORDS"
        private const val KEY_SAVED_STACK = "KEY_SAVED_STACK"
    }

    private val activity: KtFragmentActivity
    private val containerId: Int
    private val stack: ArrayList<String>
    private val manager: FragmentManager
    private val mapOfTag: HashMap<KtFragment, String>
    internal val requestsRecords: SparseArray<RequestsRecord>


    constructor(fragmentActivity: KtFragmentActivity, fragmentManager: FragmentManager, containerId: Int) {
        this.activity = fragmentActivity
        this.manager = fragmentManager
        this.containerId = containerId
        this.stack = ArrayList<String>()
        this.mapOfTag = HashMap<KtFragment, String>()
        this.requestsRecords = SparseArray<RequestsRecord>()
    }

    internal fun saveStack(outState: Bundle?) {
        outState?.putSparseParcelableArray(KEY_SAVED_RECORDS, requestsRecords)
        outState?.putStringArrayList(KEY_SAVED_STACK, stack)
    }

    internal fun restoreStack(savedInstanceState: Bundle) {
        val savedRecords = savedInstanceState.getSparseParcelableArray<RequestsRecord>(KEY_SAVED_RECORDS)
        val savedStack = savedInstanceState.getStringArrayList(KEY_SAVED_STACK)

        for (i in 0..savedRecords.size() - 1) {
            requestsRecords.put(savedRecords.keyAt(i), savedRecords.valueAt(i))
        }

        stack.clear()
        mapOfTag.clear()

        // Restore all fragments' visibility state
        manager.beginTransaction().apply {
            val lastIndex = savedStack.size - 1
            for (i in 0..lastIndex) {
                val tag = savedStack[i]
                val fragment = get(tag)!!

                if (i == lastIndex) {
                    show(fragment)
                } else {
                    hide(fragment)
                }

                stack.add(tag)
                mapOfTag[fragment] = tag
            }
        }.commit()
    }

    internal fun <T : KtFragment> push(tag: String, classType: Class<T>, args: Bundle? = null,
                                       originalTag: String? = null, requestCode: Int? = null) {

        var fragment = manager.findFragmentByTag(tag) as KtFragment?
        if (fragment != null) {
            // If the tag has already been used, throw exception
            throw IllegalArgumentException("Push framgnet error, the tag \"$tag\" has already been used.")
        }

        manager.beginTransaction().apply {
            // TODO You can set custom animations here

            // Hide the fragment top in stack
            val topFragment = getTopInStack()
            if (topFragment != null) {
                hide(topFragment)
            }

            // Obtain and show a new fragment
            val className = classType.canonicalName
            val fragment = Fragment.instantiate(activity, className, args) as KtFragment
            add(containerId, fragment, tag)

            // Add request record
            if (originalTag != null && requestCode != null) {
                addRequestToRecord(originalTag, requestCode)
                fragment.requestData = RequestData(requestCode)
            }

            stack.add(tag)
            mapOfTag[fragment] = tag

        }.commit()
    }

    internal fun addRequestToRecord(originalTag: String, requestCode: Int) {
        if (requestsRecords[requestCode] == null) {
            requestsRecords.append(requestCode, RequestsRecord(1, arrayListOf(originalTag), requestCode))
        } else {
            requestsRecords[requestCode]!!.reqCount++
            requestsRecords[requestCode]!!.tags.add(originalTag)
        }
    }

    fun popAll() {
        manager.beginTransaction().apply {
            for (tag in stack) {
                remove(get(tag)!!)
            }
            stack.clear()
            mapOfTag.clear()

        }.commit()
    }

    fun popUntil(tag: String) {
        if (tag in stack) {
            manager.beginTransaction().apply {
                stack.reversed()
                        .takeWhile { it != tag }
                        .forEach { tagToRemove ->
                            val fragmentToRemove = get(tagToRemove)
                            stack.remove(tagToRemove)
                            mapOfTag.remove(fragmentToRemove)
                            remove(fragmentToRemove)
                        }

                show(get(tag))

            }.commit()
        }
    }

    fun popTop(): Boolean {
        return pop(getTagTopInStack())
    }

    fun pop(tag: String?): Boolean {
        var failed = true

        if (tag != null && tag in stack) {
            val fragmentToRemove = get(tag)

            if (fragmentToRemove != null) {
                val reqInfo = fragmentToRemove.requestData
                if (reqInfo != null) {
                    activity.onActivityResult(reqInfo.requestCode, reqInfo.resultCode, reqInfo.resultData)
                }

                manager.beginTransaction().apply {
                    // TODO You can set custom animations here

                    remove(fragmentToRemove)
                    if (tag == getTagTopInStack() && stack.size > 1) {
                        show(get(stack[stack.size - 2]))
                    }

                    stack.remove(tag)
                    mapOfTag.remove(fragmentToRemove)

                }.commit()

                failed = false
            }
        }

        return !failed
    }


    /**
     * Query Operations
     */

    fun get(tag: String): KtFragment? = manager.findFragmentByTag(tag) as KtFragment?

    fun getTag(fragment: KtFragment?): String? = mapOfTag[fragment]

    fun getTagTopInStack(): String? {
        if (stack.size > 0) {
            return stack.last()
        }

        return null
    }

    fun getTopInStack(): KtFragment? {
        val topTag = getTagTopInStack()
        return if (topTag == null) null else get(topTag)
    }

    fun size() = stack.size
}