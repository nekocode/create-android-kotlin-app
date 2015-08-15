package cn.nekocode.baseframework.ui.activity

import android.os.Bundle
import android.os.Message
import cn.nekocode.baseframework.ui.activity.helper.SingleFragmentActivity
import cn.nekocode.baseframework.ui.fragment.TestFragment
import cn.nekocode.baseframework.utils.showToast

public class MainActivity : SingleFragmentActivity() {

    override val fragmentClass = javaClass<TestFragment>()

    override val fragmentBundle = {
        null
    }

    override fun afterCreate() {
        toolbar.setTitle("This is a test")
    }

    override fun handler(msg: Message) {
    }
}
