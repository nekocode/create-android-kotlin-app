package cn.nekocode.kotgo.sample.presentation.page2

import android.os.Message
import cn.nekocode.kotgo.component.presentation.SingleFragmentActivity
import cn.nekocode.kotgo.sample.R
import cn.nekocode.kotgo.sample.data.dto.Meizi

/**
 * Created by nekocode on 16/2/29.
 */
class Page2Activity: SingleFragmentActivity() {
    override val toolbarLayoutId = R.layout.toolbar

    override val fragmentClass = Page2Fragment::class.java
    override val fragmentBundle by lazy {
        intent.extras
    }

    override fun afterCreate() {
        val meizi = intent.getParcelableExtra<Meizi>("meizi")
        toolbar.title = meizi.who
    }

    override fun handler(msg: Message) {
    }
}