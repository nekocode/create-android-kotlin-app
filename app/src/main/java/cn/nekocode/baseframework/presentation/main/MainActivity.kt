package cn.nekocode.baseframework.presentation.main

import android.os.Message
import cn.nekocode.baseframework.presentation.SingleFragmentActivity

class MainActivity : SingleFragmentActivity() {

    override val fragmentClass = TestFragment::class.java

    override val fragmentBundle by lazy {
        intent.extras
    }

    override fun afterCreate() {
        toolbar.title = "This is a test"
    }

    override fun handler(msg: Message) {
    }
}
