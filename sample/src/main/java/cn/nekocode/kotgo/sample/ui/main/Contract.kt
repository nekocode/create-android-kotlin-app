package cn.nekocode.kotgo.sample.ui.main

import android.support.v7.widget.RecyclerView

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
interface Contract {
    interface View {
        fun setupAdapter(adapter: RecyclerView.Adapter<*>)
    }

    interface Presenter {
    }
}