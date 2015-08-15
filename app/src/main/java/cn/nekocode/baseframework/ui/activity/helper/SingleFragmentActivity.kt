package cn.nekocode.baseframework.ui.activity.helper

import android.app.Fragment
import android.os.Bundle
import android.os.Message
import android.support.v4.app.DialogFragment
import android.support.v7.widget.Toolbar
import android.util.Log
import butterknife.bindView
import cn.nekocode.baseframework.R
import cn.nekocode.baseframework.model.Weather
import cn.nekocode.baseframework.ui.activity.helper.BaseActivity
import cn.nekocode.baseframework.ui.adapter.ResultAdapter
import cn.nekocode.baseframework.ui.fragment.TestFragment
import cn.nekocode.baseframework.utils.cancelable
import cn.nekocode.baseframework.utils.dialogFragment
import org.jetbrains.anko.*
import kotlin.properties.Delegates

open abstract class SingleFragmentActivity : BaseActivity() {

//    val toolbar: Toolbar by bindView(R.id.toolbar)

    val id_toolbar = 1
    val toolbar: Toolbar by bindView(id_toolbar)

    val id_fragment_content = 2
    var fragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super<BaseActivity>.onCreate(savedInstanceState)

        relativeLayout {
            include<Toolbar>(R.layout.toolbar) {
                id = id_toolbar
            }.layoutParams(width = matchParent, height = dip(50))

            frameLayout {
                id = id_fragment_content
            }.layoutParams(width = matchParent, height = matchParent) {
                below(id_toolbar)
            }
        }

        dialogFragment {
            cancelable = true
        }

        setupFragment()
        afterCreate()

        setSupportActionBar(toolbar)
    }

    private fun setupFragment() {
        val fragmentManager = getFragmentManager()
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragment = fragmentManager.findFragmentByTag(fragmentClass.getName());

        if (fragment?.isDetached() ?: true) {
            fragment = Fragment.instantiate(this, fragmentClass.getName(), fragmentBundle.invoke());

            fragmentTransaction.add(id_fragment_content, fragment, fragmentClass.getName());
        }

        fragmentTransaction.commit()
    }

    abstract fun afterCreate()

    abstract val fragmentClass: Class<*>

    abstract val fragmentBundle: (()-> Bundle?)
}
