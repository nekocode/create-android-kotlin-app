package cn.nekocode.baseframework.component.presentation

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.Toolbar
import butterknife.bindView
import cn.nekocode.baseframework.component.presentation.BaseActivity
import org.jetbrains.anko.*

abstract class SingleFragmentActivity : BaseActivity() {
    val id_toolbar = 1
    val toolbar: Toolbar by bindView(id_toolbar)

    val id_fragment_content = 2
    var fragment: Fragment? = null

    abstract val toolbarLayoutId: Int
    open var toolbarHeight = 50

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        relativeLayout {
            include<Toolbar>(toolbarLayoutId) {
                id = id_toolbar
            }.lparams(width = matchParent, height = dip(toolbarHeight))

            frameLayout {
                id = id_fragment_content
            }.lparams(width = matchParent, height = matchParent) {
                below(id_toolbar)
            }
        }

        setupFragment()
        afterCreate()

        setSupportActionBar(toolbar)
    }

    private fun setupFragment() {
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragment = fragmentManager.findFragmentByTag(fragmentClass.name);

        if (fragment?.isDetached ?: true) {
            fragment = Fragment.instantiate(this, fragmentClass.name, fragmentBundle);

            fragmentTransaction.add(id_fragment_content, fragment, fragmentClass.name);
        }

        fragmentTransaction.commit()
    }

    abstract fun afterCreate()

    abstract val fragmentClass: Class<*>

    abstract val fragmentBundle: Bundle?
}
