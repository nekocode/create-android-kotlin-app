package cn.nekocode.template.base

import android.app.Fragment
import android.app.FragmentTransaction
import android.os.Bundle
import android.support.annotation.CallSuper
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
abstract class BaseActivity : RxAppCompatActivity(), IContextProvider {

    abstract fun onCreatePresenter(presenterFactory: PresenterFactory)

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val trans = fragmentManager.beginTransaction()
        onCreatePresenter(PresenterFactory(trans))
        trans.commit()
    }

    override fun getContext() = this

    fun <T : Fragment> addOrGetFragment(
            trans: FragmentTransaction, containerId: Int,
            tag: String, fragmentClass: Class<T>, args: Bundle? = null): T {

        var fragment = fragmentManager.findFragmentByTag(tag) as T?
        if (fragment == null) {
            fragment = Fragment.instantiate(this, fragmentClass.canonicalName, args) as T
            trans.add(containerId, fragment, tag)

        } else if (fragment.isDetached) {
            trans.attach(fragment)
        }

        return fragment
    }

    inner class PresenterFactory(val trans: FragmentTransaction) {

        fun <T : BasePresenter<*>> createOrGet(presenterClass: Class<T>, args: Bundle? = null): T {
            val _args = if (intent.extras != null) Bundle(intent.extras) else Bundle()
            if (args != null) _args.putAll(args)
            val presenter = addOrGetFragment(trans, 0, presenterClass.canonicalName, presenterClass, _args)
            presenter.setView(this@BaseActivity)

            return presenter
        }
    }
}