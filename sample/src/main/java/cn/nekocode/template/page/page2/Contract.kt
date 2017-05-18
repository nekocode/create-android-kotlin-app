package cn.nekocode.template.page.page2

import cn.nekocode.template.base.IPresenter
import cn.nekocode.template.base.IView

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
interface Contract {

    interface View : IView {
        fun setMeizi(vo: MeiziVO)
    }

    interface Presenter: IPresenter {
        fun onImageClick()
    }
}