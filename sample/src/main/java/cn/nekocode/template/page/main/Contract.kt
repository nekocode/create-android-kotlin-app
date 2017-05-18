package cn.nekocode.template.page.main

import cn.nekocode.itempool.ItemPool
import cn.nekocode.template.base.IPresenter
import cn.nekocode.template.base.IView

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
interface Contract {

    interface View : IView {
        fun setItemPool(itemPool: ItemPool)
    }

    interface Presenter: IPresenter {
    }
}