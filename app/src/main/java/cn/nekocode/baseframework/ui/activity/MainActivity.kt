package cn.nekocode.baseframework.ui.activity

import android.os.Bundle
import android.os.Message
import cn.nekocode.baseframework.ui.activity.helper.FillparentFragmentActivity
import cn.nekocode.baseframework.ui.fragment.TestFragment
import cn.nekocode.baseframework.utils.showToast

public class MainActivity : FillparentFragmentActivity() {

    override val fragmentClasses: List<Class<*>>
            = listOf(javaClass<TestFragment>())

    override val fragmentTags: List<String>
            = listOf("testFragment")

    override val fragmentBundles: List<Bundle?>?
            = null

    override fun afterCreate() {
        toolbar.setTitle("This is a test")
    }

    override fun handler(msg: Message) {
    }
}
