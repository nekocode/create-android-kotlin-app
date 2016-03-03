package cn.nekocode.baseframework.sample.presentation.main

import cn.nekocode.baseframework.component.presentation.Presenter
import cn.nekocode.baseframework.sample.data.dto.Meizi
import cn.nekocode.baseframework.sample.data.model.MeiziModel

/**
 * Created by nekocode on 2015/11/20.
 */
class MeiziPresenter(val view: MeiziPresenter.ViewInterface): Presenter() {
    interface ViewInterface {
        fun refreshMeizis(meizis: List<Meizi>)
    }

    fun getMeizis() {
        MeiziModel.getMeizis(10, 1).on(this).subscribe {
            view.refreshMeizis(it)
        }
    }
}