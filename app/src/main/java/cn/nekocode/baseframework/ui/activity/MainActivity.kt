package cn.nekocode.baseframework.ui.activity

import android.os.Bundle
import android.os.Message
import cn.nekocode.baseframework.ui.activity.helper.SingleFragmentActivity
import cn.nekocode.baseframework.ui.fragment.TestFragment
import cn.nekocode.baseframework.utils.showToast

public class MainActivity : SingleFragmentActivity() {

    override fun afterCreate() {
        toolbar.setTitle("This is a test")
    }

    override fun fragmentClass(): Class<*> {
        return javaClass<TestFragment>()
    }

    override fun fragmentBundle(): Bundle? {
        return null
    }

    override fun handler(msg: Message) {
    }
}
