package cn.nekocode.kotgo.sample.ui

import android.os.Bundle
import cn.nekocode.kotgo.component.ui.FragmentActivity
import cn.nekocode.kotgo.sample.ui.main.MainFragment

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class MainActivity : FragmentActivity() {
    override fun onCreatePresenter(presenterFactory: PresenterFactory) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setBackgroundDrawable(null)

        if (savedInstanceState == null)
            MainFragment.push(this)
    }
}
