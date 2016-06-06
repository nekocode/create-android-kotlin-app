package cn.nekocode.kotgo.sample.ui.main

import android.support.v7.widget.RecyclerView

/**
 * Created by nekocode on 16/4/9.
 */
interface Contract {
    interface View {
        fun setupAdapter(adapter: RecyclerView.Adapter<*>)
    }

    interface Presenter {
    }
}