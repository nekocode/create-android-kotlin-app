package cn.nekocode.kotgo.component.ui

import android.app.Fragment
import cn.nekocode.kotgo.component.rx.RxLifecycle

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
open class WithLifecycleFragment() : Fragment(), RxLifecycle.Impl {
    override val lifecycle = RxLifecycle()

    override fun onDestroyView() {
        lifecycle.onDestory()
        super.onDestroyView()
    }
}