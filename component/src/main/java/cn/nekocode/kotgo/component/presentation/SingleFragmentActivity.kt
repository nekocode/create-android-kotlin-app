package cn.nekocode.kotgo.component.presentation

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.TypedValue
import butterknife.bindView
import org.jetbrains.anko.*

abstract class SingleFragmentActivity: BaseActivity() {
    private val id_toolbar = 1
    private val id_fragment_content = 2
    private var fragment: Fragment? = null

    val toolbar: Toolbar by bindView(id_toolbar)
    abstract val toolbarLayoutId: Int?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        relativeLayout {
            if(toolbarLayoutId != null) {
                include<Toolbar>(toolbarLayoutId!!) {
                    id = id_toolbar
                }.lparams(width = matchParent, height = getActionBarSize())

                frameLayout {
                    id = id_fragment_content
                }.lparams(width = matchParent, height = matchParent) {
                    below(id_toolbar)
                }

            } else {
                frameLayout {
                    id = id_fragment_content
                }.lparams(width = matchParent, height = matchParent)

            }
        }

        setupFragment()
        afterCreate()

        if(toolbarLayoutId != null) {
            setSupportActionBar(toolbar)
        }
    }

    private fun getActionBarSize(): Int {
        var actionBarHeight = dip(50)

        val tv = TypedValue()
        if(theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)
        }

        return actionBarHeight
    }

    private fun setupFragment() {
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragment = fragmentManager.findFragmentByTag(fragmentClass.name)

        if (fragment?.isDetached ?: true) {
            fragment = Fragment.instantiate(this, fragmentClass.name, fragmentBundle)

            fragmentTransaction.add(id_fragment_content, fragment, fragmentClass.name)
        }

        fragmentTransaction.commit()
    }

    abstract fun afterCreate()

    abstract val fragmentClass: Class<*>

    open val fragmentBundle by lazy {
        intent.extras
    }
}
