package cn.nekocode.kotgo.component.presentation

import cn.nekocode.kotgo.component.rx.RxLifecycle

/**
 * Created by nekocode on 2015/11/20.
 */
open class Presenter(val getter: RxLifecycle.Getter): RxLifecycle.Getter {
    override val lifecycle = getter.lifecycle
}