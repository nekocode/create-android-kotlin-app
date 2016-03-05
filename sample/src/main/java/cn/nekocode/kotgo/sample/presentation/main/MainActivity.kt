package cn.nekocode.kotgo.sample.presentation.main

import android.os.Bundle
import cn.nekocode.kotgo.component.presentation.SingleFragmentActivity
import cn.nekocode.kotgo.sample.App
import cn.nekocode.kotgo.sample.R
import com.squareup.otto.Subscribe

class MainActivity: SingleFragmentActivity() {
    override val toolbarLayoutId = R.layout.toolbar
    override var toolbarHeight = 50

    override val fragmentClass = MainFragment::class.java
    override val fragmentBundle by lazy {
        intent.extras
    }

    override fun afterCreate() {
        toolbar.title = "Meizi List"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.bus.register(this)
    }

    @Subscribe fun loadState(state: String) {
        if(state.equals("Load finished.")) {
            toolbar.title = "Meizi List - Load finished"
        }
        App.bus.unregister(this)
    }
}
