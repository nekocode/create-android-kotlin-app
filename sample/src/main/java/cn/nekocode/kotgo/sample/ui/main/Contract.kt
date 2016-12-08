package cn.nekocode.kotgo.sample.ui.main

import android.support.v7.widget.RecyclerView
import cn.nekocode.kotgo.sample.base.BaseView

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
interface Contract {
    interface View : BaseView {
        fun setupAdapter(adapter: RecyclerView.Adapter<*>)
    }

    interface Presenter {
    }
}