package cn.nekocode.kotgo.sample.presentation.page2

import android.os.Message
import cn.nekocode.kotgo.component.presentation.SingleFragmentActivity

/**
 * Created by nekocode on 16/2/29.
 */
class Page2Activity: SingleFragmentActivity() {
    override val toolbarLayoutId = null
    override val fragmentClass = Page2Fragment::class.java

    override fun afterCreate() {
    }

    override fun handler(msg: Message) {
    }
}