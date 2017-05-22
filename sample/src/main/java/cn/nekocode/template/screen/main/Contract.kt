package cn.nekocode.template.screen.main

import android.support.v7.widget.RecyclerView
import cn.nekocode.template.base.IPresenter
import cn.nekocode.template.base.IView

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
interface Contract {

    interface View : IView {
        fun setAdapter(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>)
    }

    interface Presenter: IPresenter {
    }
}