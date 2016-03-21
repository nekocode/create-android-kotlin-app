package cn.nekocode.kotgo.component.presentation

import android.os.Bundle
import cn.nekocode.kotgo.component.rx.RxLifecycle

/**
 * Created by nekocode on 2015/11/20.
 */
open class Presenter(view: BaseViewInterface): RxLifecycle.Impl {
    interface BaseViewInterface: RxLifecycle.Impl {
    }

    override val lifecycle = view.lifecycle

    open fun onCreate(savedState: Bundle?) {
    }

    open fun onDestory() {
    }

    open fun onSave(state: Bundle) {
    }
}