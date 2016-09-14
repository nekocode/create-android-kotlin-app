package cn.nekocode.kotgo.sample.ui

import android.os.Bundle
import cn.nekocode.kotgo.component.ui.FragmentActivity
import cn.nekocode.kotgo.sample.ui.main.MainFragment

class MainActivity : FragmentActivity() {
    override fun afterOnCreate(savedInstanceState: Bundle?) {
        super.afterOnCreate(savedInstanceState)

        window.setBackgroundDrawable(null)

        if (savedInstanceState == null)
            MainFragment.push(this)
    }
}
