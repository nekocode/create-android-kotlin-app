package cn.nekocode.baseframework.ui.activity.helper

import android.app.Fragment
import android.os.Bundle
import android.os.Message
import android.support.v7.widget.Toolbar
import android.util.Log
import butterknife.bindView
import cn.nekocode.baseframework.R
import cn.nekocode.baseframework.model.Weather
import cn.nekocode.baseframework.ui.activity.helper.BaseActivity
import cn.nekocode.baseframework.ui.adapter.ResultAdapter
import cn.nekocode.baseframework.ui.fragment.TestFragment
import kotlin.properties.Delegates

open abstract class FillparentFragmentActivity : BaseActivity() {

    val toolbar: Toolbar by bindView(R.id.toolbar)

    var fragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super<BaseActivity>.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_fragment)

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

            fragmentTransaction.add(R.id.fragment_container, fragment, fragmentClass.getName());
        }

        fragmentTransaction.commit()
    }

    abstract fun afterCreate()

    abstract val fragmentClass: Class<*>

    abstract val fragmentBundle: (()-> Bundle?)
}
