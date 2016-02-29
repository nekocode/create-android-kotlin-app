package cn.nekocode.baseframework.sample.presentation.page2

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.nekocode.baseframework.component.presentation.SingleFragmentActivity
import cn.nekocode.baseframework.sample.R

/**
 * Created by nekocode on 16/2/29.
 */
class Page2Activity: SingleFragmentActivity() {
    override val toolbarLayoutId = R.layout.toolbar

    override val fragmentClass = Page2MainFragment::class.java
    override val fragmentBundle by lazy {
        intent.extras
    }

    override fun afterCreate() {
        toolbar.title = intent.getStringExtra("title")
    }

    class Page2MainFragment: Fragment() {
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.fragment_page2, container, false)
        }
    }

}