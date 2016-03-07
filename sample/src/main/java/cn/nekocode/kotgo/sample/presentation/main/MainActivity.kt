package cn.nekocode.kotgo.sample.presentation.main

import cn.nekocode.kotgo.component.presentation.SingleFragmentActivity
import cn.nekocode.kotgo.component.util.bus
import cn.nekocode.kotgo.sample.R

class MainActivity: SingleFragmentActivity() {
    override val toolbarLayoutId = R.layout.toolbar
    override val fragmentClass = MainFragment::class.java

    override fun afterCreate() {
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
