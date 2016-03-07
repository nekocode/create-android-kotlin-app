package cn.nekocode.kotgo.sample.presentation.main

import cn.nekocode.kotgo.component.presentation.Presenter
import cn.nekocode.kotgo.component.util.LifecycleContainer
import cn.nekocode.kotgo.component.util.onUIInLifecycle
import cn.nekocode.kotgo.sample.data.dto.Meizi
import cn.nekocode.kotgo.sample.data.model.MeiziModel

/**
 * Created by nekocode on 2015/11/20.
 */
class MeiziPresenter(val view: MeiziPresenter.ViewInterface): Presenter(view) {
    interface ViewInterface: LifecycleContainer {
        fun refreshMeizis(meizis: List<Meizi>)
    }

    fun getMeizis() {
        MeiziModel.getMeizis(50, 1).onUIInLifecycle(view) {
            view.refreshMeizis(it)
        }
    }
}