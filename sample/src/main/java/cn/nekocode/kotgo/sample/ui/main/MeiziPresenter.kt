package cn.nekocode.kotgo.sample.ui.main

import android.os.Bundle
import cn.nekocode.kotgo.component.ui.Presenter
import cn.nekocode.kotgo.component.rx.bindLifecycle
import cn.nekocode.kotgo.component.rx.onUI
import cn.nekocode.kotgo.sample.data.dto.Meizi
import cn.nekocode.kotgo.sample.data.model.MeiziModel

/**
 * Created by nekocode on 2015/11/20.
 */
class MeiziPresenter(val view: ViewInterface): Presenter(view) {
    interface ViewInterface: BaseViewInterface {
        fun refreshMeizis(meizis: List<Meizi>)
    }

    override fun onCreate(savedState: Bundle?) {
        MeiziModel.getMeizis(50, 1).onUI().bindLifecycle(view).subscribe {
            view.refreshMeizis(it)
        }
    }
}