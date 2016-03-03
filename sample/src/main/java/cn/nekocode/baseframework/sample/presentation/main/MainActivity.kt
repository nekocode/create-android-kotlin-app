package cn.nekocode.baseframework.sample.presentation.main

import android.os.Message
import cn.nekocode.baseframework.component.presentation.SingleFragmentActivity
import cn.nekocode.baseframework.sample.R

class MainActivity : SingleFragmentActivity() {
    override val toolbarLayoutId = R.layout.toolbar
    override var toolbarHeight = 50

    override val fragmentClass = MainFragment::class.java
    override val fragmentBundle by lazy {
        intent.extras
    }

    override fun afterCreate() {
        toolbar.title = "Meizi List"
    }

    override fun handler(msg: Message) {
    }
}
