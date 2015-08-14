package cn.nekocode.baseframework.ui.activity.helper

import android.app.Fragment
import android.os.Bundle
import android.os.Message
import android.support.v7.widget.Toolbar
import cn.nekocode.baseframework.R
import cn.nekocode.baseframework.model.Weather
import cn.nekocode.baseframework.ui.activity.helper.BaseActivity
import cn.nekocode.baseframework.ui.adapter.ResultAdapter
import cn.nekocode.baseframework.ui.fragment.TestFragment
import kotlin.properties.Delegates

open abstract class SingleFragmentActivity : BaseActivity() {

    val toolbar: Toolbar by Delegates.lazy {
        findViewById(R.id.toolbar) as Toolbar
    }

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
        val fragmentClass = fragmentClass()

        fragment = fragmentManager.findFragmentByTag(fragmentClass.getName());

        if(fragment?.isDetached() ?: true) {
            fragment = Fragment.instantiate(this, fragmentClass.getName(), fragmentBundle() ?: Bundle());

            fragmentTransaction.add(R.id.fragment_container, fragment, fragmentClass.getName());
            fragmentTransaction.commit()
        }
    }

    abstract fun afterCreate()

    abstract fun fragmentClass(): Class<*>

    abstract fun fragmentBundle(): Bundle?
}
