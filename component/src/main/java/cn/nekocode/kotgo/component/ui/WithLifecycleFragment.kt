package cn.nekocode.kotgo.component.ui

import android.app.Fragment
import cn.nekocode.kotgo.component.rx.RxLifecycle

/**
 * Created by nekocode on 2015/11/20.
 */
open class WithLifecycleFragment(): Fragment(), RxLifecycle.Impl {
    override val lifecycle = RxLifecycle()

    override fun onDestroyView() {
        lifecycle.onDestory()
        super.onDestroyView()
    }
}