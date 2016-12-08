package cn.nekocode.kotgo.sample.ui.page2

import cn.nekocode.kotgo.sample.base.BaseView

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
interface Contract {
    interface View : BaseView {
        fun showMeizi(meiziVO: MeiziVO)
    }

    interface Presenter {
        fun onImageClick(meiziVO: MeiziVO)
    }
}