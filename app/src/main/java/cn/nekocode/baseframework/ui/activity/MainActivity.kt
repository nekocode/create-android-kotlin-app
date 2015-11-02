package cn.nekocode.baseframework.ui.activity

import android.os.Message
import cn.nekocode.baseframework.model.Model
import cn.nekocode.baseframework.ui.activity.helper.SingleFragmentActivity
import cn.nekocode.baseframework.ui.fragment.TestFragment
import org.jetbrains.anko.intentFor

public class MainActivity : SingleFragmentActivity() {

    override val fragmentClass = TestFragment::class.java

    override val fragmentBundle = {
        null
    }

    override fun afterCreate() {
        val model = intent.getParcelableExtra<Model>("test")

        if(model == null) {
            toolbar.title = "This is a test"
            val intent = intentFor<MainActivity>(Pair("test", Model(5, 0)))
            startActivity(intent)
        } else {
            toolbar.title = "Intent: " + model.test1 + "," + model.test2
        }
    }

    override fun handler(msg: Message) {
    }
}
