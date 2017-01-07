package cn.nekocode.kotgo.component.ui

import android.app.Fragment
import android.app.FragmentTransaction
import android.content.Intent
import android.os.Bundle
import android.support.annotation.CallSuper
import java.util.*

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
abstract class KtActivity : WithLifecycleActivity() {
    private var presenterTags = ArrayList<String>()

    abstract fun onCreatePresenter(presenterFactory: PresenterFactory)

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val trans = fragmentManager.beginTransaction()
        onCreatePresenter(PresenterFactory(trans))
        trans.commit()
    }

    @CallSuper
    override public fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        for (tag in presenterTags) {
            val frg = fragmentManager.findFragmentByTag(tag)

            if (frg != null && frg is KtPresenter<*>) {
                frg.onResult(requestCode, resultCode, data)
            }
        }
    }

    inner class PresenterFactory(val trans: FragmentTransaction) {

        fun <T : KtPresenter<*>> createOrGet(presenterClass: Class<T>, args: Bundle? = null): T {
            val tag = presenterClass.canonicalName
            presenterTags.add(tag)

            return addOrGetFragment(trans, 0, presenterClass.canonicalName, presenterClass, args)
        }

    }

    fun <T : Fragment> addOrGetFragment(
            trans: FragmentTransaction, containerId: Int, tag: String,
            fragmentClass: Class<T>, args: Bundle? = null): T {

        val _args = if (intent.extras != null) Bundle(intent.extras) else Bundle()
        if (args != null) _args.putAll(args)

        val className = fragmentClass.canonicalName
        var fragment = supportFragmentManager.findFragmentByTag(tag) as T?
        if (fragment == null || fragment.isDetached) {
            fragment = Fragment.instantiate(this, className, _args) as T

            trans.add(containerId, fragment, tag)
        }

        return fragment
    }
}
