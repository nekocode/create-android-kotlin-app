package cn.nekocode.kotgo.sample.ui.main

import cn.nekocode.kotgo.sample.data.dto.Meizi

/**
 * Created by nekocode on 16/4/9.
 */
interface Contract {
    interface View {
        fun refreshMeizis(meizis: List<Meizi>)
    }

    interface MeiziPresenter {

    }
}