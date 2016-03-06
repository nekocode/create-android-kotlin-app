package cn.nekocode.kotgo.sample.presentation.main

import cn.nekocode.kotgo.component.presentation.Presenter
import cn.nekocode.kotgo.sample.data.dto.Meizi
import cn.nekocode.kotgo.sample.data.model.MeiziModel

/**
 * Created by nekocode on 2015/11/20.
 */
class MeiziPresenter(val view: MeiziPresenter.ViewInterface): Presenter() {
    interface ViewInterface {
        fun refreshMeizis(meizis: List<Meizi>)
    }

    fun getMeizis() {
        MeiziModel.getMeizis(50, 1).on(this).subscribe {
            view.refreshMeizis(it)
        }
    }
}