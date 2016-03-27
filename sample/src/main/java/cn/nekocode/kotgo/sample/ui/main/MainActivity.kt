package cn.nekocode.kotgo.sample.ui.main

import android.os.Bundle
import cn.nekocode.kotgo.component.ui.SingleFragmentActivity
import cn.nekocode.kotgo.component.rx.bus
import cn.nekocode.kotgo.sample.R

class MainActivity: SingleFragmentActivity<MainFragment>() {
    override var toolbarLayoutId: Int? = R.layout.toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar.title = "Meizi List"

        bus {
            subscribe(String::class.java) {
                if(it.equals("Load finished.")) {
                    toolbar.title = "Meizi List - Load finished"
                }
            }
        }
    }
}
