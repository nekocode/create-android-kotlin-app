package cn.nekocode.kotgo.component.ui.stack

import android.app.Fragment
import android.app.FragmentManager
import android.os.Bundle
import android.util.SparseArray
import cn.nekocode.kotgo.component.ui.BaseFragment
import cn.nekocode.kotgo.component.ui.FragmentActivity
import org.jetbrains.anko.collections.asSequence
import java.util.*

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class FragmentStack {
    companion object {
        private const val KEY_SAVE_RECORDS = "__REQUEST_RECORDS__"
    }

    private val activity: FragmentActivity
    private val containerId: Int
    private val manager: FragmentManager
    private val mapOfTag: HashMap<BaseFragment, String>
    internal val requestsRecord: SparseArray<RequestsRecord>


    constructor(fragmentActivity: FragmentActivity, fragmentManager: FragmentManager, containerId: Int) {
        this.activity = fragmentActivity
        this.manager = fragmentManager
        this.containerId = containerId
        mapOfTag = HashMap<BaseFragment, String>()
        requestsRecord = SparseArray<RequestsRecord>()
    }

    internal fun saveStack(outState: Bundle?) {
        outState?.putParcelableArrayList(KEY_SAVE_RECORDS,
                requestsRecord.asSequence().toMutableList() as ArrayList<RequestsRecord>
        )
    }

    internal fun restoreStack(savedInstanceState: Bundle) {
        savedInstanceState.getParcelableArrayList<RequestsRecord>(KEY_SAVE_RECORDS)
                .forEach { requestsRecord.setValueAt(it.requestCode, it) }

        mapOfTag.clear()
        val trans = manager.beginTransaction()

        // Restore all fragments' visibility state
        var showFlag = false
        for (i in (manager.backStackEntryCount - 1) downTo 0) {
            val tag = manager.getBackStackEntryAt(i).name
            val fragment = get(tag)!!

            if (!showFlag) {
                trans.show(fragment)
                showFlag = true
            } else {
                trans.hide(fragment)
            }

            // Restore map of fragments' tags
            mapOfTag[fragment] = tag
        }

        trans.commit()
    }

    internal fun <T : BaseFragment> push(tag: String, classType: Class<T>, args: Bundle? = null,
                                         originalTag: String? = null, requestCode: Int? = null) {

        var fragment = manager.findFragmentByTag(tag) as BaseFragment?
        if (fragment != null) {
            // If the tag has already been used, throw exception
            throw IllegalArgumentException("Push framgnet error, the tag \"$tag\" has already been used.")
        }

        val trans = manager.beginTransaction()

        // Hide the fragment top in stack
        val topFragment = getTopInStack()
        if (topFragment != null) {
            trans.hide(topFragment)
        }

        // Obtain and show a new fragment
        val className = classType.canonicalName
        fragment = Fragment.instantiate(activity, className, args) as BaseFragment
        trans.add(containerId, fragment, tag)

        // Add request record
        if (originalTag != null && requestCode != null) {
            addRequestToRecord(originalTag, requestCode)
            fragment.requestData = RequestData(requestCode)
        }

        mapOfTag[fragment] = tag
        trans.addToBackStack(tag)
        trans.commit()
    }

    internal fun addRequestToRecord(originalTag: String, requestCode: Int) {
        if (requestsRecord[requestCode] == null) {
            requestsRecord.append(requestCode, RequestsRecord(1, arrayListOf(originalTag), requestCode))
        } else {
            requestsRecord[requestCode]!!.reqCount++
            requestsRecord[requestCode]!!.tags.add(originalTag)
        }
    }

    fun popAll() {
        mapOfTag.clear()

        val count = manager.backStackEntryCount
        kotlin.repeat(count) {
            manager.popBackStack()
        }
    }

    fun popUntil(tag: String) {
        // TODO: Make sure have the tag
        val count = manager.backStackEntryCount
        for (i in (count - 1) downTo 0) {
            val topTag = manager.getBackStackEntryAt(i).name
            if (topTag != tag) {
                manager.popBackStack()

            } else return
        }
    }

    fun popTop(): Boolean {
        var failed = true
        val topFragment = getTopInStack()

        if (topFragment != null) {
            val reqInfo = topFragment.requestData
            if (reqInfo != null) {
                activity.onActivityResult(reqInfo.requestCode, reqInfo.resultCode, reqInfo.resultData)
            }

            mapOfTag.remove(topFragment)
            manager.popBackStack()
            failed = false
        }

        return !failed
    }


    /**
     * Query Operations
     */

    fun get(tag: String): BaseFragment? = manager.findFragmentByTag(tag) as BaseFragment?

    fun getTag(fragment: BaseFragment?): String? = mapOfTag[fragment]

    fun getTagTopInStack(): String? {
        val count = manager.backStackEntryCount
        if (count > 0) {
            val topTag = manager.getBackStackEntryAt(count - 1).name
            return topTag
        }

        return null
    }

    fun getTopInStack(): BaseFragment? {
        val topTag = getTagTopInStack()
        return if (topTag == null) null else get(topTag)
    }

    fun size() = manager.backStackEntryCount
}