package cn.nekocode.kotgo.component.ui

import android.app.Fragment
import android.app.FragmentManager
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.CallSuper
import android.util.Log
import android.util.SparseArray
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.collections.asSequence
import org.jetbrains.anko.collections.forEachReversed
import org.jetbrains.anko.frameLayout
import java.util.*

abstract class FragmentActivity: BaseActivity() {
    private lateinit var stack: FragmentStack
    private var containerId: Int = 0
    private var requestFragments = SparseArray<RequestFragmentsRecord>()

    // Stack operation
    fun <T: BaseFragment> push(tag: String, classType: Class<T>, args: Bundle? = null) {
        stack.push(tag, classType, args)
    }
    fun <T: BaseFragment> pushForResult(originalTag: String, requestCode: Int, fragmentTag: String, classType: Class<T>, args: Bundle? = null) {
        addRequestToRecord(originalTag, requestCode)
        stack.push(fragmentTag, classType, args, requestCode)
    }
    fun pop() = stack.pop()
    fun get(tag: String) = stack.get(tag)
    fun getFragmentTopInStack() = stack.getFragmentTopInStack()


    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        containerId = onCreateContainer()
        stack = FragmentStack(fragmentManager, containerId)

        if(savedInstanceState != null) {
            stack.restoreStack(savedInstanceState)
            savedInstanceState.getParcelableArrayList<RequestFragmentsRecord>("__requestFragments")
                    .forEach {
                        requestFragments.setValueAt(it.requestCode, it)
                    }
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
        outState?.putParcelableArrayList("__requestFragments",
                requestFragments.asSequence().toMutableList() as ArrayList<RequestFragmentsRecord>
        )

        super.onSaveInstanceState(outState)
    }

    private fun addRequestToRecord(fragmentTag: String, requestCode: Int) {
        requestFragments
        if(requestFragments[requestCode] == null) {
            requestFragments.setValueAt(requestCode, RequestFragmentsRecord(1, arrayListOf(fragmentTag), requestCode))
        } else {
            requestFragments[requestCode]!!.reqCount ++
            requestFragments[requestCode]!!.tags += fragmentTag
        }
    }

    fun startActivityForResult(fragmentTag: String, intent: Intent?, requestCode: Int, options: Bundle?=null) {
        addRequestToRecord(fragmentTag, requestCode)
        super.startActivityForResult(intent, requestCode, options)
    }

    @CallSuper
    override public fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val reqs = requestFragments[requestCode]
        if(reqs != null) {
            if((--requestFragments[requestCode]!!.reqCount) == 0) {
                requestFragments.remove(requestCode)
            }

            var found = false
            for(tag in reqs.tags) {
                val fragment = stack.get(tag)
                if(fragment is BaseFragment) {
                    fragment.onResult(requestCode, resultCode, data)
                    found = true
                }
            }

            if(!found) requestFragments.remove(requestCode)
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    @CallSuper
    override fun onBackPressed() {
        do {
            val topFragment = stack.getFragmentTopInStack()

            if(topFragment is BaseFragment) {
                if(topFragment.onBackPressed()) {
                    break
                }
            }

            // Pop fragment stack
            if(stack.pop()) break

            // Finish when only one fragment in the stack
            finish()
        } while(false)
    }

    open fun afterOnCreate(savedInstanceState: Bundle?) {}

    inner class FragmentStack {
        private var activity: FragmentActivity
        private var containerId: Int
        private var fragmentManager: FragmentManager

        private var fragmentStackRecord: ArrayList<String> = ArrayList()
        private var fragments: HashMap<String, Fragment> = HashMap()

        private var curSystemTime: Long = 0L
        private val PUSH_INTERVAL = 500L

        constructor(fragmentManager: FragmentManager, containerId: Int) {
            this.activity = this@FragmentActivity
            this.fragmentManager = fragmentManager
            this.containerId = containerId
        }

        fun saveStack(outState: Bundle?) {
            outState?.apply {
                putStringArrayList("__fragmentRecord", fragmentStackRecord)
            }
        }

        fun restoreStack(savedInstanceState: Bundle) {
            savedInstanceState.apply {
                fragmentStackRecord.clear()
                fragmentStackRecord.addAll(getStringArrayList("__fragmentRecord"))

                val trans = fragmentManager.beginTransaction()
                // Restore all fragments' visibility state
                var showFlag = false
                fragmentStackRecord.forEachReversed { tag ->
                    val fragment = fragmentManager.findFragmentByTag(tag)

                    if(!showFlag) {
                        trans.show(fragment)
                        showFlag = true
                    } else {
                        trans.hide(fragment)
                    }

                    fragments[tag] = fragment as BaseFragment
                }
                trans.commit()
            }
        }

        fun <T: BaseFragment> push(tag: String, classType: Class<T>, args: Bundle? = null, requestCode: Int? = null) {
            val time = System.currentTimeMillis()

            var fragment = fragmentManager.findFragmentByTag(tag)
            if(fragment != null) {
                // If the tag has already been used
                curSystemTime = time
                Log.d("FragmentStack", "The tag \"$tag\" has already been used.")
                return
            }

            if(time - curSystemTime < PUSH_INTERVAL) {
                // Refuse to push when the previous commit hasn't been applied
                curSystemTime = time
                return
            }
            curSystemTime = time

            val trans = fragmentManager.beginTransaction()

            // Hide the fragment top in stack
            val topFragment = getFragmentTopInStack()
            if(topFragment != null) {
                trans.hide(topFragment)
            }

            // Obtain and show a new fragment
            val className = classType.canonicalName
            fragment = Fragment.instantiate(activity, className, args)
            trans.add(containerId, fragment, tag)

            // Set request code
            if(requestCode != null)
                (fragment as BaseFragment).apply {
                    requestInfo = RequestInfo(requestCode)
                }

            // Add to record
            fragments[tag] = fragment
            fragmentStackRecord.add(tag)

            trans.addToBackStack(tag).commit()
        }

        fun pop(): Boolean {
            val count = fragmentManager.backStackEntryCount
            if(count > 1) {
                val tag = fragmentStackRecord.last()

                val reqInfo = get(tag)!!.requestInfo
                if(reqInfo != null) {
                    activity.onActivityResult(reqInfo.requestCode, reqInfo.resultCode, reqInfo.resultData)
                }

                fragmentStackRecord.remove(tag)
                fragments.remove(tag)
                fragmentManager.popBackStack()

                return true
            } else {
                return false
            }
        }

        // TODO: pop(tag)

        fun get(tag: String): BaseFragment? {
            return fragments[tag] as BaseFragment?
        }

        fun getFragmentTopInStack(): Fragment? {
            val count = fragmentManager.backStackEntryCount
            if(count > 0) {
                val tag = fragmentManager.getBackStackEntryAt(count - 1).name
                val fragment = fragmentManager.findFragmentByTag(tag)

                return fragment
            }

            return null
        }
    }

    data class RequestFragmentsRecord(var reqCount: Int, val tags: ArrayList<String>, var requestCode: Int) : Parcelable {
        constructor(source: Parcel): this(source.readInt(), source.createStringArrayList(), source.readInt())

        override fun describeContents(): Int {
            return 0
        }

        override fun writeToParcel(dest: Parcel?, flags: Int) {
            dest?.writeInt(reqCount)
            dest?.writeStringList(tags)
            dest?.writeInt(requestCode)
        }

        companion object {
            @JvmField final val CREATOR: Parcelable.Creator<RequestFragmentsRecord> = object : Parcelable.Creator<RequestFragmentsRecord> {
                override fun createFromParcel(source: Parcel): RequestFragmentsRecord {
                    return RequestFragmentsRecord(source)
                }

                override fun newArray(size: Int): Array<RequestFragmentsRecord?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }

    data class RequestInfo(var requestCode: Int, var resultCode: Int = 0, var resultData: Intent? = null) : Parcelable {
        constructor(source: Parcel): this(
                source.readInt(),
                source.readInt(),
                source.readParcelable<Intent?>(Intent::class.java.classLoader)
        )

        override fun describeContents(): Int {
            return 0
        }

        override fun writeToParcel(dest: Parcel?, flags: Int) {
            dest?.writeInt(requestCode)
            dest?.writeInt(resultCode)
            dest?.writeParcelable(resultData, 0)
        }

        companion object {
            @JvmField final val CREATOR: Parcelable.Creator<RequestInfo> = object : Parcelable.Creator<RequestInfo> {
                override fun createFromParcel(source: Parcel): RequestInfo {
                    return RequestInfo(source)
                }

                override fun newArray(size: Int): Array<RequestInfo?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }
}
