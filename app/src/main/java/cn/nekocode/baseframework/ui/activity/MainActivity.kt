package cn.nekocode.baseframework.ui.activity

import android.os.Message
import cn.nekocode.baseframework.ui.activity.component.SingleFragmentActivity
import cn.nekocode.baseframework.ui.fragment.TestFragment

public class MainActivity : SingleFragmentActivity() {

    override val fragmentClass = TestFragment::class.java

    override val fragmentBundle = {
        null
    }

    override fun afterCreate() {
        toolbar.title = "This is a test"
    }

    override fun handler(msg: Message) {
    }
}
