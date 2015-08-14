package cn.nekocode.baseframework.ui.activity

import android.os.Bundle
import android.os.Message
import cn.nekocode.baseframework.ui.activity.helper.FillparentFragmentActivity
import cn.nekocode.baseframework.ui.fragment.TestFragment
import cn.nekocode.baseframework.utils.showToast

public class MainActivity : FillparentFragmentActivity() {

    override val fragmentClass: Class<*> = javaClass<TestFragment>()

    override val fragmentBundle: Bundle? = null

    override fun afterCreate() {
        toolbar.setTitle("This is a test")
    }

    override fun handler(msg: Message) {
    }
}
